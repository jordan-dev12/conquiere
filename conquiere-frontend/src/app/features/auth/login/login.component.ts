import { Component } from '@angular/core';
import { AUTH_URL } from '../../../constants/url';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [RouterModule ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

   readonly AUTH  =  AUTH_URL;

}
