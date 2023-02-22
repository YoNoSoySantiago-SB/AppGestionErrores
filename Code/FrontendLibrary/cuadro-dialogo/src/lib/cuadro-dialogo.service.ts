import { saveError } from 'event-logs';
import { createError } from 'control-errores';
import { Injectable, ErrorHandler, NgZone } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AlertDialog } from './cuadro-dialogo.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

export class getIpJs {
  /**
   * Método que permite obtener la dirección IP del cliente
   * utilizando una solicitud AJAX a la API de ipify.
   *
   * @param callback - función que se llamará una vez que se haya obtenido la dirección IP
   * @returns void
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

Servicio de manejo de errores personalizado para la aplicación.
Extiende el servicio ErrorHandler provisto por Angular y
sobrescribe el método handleError().
*/
export class ErrorHandlerService extends ErrorHandler {
  /*
Dirección IP del usuario.
*/
  ip: any;

  /**

Crea una instancia del servicio.
@param dialog Servicio MatDialog de Angular para mostrar un diálogo modal.
@param ngZone Servicio NgZone de Angular para ejecutar código dentro de la zona Angular.
*/
  constructor(private dialog: MatDialog, private ngZone: NgZone) {
    super();
  }

  /**

Maneja un error y muestra un diálogo modal al usuario para reportar el error.
@param err Objeto Error o HttpErrorResponse que se produjo.
*/
  override handleError(err: any): void {
    console.log(err);
    // Obtiene la hora actual y la información del navegador
    let time = new Date().toLocaleString();
    let navegator = navigator.userAgent;
    let trazabilidad: any;
    let mensajeError: any;
    let idBackend: any;
    // Obtiene la dirección IP del usuario usando un servicio externo
    const instancia = new getIpJs();
    instancia.obtenerDireccionIP((direcionIp) => {
      this.ip = direcionIp;
    });
    // Obtiene la trazabilidad del error y los eventos de usuario
    const eventosUsuario = saveError(err.message);
    let status: number;
    //Si es de backend mando trazabilidad ,eventos y id recibido en mensaje

    if (getCallStackhtpp().length > 0 && err instanceof HttpErrorResponse) {
      status = err.status;
      if (status === 409) {
        console.log('error 409 backend');
        idBackend = err.error;
        trazabilidad = getCallStackhtpp();

        mensajeError = '';
        console.log(mensajeError);
      } else {
        console.log('otro error');

        trazabilidad = getCallStackhtpp();
        mensajeError = createError().handleError(err)[1];
        console.log(mensajeError);
      }
    } else {
      console.log('object error');

      trazabilidad = createError().handleError(err)[0];
      mensajeError = createError().handleError(err)[1];
      console.log(mensajeError);
    }
    // Abre un diálogo modal para reportar el error al usuario

    this.ngZone.run(() => {
      this.dialog.open(AlertDialog, {
        data: {
          icon: 'Error',
          message: err.message,
          buttonText: 'Reportar',
          ip: '',
          trazabilidad: trazabilidad,
          mensajeobject: mensajeError,
          tiempo: time,
          navegador: navegator,
          eventosUsuario: eventosUsuario,
          status: status,
        },
      });
    });
    // Limpia la trazabilidad del error para evitar errores en futuras solicitudes HTTP

    setCallStackHttp('');
  }
}

/**
 * Metodo que recibe  el callstack  del error http para almacenar
 * @param callStackhtpp  call stack almacenado de un error http
 */
export function setCallStackHttp(callStackhtpp: string) {
  callstack = callStackhtpp;
}
/**
 *  Metodo que retorna el call stack del error http
 * @returns Retorna el callstack del error http
 */
export function getCallStackhtpp() {
  return callstack;
}

/**

Crea un cuadro de diálogo de error utilizando el servicio MatDialog y el objeto NgZone.
Si se proporciona un rastro de error, se establece en el servicio ErrorHandlerService antes de crear el cuadro de diálogo.
@param dialog El servicio MatDialog utilizado para crear el cuadro de diálogo de error.
@param ngZone El objeto NgZone utilizado para ejecutar el cuadro de diálogo de error dentro de la zona de Angular.
@param errorTraz Un rastro de error opcional que se establece en el servicio ErrorHandlerService antes de crear el cuadro de diálogo.
@returns Un nuevo objeto ErrorHandlerService que se utiliza para crear el cuadro de diálogo de error.
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
