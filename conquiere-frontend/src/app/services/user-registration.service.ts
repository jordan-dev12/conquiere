import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  private http = inject(HttpClient);
  private apiUrl = inject(ApiService);

  registerUser(userData: User): Observable<User> {

    return this.http.post<User>(`${this.apiUrl.getUserUrl()}/register`, userData)
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl.getUserUrl()}/get`)
  }
}