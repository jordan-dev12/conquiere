import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AUTHENTIFICATION, HOME, USER } from './constants/url';
import { HomeComponent } from './features/home/home.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    { path: HOME.base, component: HomeComponent },
    { path: AUTHENTIFICATION.base, loadChildren: () => import('./features/auth/auth/auth.module').then(m => m.AuthModule) },
    { path: USER.base, canActivate: [authGuard], loadChildren: () => import('./features/user/user.module').then(m => m.UserModule) }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutes { }