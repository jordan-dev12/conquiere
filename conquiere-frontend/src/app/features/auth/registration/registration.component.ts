import { Component, computed, signal } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AUTH_URL } from '../../../constants/url';
import { Form, FormsModule } from '@angular/forms';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-registration',
  imports: [RouterModule, FormsModule],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {

  readonly AUTH = AUTH_URL;
  userData = signal<User>({
    name: '',
    surname: '',
    email: '',
    password: '',
    confirmPassword: '',
    birthdate: ''
  })

  // Signal pour les erreurs de validation
formErrors = signal<Record<string, string>>({});

// verifier que la password est la m


  registration(form: Form) {
    console.log(this.userData());
  }


  updateEmail(value : string){

    this.userData.update((data) => ({
      ...data,
      email : value
    }));
  }

  updatePassword(password :string){

  }

  isFormValid = computed<boolean>(() => {
    const data = this.userData();
    return (
      data.name.trim().length > 0 &&
      data.surname.trim().length > 0 &&
      this.isValidEmail(data.email) &&
      data.password.length >= 8 &&
      this.passwordMatch() 
    );
  });

  passwordMatch = computed( () => this.userData().confirmPassword === this.userData().password);



  // Fonction de validation personnalisée
  isValidEmail(email: string) {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  
  if (!emailRegex.test(email)) {
    this.formErrors.update(errors => ({
      ...errors,
      email: 'Format d\'email invalide'
    }));
    return false;
  } 
  return true;
}

}
