import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AUTHENTIFICATION } from '../../../constants/url';
import { LoginComponent } from '../login/login.component';
import { RegistrationComponent } from '../registration/registration.component';

const routes: Routes = [
  {path : AUTHENTIFICATION.login , component : LoginComponent},
  {path : AUTHENTIFICATION.registration , component : RegistrationComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
 