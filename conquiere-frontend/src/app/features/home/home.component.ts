import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AUTH_URL } from '../../constants/url';

@Component({
  selector: 'app-home',
  standalone: true,
  imports : [RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  readonly AUTH  =  AUTH_URL;
  // readonly AUTH_REGISTER  =  AUTH_URL.register;

}
