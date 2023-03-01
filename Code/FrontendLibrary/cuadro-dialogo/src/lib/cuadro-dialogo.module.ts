import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {
  MatDialogModule,
  MAT_DIALOG_DEFAULT_OPTIONS,
} from '@angular/material/dialog';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertDialog } from './cuadro-dialogo.component';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { crearCuadroError } from './cuadro-dialogo.service';
import { saveError } from 'event-logs';
import { ControlErroresModule } from 'control-errores';

@NgModule({
  declarations: [AlertDialog],
  imports: [
    MatDialogModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    ControlErroresModule,
    HttpClientModule,
  ],
  exports: [AlertDialog],
  providers: [
    {
      provide: MAT_DIALOG_DEFAULT_OPTIONS,
      useValue: {
        disableClose: true,
        hasBackdrop: true,
      },
    },
  ],
})
export class CuadroDialogoModule {}
