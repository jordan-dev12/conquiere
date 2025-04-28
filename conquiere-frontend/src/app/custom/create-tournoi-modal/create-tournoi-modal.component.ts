import { Component, inject, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { Tournoi } from '../../models/tournoi.model';
import { TournoiService } from '../../services/tournoi.service';

@Component({
  selector: 'app-create-tournoi-modal',
  imports: [MatDialogModule, ReactiveFormsModule],
  templateUrl: './create-tournoi-modal.component.html',
  styleUrl: './create-tournoi-modal.component.css'
})
export class CreateTournoiModalComponent {

  private fb = inject(FormBuilder)
  private tournoiService = inject(TournoiService)

  constructor(@Inject(MAT_DIALOG_DATA) private data: any,
    private dialogRef: MatDialogRef<CreateTournoiModalComponent>) { }


  tournoiForm = this.fb.group({
    title: ['', [Validators.required]],
    description: ['', [Validators.required]],
    eventDate: ['', [Validators.required, this.dateFormatValidator()]]
  })

  get f() { return this.tournoiForm.controls; }


  createTournoi() {

    if (this.tournoiForm.invalid)
      return;

    const tournoiRequest: Tournoi = {
      title: this.f.title.value!,
      description: this.f.description.value!,
      eventDate: this.f.eventDate.value!,
    }

    this.tournoiService.createTournoi(tournoiRequest).subscribe(response => {
      this.dialogRef.close(true);
      this.data.confirmationFunc();

    });

  }



  dateFormatValidator() {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      // Regex pour valider le format YYYY-MM-DD
      const dateRegex = /^\d{4}-\d{2}-\d{2}$/;

      if (!dateRegex.test(control.value)) {
        return { invalidDateFormat: true };
      }

      // Vérifier que c'est une date valide
      const date = new Date(control.value);
      if (isNaN(date.getTime())) {
        return { invalidDateFormat: true };
      }

      // Vérifier que le format correspond exactement
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const formattedDate = `${year}-${month}-${day}`;

      if (formattedDate !== control.value) {
        return { invalidDateFormat: true };
      }


      return null;
    };
  }
}
