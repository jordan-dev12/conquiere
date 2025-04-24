import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard-user',
  imports: [CommonModule],
  templateUrl: './dashboard-user.component.html',
  styleUrl: './dashboard-user.component.css'
})
export class DashboardUserComponent {

  items = [
    { id: 1, name: 'Item A', description: 'Description de l\'item A' },
    { id: 2, name: 'Item B' },
    { id: 3, name: 'Item C', description: 'Ceci est l\'item C' },
    { id: 4, name: 'Item C', description: 'Ceci est l\'item C' },
    { id: 5, name: 'Item C', description: 'Ceci est l\'item C' }
  ];


}
