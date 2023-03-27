import { saveError } from 'event-logs';
import { createError } from 'control-errores';
import { Injectable, ErrorHandler, NgZone } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AlertDialog } from './cuadro-dialogo.component';
import {
  HttpClient,
  HttpErrorResponse,
  HttpXhrBackend,
} from '@angular/common/http';
import { getDireccionIp, sendAPIBackend, sendAPIFront } from './httpservice';
import { AplicacionErrorDto, TrazabilidadCodigoDto } from './interfaces';
import { getnameApp } from './getNameApp';
import { getUserInfo } from './getUserLoggedInfo';

let callstack: string = '';

@Injectable()
/**

Custom error handling service for the application.
Extends the ErrorHandler service provided by Angular and
overrides the handleError() method.
*/
export class ErrorHandlerService extends ErrorHandler {
  /*
User's IP address.
*/
  ip: any;

  /**

/**
Creates an instance of the service.
@param dialog Angular's MatDialog service to display a modal dialog.
@param ngZone Angular's NgZone service to execute code within the Angular zone.
*/
  constructor(private dialog: MatDialog, private ngZone: NgZone) {
    super();
  }

  /**

Handles an error and displays a modal dialog to the user to report the error.
@param err Error object or HttpErrorResponse that occurred.
*/
  override handleError(err: any): void {
    // Obtiene la hora actual y la información del navegador
    let aplicacionError: AplicacionErrorDto;
    let time = new Date();
    let trazabilidad: any;
    let mensajeError: any;
    let idBackend: any;
    let trazabilidadCodigo: TrazabilidadCodigoDto;
    let navegator = navigator.userAgent;
    let trazaStatus = getCallStackhtpp();
    let origen: string;

    const eventosUsuario = saveError(err.message);
    let status: number = -1;
    if (getCallStackhtpp().length > 0 && err instanceof HttpErrorResponse) {
      status = err.status;
      if (status >= 500) {
        idBackend = err.error;
        idBackend = idBackend.id_application_error;
        trazabilidad = trazaStatus;
        mensajeError = '';
      } else {
        trazabilidad = trazaStatus;
        mensajeError = createError().handleError(err)[1];
      }
      origen = 'Backend';
    } else {
      trazabilidad = createError().handleError(err)[0];
      if (trazabilidad.status === 0) {
        trazabilidad = trazaStatus;
        origen = 'Frontend';
      } else {
        trazabilidad = trazabilidad.stack;
        origen = 'Frontend';
      }
      mensajeError = createError().handleError(err)[1];
    }
    // get Id addres
    getDireccionIp().subscribe(
      (direcionIp) => {
        this.ip = direcionIp.ip;

        aplicacionError = {
          tituloError: '',
          descripcionError: '',
          nombreAplicacion: getnameApp(),
          horaError: time.toISOString(),
          ipUsuario: this.ip,
          navegadorUsuario: navegator,
          userinfo: getUserInfo(),
        };

        trazabilidadCodigo = {
          trazaError: trazabilidad,
          origen: origen,
        };

        if (status >= 500) {
          sendAPIBackend(
            idBackend,
            aplicacionError,
            trazabilidadCodigo,
            eventosUsuario
          ).subscribe({
            next: (response) => {
              //Displays the successful request and the error ID.
              // Opens a modal dialog to report the error to the user.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: idBackend,
                    errorService: false,
                  },
                });
              });
            },
            error: (err) => {
              //Displays that there was an error in the request.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: '',
                    errorService: true,
                  },
                });
              });
            },
          });
        } else {
          sendAPIFront(
            aplicacionError,
            trazabilidadCodigo,
            eventosUsuario
          ).subscribe({
            next: (response) => {
              //Displays the successful request and the error ID.
              // Opens a modal dialog to report the error to the user.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: response,
                    errorService: false,
                  },
                });
              });
            },
            error: (err) => {
              //Displays that there was an error in the request.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: '',
                    errorService: true,
                  },
                });
              });
            },
          });
        }
        // Clears the error traceability to prevent errors in future HTTP requests.
        setCallStackHttp('');
      },
      (error) => {
        this.ip = 'No Disponible';
        aplicacionError = {
          tituloError: '',
          descripcionError: '',
          nombreAplicacion: getnameApp(),
          horaError: time.toISOString(),
          ipUsuario: this.ip,
          navegadorUsuario: navegator,
          userinfo: getUserInfo(),
        };

        trazabilidadCodigo = {
          trazaError: trazabilidad,
          origen: origen,
        };

        if (status >= 500) {
          sendAPIBackend(
            idBackend,
            aplicacionError,
            trazabilidadCodigo,
            eventosUsuario
          ).subscribe({
            next: (response) => {
              //Displays the successful request and the error ID.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: idBackend,
                    errorService: false,
                  },
                });
              });
            },
            error: (err) => {
              //Displays that there was an error in the request.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: '',
                    errorService: true,
                  },
                });
              });
            },
          });
        } else {
          sendAPIFront(
            aplicacionError,
            trazabilidadCodigo,
            eventosUsuario
          ).subscribe({
            next: (response) => {
              //Displays the successful request and the error ID.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: response,
                    errorService: false,
                  },
                });
              });
            },
            error: (err) => {
              //Displays that there was an error in the request.
              this.ngZone.run(() => {
                this.dialog.open(AlertDialog, {
                  data: {
                    icon: 'Error',
                    message: err.message,
                    buttonText: 'Reportar',
                    idBackend: '',
                    errorService: true,
                  },
                });
              });
            },
          });
        }
        // Clears the error traceability to prevent errors in future HTTP requests.
        setCallStackHttp('');
      }
    );
  }
}

/**

Method that receives the call stack of the http error to store.
@param callStackHttp The call stack of a http error to be stored.
*/
export function setCallStackHttp(callStackhtpp: string) {
  callstack = callStackhtpp;
}
/**

Method that returns the call stack of the http error.
@returns Returns the call stack of the http error.
*/
export function getCallStackhtpp() {
  return callstack;
}

/**

Create an error dialog using the MatDialog service and the NgZone object. If an error trace is provided, it is set in the ErrorHandlerService before creating the dialog.
@param dialog The MatDialog service used to create the error dialog.
@param ngZone The NgZone object used to execute the error dialog within the Angular zone.
@param errorTrace An optional error trace that is set in the ErrorHandlerService before creating the dialog.
@returns A new ErrorHandlerService object that is used to create the error dialog.
*/
export function crearCuadroError(
  dialog: MatDialog,
  ngZone: NgZone,
  errorTraz?: string
) {
  if (errorTraz) {
    setCallStackHttp(errorTraz);
    return new ErrorHandlerService(dialog, ngZone);
  } else {
    return new ErrorHandlerService(dialog, ngZone);
  }
}
