import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-error-handler-dialog',
  imports: [MatDialogModule],
  templateUrl: './error-handler-dialog.component.html',
  styleUrl: './error-handler-dialog.component.css'
})
export class ErrorHandlerDialogComponent {

  public title = 'Attention';
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

}
