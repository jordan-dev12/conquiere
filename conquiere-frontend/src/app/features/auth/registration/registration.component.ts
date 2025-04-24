import { Component, computed, inject, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AUTH_URL } from '../../../constants/url';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { User } from '../../../models/user.model';
import { UserRegistrationService } from '../../../services/user-registration.service';

@Component({
  selector: 'app-registration',
  imports: [RouterModule, ReactiveFormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {


  datePattern = /^(0[1-9]|[12][0-9]|3[01])\/(0[1-9]|1[0-2])\/\d{4}$/;
  private fb = inject(FormBuilder);
  private userService = inject(UserRegistrationService);

  readonly AUTH = AUTH_URL;


  get f() { return this.registrationForm.controls; }

  registrationForm = this.fb.group({
    name: ['', [Validators.required, Validators.minLength(2)]],
    surname: ['', [Validators.required, Validators.minLength(2)]],
    terms: ['', [Validators.required, Validators.requiredTrue]],
    // birthdate: ['', [Validators.required, Validators.pattern(this.datePattern)]],
    birthdate: ['', [Validators.required]],
    email: ['',
      [Validators.required, Validators.email]
    ],
    password: ['', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)
    ]],
    confirmPassword: ['', Validators.required]
  }, { validators: this.passwordMatchValidator });

  registration() {

    if (this.registrationForm.invalid) {
      Object.keys(this.registrationForm.controls).forEach(key => {
        const control = this.registrationForm.get(key);
        control?.markAsTouched();
      });
      return;
    }


    const userRequest: User = {
      name: this.f.name.value!,
      surname: this.f.surname.value!,
      email: this.f.email.value!,
      birthdate: this.f.birthdate.value!,
      password: this.f.password.value!
    };

    this.userService.registerUser(userRequest).subscribe((data) => console.log(data));
  }


  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      control.get('confirmPassword')?.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }

    return null;
  }








}
