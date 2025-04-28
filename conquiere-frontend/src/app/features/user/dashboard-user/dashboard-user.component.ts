import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { TournoiService } from '../../../services/tournoi.service';
import { Tournoi } from '../../../models/tournoi.model';
import { Pagination } from '../../../models/pagination.model';
import { UserRegistrationService } from '../../../services/user-registration.service';
import { User } from '../../../models/user.model';
import { CreateTournoiModalComponent } from '../../../custom/create-tournoi-modal/create-tournoi-modal.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-dashboard-user',
  imports: [CommonModule, MatDialogModule],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent implements OnInit {

  tournoiService = inject(TournoiService)
  userService = inject(UserRegistrationService)
  private matDialog = inject(MatDialog);

  tounois = signal<Tournoi[] | null>(null)
  currenUser = signal<User | null>(null)

  hasAdmin = computed(() => this.currenUser()?.roles?.includes('ADMIN'))


  ngOnInit(): void {

    this.userService.getCurrentUser().subscribe(response => {
      this.currenUser.set(response);
    })

    this.loadAllTournois();


  }

  getPagination(): Pagination {
    return {
      page: 0,
      size: 12
    };
  }


  createTournoiModal() {

    this.matDialog.open(CreateTournoiModalComponent, {
      data: {
        confirmationFunc: () => this.loadAllTournois()
      }, disableClose: false,
      hasBackdrop: false
    },);

  }


  loadAllTournois() {
    this.tournoiService.loadAllTournoi(this.getPagination()).subscribe(response => {
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
