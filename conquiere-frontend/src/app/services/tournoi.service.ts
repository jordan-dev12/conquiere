import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Pagination } from '../models/pagination.model';
import { Tournois } from '../models/tournois';
import { Tournoi } from '../models/tournoi.model';

@Injectable({
  providedIn: 'root'
})
export class TournoiService {

  private http = inject(HttpClient);
  private apiUrl = inject(ApiService);

  loadAllTournoi(pageable: Pagination): Observable<Tournois> {
    return this.http.post<Tournois>(`${this.apiUrl.getTournoiUrl()}/loadAll`, pageable)
  }
  createTournoi(tournoi: Tournoi): Observable<Tournoi> {
    return this.http.post<Tournoi>(`${this.apiUrl.getTournoiUrl()}/create`, tournoi)
  }

}
