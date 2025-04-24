import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-custom-modal',
  imports: [MatDialogModule],
  templateUrl: './custom-modal.component.html',
  styleUrl: './custom-modal.component.css'
})
export class CustomModalComponent implements OnInit {

  text: string | undefined


  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<CustomModalComponent>) { }

  ngOnInit(): void {

    this.text = this.data.text;
  }

  onConfirmClick(): void {
    this.dialogRef.close(true);
    this.data.confirmationFunc();
  }

}
