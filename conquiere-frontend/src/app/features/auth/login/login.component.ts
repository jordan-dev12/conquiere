import { Component, inject } from '@angular/core';
import { AUTH_URL } from '../../../constants/url';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthRequest } from '../../../models/auth-request.model';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  readonly AUTH = AUTH_URL;
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);


  loginForm = this.fb.group({
    email: ['',
      [Validators.required,Validators.email]
    ],
    rememberMe: ['',],
    password: ['', [
      Validators.required,
      Validators.minLength(8),
    ]],
  });

  get f() { return this.loginForm.controls; }

  login() {

    if (this.loginForm.invalid) {
      Object.keys(this.loginForm.controls).forEach(key => {
        const control = this.loginForm.get(key);
        control?.markAsTouched();
      });
      return;
    }

    const request: AuthRequest = {
      username: this.f.email.value!,
      password: this.f.password.value!
    };

    this.authService.login(request).subscribe((data) => console.log(data));;

  }

}
