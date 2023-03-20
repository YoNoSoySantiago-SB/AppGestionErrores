import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Component, Inject, Injectable, OnInit, NgZone } from '@angular/core';
import { AplicacionErrorDto, TrazabilidadCodigoDto } from './interfaces';
import { ServicehttpAPIError } from './httpservice';
import { HttpClient, HttpBackend, HttpXhrBackend } from '@angular/common/http';
import { DatePipe, Time, XhrFactory } from '@angular/common';
import { getnameApp } from './getNameApp';

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
  //Ip user addres
  ipUsuario: string = '';
  //Trace
  trazabilidad: string = '';
  //Message object
  mensajeobject: string = '';
  //Time error
  tiempo: Date = new Date();
  //navegator
  navegadorUsuario: string = '';
  //Events user before error
  eventosUsuario: any;
  //NameUser
  nombre: string = '';
  //Email del usuario
  email: string = '';
  //Description
  descripcion: string = '';
  //Status
  status: number = -1;
  idBackend: number = -1;
  showDialog: boolean = false;
  errorDialog: boolean = false;
  origen: string = '';
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
      ipUsuario: string;
      mensajeobject: string;
      trazabilidad: string;
      tiempo: Date;
      navegadorUsuario: string;
      eventosUsuario: any;
      status: number;
      idBackend: number;
      origen: string;
    },
    private dialogRef: MatDialogRef<AlertDialog>
  ) {
    if (data?.icon) this.icon = data.icon;
    if (data?.message) this.message = data.message;
    if (data?.buttonText) this.buttonText = data.buttonText;
    if (data?.ipUsuario) this.ipUsuario = data.ipUsuario;
    if (data?.trazabilidad) this.trazabilidad = data.trazabilidad;
    if (data?.mensajeobject) this.mensajeobject = data.mensajeobject;
    if (data?.tiempo) this.tiempo = data.tiempo;
    if (data?.navegadorUsuario) this.navegadorUsuario = data.navegadorUsuario;
    if (data?.eventosUsuario) this.eventosUsuario = data.eventosUsuario;
    if (data?.status) this.status = data.status;
    if (data?.idBackend) this.idBackend = data.idBackend;
    if (data?.origen) this.origen = data.origen;
  }
  //Method that is executed to close the dialog and send the error information to the backend.
  async enviar() {
    let aplicacionError: AplicacionErrorDto;

    aplicacionError = {
      tituloError: this.nombre,
      descripcionError: this.descripcion,
      nombreAplicacion: getnameApp(),
      horaError: this.tiempo.toISOString(),
      ipUsuario: this.ipUsuario,
      navegadorUsuario: this.navegadorUsuario,
    };

    let trazabilidadCodigo: TrazabilidadCodigoDto;
    trazabilidadCodigo = {
      trazaError: this.trazabilidad,
      origen: this.origen,
    };
    //Evaluates if the error comes from the backend with status 409.
    if (this.status == 409) {
      sendAPIBackend(
        this.idBackend,
        aplicacionError,
        trazabilidadCodigo,
        this.eventosUsuario
      ).subscribe({
        next: (response) => {
          //Displays the successful request and the error ID.
          this.resp = this.idBackend;
          response;
          this.showDialog = true;
        },
        error: (err) => {
          //Displays that there was an error in the request.
          this.errorDialog = true;
          err;
        },
      });
    } else {
      //In case the error is from the frontend, it will persist it as a frontend error and make the necessary service call.
      sendAPIFront(
        aplicacionError,
        trazabilidadCodigo,
        this.eventosUsuario
      ).subscribe({
        next: (response) => {
          //Displays the successful request and the error ID.
          this.resp = response;
          this.showDialog = true;
        },
        error: (err) => {
          //Displays that there was an error in the request.
          this.errorDialog = true;
        },
      });
    }
  }

  cerrar() {
    this.dialogRef.close();
  }
}

/**

Class that implements the XhrFactory interface to provide a custom factory for Angular's HttpClient.
*/
class MyXhrFactory implements XhrFactory {
  /**

Creates and returns a new instance of XMLHttpRequest.
@returns A new instance of XMLHttpRequest.
*/
  build(): XMLHttpRequest {
    return new XMLHttpRequest();
  }
}

/**

Sends an API request to save application errors and user events on the frontend.
@param applicationError - Object containing information about the application error.
@param traceabilityCode - Traceability code for tracking the error.
@param userEvents - Object containing information about user events.
@returns An Observable that emits an API response.
*/
function sendAPIFront(
  aplicacionError: AplicacionErrorDto,
  trazabilidad_codigo: TrazabilidadCodigoDto,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.persistAplicacionErrorFrontEnd(
    aplicacionError,
    trazabilidad_codigo,
    eventosUsuario
  );
}

/**

Class that implements the XhrFactory interface to provide a custom factory for Angular's HttpClient.
*/
class MyXhrFactory1 implements XhrFactory {
  /**

Creates and returns a new instance of XMLHttpRequest.
@returns A new instance of XMLHttpRequest.
*/
  build(): XMLHttpRequest {
    return new XMLHttpRequest();
  }
}

/**

Sends an API request to save user events on the backend.
@param applicationErrorId - ID of the application error.
@param applicationError - Object containing information about the application error.
@param traceabilityCode - Traceability code for tracking the error.
@param userEvents - Object containing information about user events.
@returns An Observable that emits an API response.
*/
function sendAPIBackend(
  idaplicacionError: number,
  aplicacionError: AplicacionErrorDto,
  trazabilidad_codigo: TrazabilidadCodigoDto,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory1();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.saveTrazabilitiyandUserevents(
    idaplicacionError,
    aplicacionError,
    trazabilidad_codigo,
    eventosUsuario
  );
}
