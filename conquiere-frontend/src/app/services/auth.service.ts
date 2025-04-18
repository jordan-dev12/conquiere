import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthRequest } from '../models/auth-request.model';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

   private apiUrl = inject(ApiService);
  private http = inject(HttpClient);

  login(auth: AuthRequest): Observable<any> {

    return this.http.post<any>(`${this.apiUrl.getAuthUrl()}/autentification`, auth);
  }
}
