import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthRequest } from '../models/auth-request.model';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { AuthResponse } from '../models/auth-response.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = inject(ApiService);
  private http = inject(HttpClient);

  login(auth: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl.getAuthUrl()}/login`, auth);
  }


  setToken(token: string): void {
    localStorage.setItem('auth_token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('auth_token');
  }
}
