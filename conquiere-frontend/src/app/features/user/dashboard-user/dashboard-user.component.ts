import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { TournoiService } from '../../../services/tournoi.service';
import { Tournoi } from '../../../models/tournoi.model';
import { Pagination } from '../../../models/pagination.model';

@Component({
  selector: 'app-dashboard-user',
  imports: [CommonModule],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent implements OnInit {

  tournoiService = inject(TournoiService)

  tounois = signal<Tournoi[] | null>(null)

  ngOnInit(): void {

    const paginationRequest: Pagination = {
      page: 0,
      size: 12
    };

    this.tournoiService.loadAllTournoi(paginationRequest).subscribe(response => {

      this.tounois.set(response.tournaments);
    });

  }


  items = [
    { id: 1, name: 'Item A', description: 'Description de l\'item A' },
    { id: 2, name: 'Item B' },
    { id: 3, name: 'Item C', description: 'Ceci est l\'item C' },
    { id: 4, name: 'Item C', description: 'Ceci est l\'item C' },
    { id: 5, name: 'Item C', description: 'Ceci est l\'item C' }
  ];


}
