import { HttpClient } from '@angular/common/http';
import { computed, effect, inject, Injectable, signal } from '@angular/core';
import { AuthRequest } from '../models/auth-request.model';
import { BehaviorSubject, catchError, map, Observable, of, tap, throwError } from 'rxjs';
import { ApiService } from './api.service';
import { AuthResponse } from '../models/auth-response.model';
import { jwtDecode, JwtPayload } from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = inject(ApiService);
  private http = inject(HttpClient);


  private static ACCESS_TOKEN: string = 'access_token';
  private static REFRESH_TOKEN: string = 'refresh_token';


  private accessTokenSignal = signal<string | null>(localStorage.getItem(AuthService.ACCESS_TOKEN));
  private refreshTokenSignal = signal<string | null>(localStorage.getItem(AuthService.REFRESH_TOKEN));

  constructor() {
    // Effect pour synchroniser les signals avec le localStorage
    effect(() => localStorage.setItem(AuthService.ACCESS_TOKEN, this.accessTokenSignal() || ''));
    effect(() => localStorage.setItem(AuthService.REFRESH_TOKEN, this.refreshTokenSignal() || ''));

  }

  login(auth: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl.getAuthUrl()}/login`, auth);
  }


  public isAuthenticated = computed(() => !!this.accessTokenSignal());


  setToken(auth: AuthResponse): void {

    this.accessTokenSignal.set(auth.accessToken)
    this.refreshTokenSignal.set(auth.refreshToken)

  }

  removeToken(): void {

    this.accessTokenSignal.set(null)
    this.refreshTokenSignal.set(null)

  }

  getAccessToken(): string | null {
    return this.accessTokenSignal();
  }

  refreshToken(): Observable<boolean> {
    const refreshToken = this.refreshTokenSignal();

    if (!refreshToken) {
      of(false)
    }

    this.http.post<AuthResponse>(`${this.apiUrl.getAuthUrl}/refresh`, { refreshToken })
      .pipe(
        tap(response => {
          this.accessTokenSignal.set(response.accessToken);
        }),
        catchError(() => {
          return of(false);
        })
      );

    return of(true);
  }


  isTokenExpired(): boolean {
    const token = this.accessTokenSignal();
    if (!token) return false;

    try {
      const decoded = jwtDecode<JwtPayload>(token);
      const currentTime = Date.now() / 1000;

      // Vérifier si le token expire dans moins de 30 secondes
      return decoded.exp !== undefined && decoded.exp < currentTime + 30;
    } catch (error) {
      return true;
    }
  }
}

