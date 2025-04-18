import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { AuthRequest } from '../models/auth-request.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8081/api/auth';
  private http = inject(HttpClient);

  login(auth: AuthRequest): Observable<any> {

    return this.http.post<any>(`${this.apiUrl}/autentification`, auth);
  }
}
