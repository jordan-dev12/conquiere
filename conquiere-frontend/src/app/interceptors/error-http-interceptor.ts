import { HttpErrorResponse, HttpInterceptorFn } from "@angular/common/http";
import { NotificationService } from "../services/notification.service";
import { inject } from "@angular/core";
import { catchError, throwError } from "rxjs";

export const errorHttpInterceptor : HttpInterceptorFn = (req, next) => {
    const notificationService = inject(NotificationService);
    
    return next(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = '';
        
        if (error.error instanceof ErrorEvent) {
          // Erreur côté client
          errorMessage = `Erreur: ${error.error.message}`;
        } else {
          // Erreur côté serveur
          errorMessage = `Code d'erreur: ${error.status}\nMessage: ${error.error?.detail|| error.message}`;
        }
        
        // Afficher l'erreur dans un popup
        notificationService.openServerErrorDialog(errorMessage);
        
        return throwError(() => error);
      })
    );
  }; 