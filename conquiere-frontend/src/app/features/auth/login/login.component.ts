import { Component } from '@angular/core';
import { AUTH_URL } from '../../../constants/url';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

   readonly AUTH  =  AUTH_URL;

}
