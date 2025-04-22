import { HttpInterceptorFn } from "@angular/common/http";
import { inject } from "@angular/core";
import { AuthService } from "../services/auth.service";
import { environment } from "../../environments/environment";

export const authInterceptor: HttpInterceptorFn = (request, next) => {

    const authService = inject(AuthService);
    const baseUrl = environment.conquiereBackEnd;

    if (!request.url.startsWith(baseUrl)) {
        return next(request);
    }

    if (request.url.includes('/api/auth')) {
        return next(request);
    }

    const token = authService.getToken();

    if (token) {
        const authRequest = request.clone({
            headers: request.headers.set('Authorization', `TBASS ${token}`)
        });
        return next(authRequest);
    }

    return next(request);
}

