import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = environment.conquiereBackEnd;

  getAuthUrl(): string {
    return `${this.baseUrl}/api/auth`;
  }

  getUsersUrl(): string {
    return `${this.baseUrl}/api/user`;
  }
}
