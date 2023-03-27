import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Component, Inject, Injectable, OnInit, NgZone } from '@angular/core';
import { sendJira } from './httpservice';
import { getnameApp, getnameKey, getnameParent } from './getNameApp';

@Component({
  selector: 'app-alert-dialog',
  templateUrl: './cuadro-dialogo.component.html',
  styleUrls: ['./cuadro-dialogo.component.css'],
})
/**

Represents an alert dialog that can be used to display an error message and perform actions to report or close the dialog.
*/
export class AlertDialog {
  //In case the message is not found.
  message: string = 'An unspecified error has occurred';
  //Icon show
  icon: string = '';
  //Text default
  buttonText = 'Ok';
  nombre = '';
  descripcion = '';

  //ticketJira
  ticket: string = '';
  //idbackenError
  idBackend: number = -1;
  //muestra el dialogo cuando se guarda el ticket
  showDialog: boolean = false;
  //muestra cuando ocurre un error al crear el tick
  errorDialog: boolean = false;
  //Se muestra cuando no logra guardar el error
  errorService: boolean = false;
  public resp: any;

  /**

Creates a new instance of AlertDialog.
@param data The data to initialize the dialog. Includes a message, an optional icon, text for the button, tracking information, user IP address, message object, time when the error occurred, browser used, and user events.
@param dialogRef A reference to the dialog that is being displayed.
*/
  constructor(
    @Inject(MAT_DIALOG_DATA)
    private data: {
      message: string;
      icon: string;
      buttonText: string;
      idBackend: number;
      errorService: boolean;
    },
    private dialogRef: MatDialogRef<AlertDialog>
  ) {
    if (data?.icon) this.icon = data.icon;
    if (data?.message) this.message = data.message;
    if (data?.buttonText) this.buttonText = data.buttonText;
    if (data?.idBackend) this.idBackend = data.idBackend;
    if (data.errorService) this.errorDialog = true;
  }
  //Method that is executed to close the dialog and send the error information to the backend.
  async enviar() {
    let issue: any;
    let descriptionSend: string = `${this.nombre} \n${
      this.descripcion
    } \nError presentado en la aplicación: ${getnameApp()} \nIdentificado con código: ${
      this.idBackend
    }`;
    //Estructura a mandar a jira
    issue = {
      summary: 'Error presentado',
      description: descriptionSend,
      projectname: getnameKey(),
      parent: getnameParent(),
      idError: this.idBackend,
    };
    //Mandar a JIRA
    sendJira(issue).subscribe(
      (data) => {
        this.ticket = data.key;
        this.resp = this.idBackend;
        this.showDialog = true;
      },
      (error) => {
        this.errorDialog = true;
      }
    );
  }

  //Close the matDialog
  cerrar() {
    this.dialogRef.close();
  }
}
