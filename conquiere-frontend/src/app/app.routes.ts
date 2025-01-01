import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AUTHENTIFICATION, HOME } from './constants/url';
import { HomeComponent } from './features/home/home.component';

export const routes: Routes = [
    {path : HOME.base, component: HomeComponent },
    {path : AUTHENTIFICATION.base, loadChildren : () => import('./features/auth/auth/auth.module').then(m => m.AuthModule)}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  export class AppRoutes {}