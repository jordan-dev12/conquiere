import { HttpErrorResponse, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { environment } from "../../environments/environment";
import { catchError, throwError } from "rxjs";

export const authInterceptor: HttpInterceptorFn = (request, next) => {

    const authService = inject(AuthService);
    const baseUrl = environment.conquiereBackEnd;

    if (!request.url.startsWith(baseUrl)) {
        return next(request);
    }

    if (request.url.includes('/api/auth/login') || request.url.includes('/api/user/register')) {
        return next(request);
    }

    const token = authService.getAccessToken();

    if (token) {
        const authRequest = request.clone({
            headers: request.headers.set('Authorization', `TBASS ${token}`)
        });
        return next(authRequest).pipe(
            catchError(error => {
                if (error instanceof HttpErrorResponse && error.status === 401) {
                    // return this.handle401Error(request, next);
                }
                return throwError(() => error);
            })
        );;
    }

    return next(request);
}

