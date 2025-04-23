import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardUserComponent } from './dashboard-user/dashboard-user.component';
import { UserRegistrationService } from '../../services/user-registration.service';
import { USER } from '../../constants/url';

const routes: Routes = [
  { path: USER.dashbord, component: DashboardUserComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
