import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { environment } from "../../environments/environment";
import { catchError, Observable, switchMap, throwError } from "rxjs";

export const authInterceptor: HttpInterceptorFn = (request, next): Observable<HttpEvent<unknown>> => {


    const authService = inject(AuthService);
    const baseUrl = environment.conquiereBackEnd;

    if (!request.url.startsWith(baseUrl)) {
        return next(request);
    }

    // Laisser passer les routes d'authentification sans token
    const publicRoutes = ['/api/auth/login', '/api/user/register'];
    if (publicRoutes.some(route => request.url.includes(route))) {
        return next(request);
    }

    const token = authService.getAccessToken();

    if (token === null)
        return next(request);

    const authRequest = addAuthHeader(request, token);

    return next(authRequest).pipe(
        catchError(error => {
            if (error instanceof HttpErrorResponse && error.status === 403) {
                return handleTokenRefresh(authService, request, next);
            }
            return throwError(() => error);
        })
    );
};

// Fonctions auxiliaires pour améliorer la lisibilité
function addAuthHeader(request: HttpRequest<unknown>, token: string): HttpRequest<unknown> {
    return request.clone({
        headers: request.headers.set('Authorization', `TBASS ${token}`)
    });
}

function handleTokenRefresh(authService: AuthService, request: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    return authService.refreshToken().pipe(
        switchMap(() => {
            const newToken = authService.getAccessToken();
            if (!newToken) {
                authService.logout();
                return throwError(() => new Error('Token is null after refresh'));
            }
            const authRequest = addAuthHeader(request, newToken);
            return next(authRequest);
        }),
        catchError((refreshError) => {
            authService.logout();
            return throwError(() => refreshError);
        })
    );
}
