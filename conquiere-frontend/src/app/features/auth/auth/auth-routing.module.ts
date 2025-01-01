import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AUTHENTIFICATION } from '../../../constants/url';
import { LoginComponent } from '../login/login.component';

const routes: Routes = [
  {path : AUTHENTIFICATION.login , component : LoginComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
