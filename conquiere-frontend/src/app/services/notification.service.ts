import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ErrorHandlerDialogComponent } from '../features/error/error-handler-dialog/error-handler-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {


  private dialog = inject(MatDialog);
  constructor() { }


  openServerErrorDialog(message: string) {
    this.dialog.open(ErrorHandlerDialogComponent, {
      data: { message },
    });
}

}
