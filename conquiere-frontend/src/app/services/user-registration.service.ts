import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserRegistrationService {

  private apiUrl = 'https://localhost:8180/api/user';
  private http = inject(HttpClient);

  registerUser(userData: User): Observable<User> {

    return this.http.post<User>(`${this.apiUrl}/register`, userData)
}
}