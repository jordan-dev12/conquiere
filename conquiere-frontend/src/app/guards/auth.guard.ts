import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service"
import { Router } from "@angular/router";
import { AUTH_URL } from "../constants/url";


export const authGuard = () => {

    const authService = inject(AuthService);
    const AUTH = AUTH_URL;
    const router = inject(Router);

    if (authService.isAuthenticated()) {
        if (authService.isTokenExpired()) {
            // Rafraîchir le token avant de continue r
            authService.refreshToken().subscribe({
                next: () => true,
                error: () => {
                    router.createUrlTree([AUTH.login]);
                    return false;
                }
            });
        }
        return true;
    }

    return router.createUrlTree([AUTH.login]);

}