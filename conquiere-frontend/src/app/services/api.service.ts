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

  getUserUrl(): string {
    return `${this.baseUrl}/api/user`;
  }
  getTournoiUrl(): string {
    return `${this.baseUrl}/api/tournoi`;
  }
}
