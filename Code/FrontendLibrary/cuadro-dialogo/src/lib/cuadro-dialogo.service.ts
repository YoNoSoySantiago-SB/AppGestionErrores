import { saveError } from 'event-logs';
import { createError } from 'control-errores';
import { Injectable, ErrorHandler, NgZone } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AlertDialog } from './cuadro-dialogo.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

export class getIpJs {
  /**

Method that allows to obtain the client's IP address
using an AJAX request to the ipify API.
@param callback - function that will be called once the IP address has been obtained
@returns void
*/
  obtenerDireccionIP(callback: (direccionIP: string) => void) {
    const solicitud = new XMLHttpRequest();
    solicitud.onreadystatechange = () => {
      if (solicitud.readyState === 4 && solicitud.status === 200) {
        const datos = JSON.parse(solicitud.responseText);
        callback(datos.ip);
      }
    };
    solicitud.open('GET', 'https://api.ipify.org?format=json', true);
    solicitud.send();
  }
}
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
    let time = new Date();
    let trazabilidad: any;
    let mensajeError: any;
    let idBackend: any;
    let navegator = navigator.userAgent;
    let trazaStatus = getCallStackhtpp();
    let origen: string;
    // get Id addres
    const instancia = new getIpJs();
    instancia.obtenerDireccionIP((direcionIp) => {
      this.ip = direcionIp;
      const eventosUsuario = saveError(err.message);
      let status: number;

      if (getCallStackhtpp().length > 0 && err instanceof HttpErrorResponse) {
        status = err.status;
        if (status === 409) {
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

      // Opens a modal dialog to report the error to the user.

      this.ngZone.run(() => {
        this.dialog.open(AlertDialog, {
          data: {
            icon: 'Error',
            message: err.message,
            buttonText: 'Reportar',
            ipUsuario: this.ip,
            trazabilidad: trazabilidad,
            mensajeobject: mensajeError,
            tiempo: time,
            navegadorUsuario: navegator,
            eventosUsuario: eventosUsuario,
            status: status,
            idBackend: idBackend,
            origen: origen,
          },
        });
      });
      // Clears the error traceability to prevent errors in future HTTP requests.

      setCallStackHttp('');
    });
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
