import { CommonModule } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { TournoiService } from '../../../services/tournoi.service';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { Tournoi } from '../../../models/tournoi.model';
import { Pagination } from '../../../models/pagination.model';
import { UserRegistrationService } from '../../../services/user-registration.service';
import { User } from '../../../models/user.model';
import { CreateTournoiModalComponent } from '../../../custom/create-tournoi-modal/create-tournoi-modal.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AuthService } from '../../../services/auth.service';
import { HOME } from '../../../constants/url';
import { Router } from '@angular/router';


@Component({
  selector: 'app-dashboard-user',
  imports: [CommonModule, MatDialogModule, NgbDropdownModule],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent implements OnInit {

  tournoiService = inject(TournoiService)
  userService = inject(UserRegistrationService)
  authServivce = inject(AuthService)
  router = inject(Router)
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
      hasBackdrop: false, autoFocus: true
    },);

  }


  loadAllTournois() {
    this.tournoiService.loadAllTournoi(this.getPagination()).subscribe(response => {
      this.tounois.set(response.tournaments);
    });
  }

  logout() {
    this.authServivce.logout();
    this.router.navigate([HOME.base]);
  }
}
