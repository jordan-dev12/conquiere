import { Component, inject } from '@angular/core';
import { AUTH_URL } from '../../../constants/url';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [RouterModule,ReactiveFormsModule ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

   readonly AUTH  =  AUTH_URL;
   private fb = inject(FormBuilder);


   loginForm = this.fb.group({
    email: ['', 
      [Validators.required]
    ],
    password: ['', [
      Validators.required, 
      Validators.minLength(8),
    ]],
   });

   get f() { return this.loginForm.controls; }

   login(){
    
   }

}
