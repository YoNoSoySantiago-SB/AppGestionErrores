import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { aplicacion_error, trazabilidad_codigo } from './interfaces';
import { ServicehttpAPIError } from './httpservice';
import { HttpClient, HttpBackend, HttpXhrBackend } from '@angular/common/http';
import { Time, XhrFactory } from '@angular/common';

@Component({
  selector: 'app-alert-dialog',
  templateUrl: './cuadro-dialogo.component.html',
  styleUrls: ['./cuadro-dialogo.component.css'],
})
/**

Representa un diálogo de alerta que se puede utilizar para mostrar un mensaje de error y realizar acciones para reportar o cerrar el diálogo.
*/
export class AlertDialog {
  //En caso de no encontrar el el mensaje
  message: string = 'An unspecified error has occurred';
  //Icono que aparecera
  icon: string = '';
  //Texto del boton por defecto
  buttonText = 'Ok';
  //Ip del usuario que presento el error
  ip: string = '';
  //Trazabilidad del error
  trazabilidad: string = '';
  //Mensaje del objeto
  mensajeobject: string = '';
  //Tiempo en el que sucedio el problema
  tiempo: Time = {
    hours: 0,
    minutes: 0,
  };
  //navegador y sistema operativo que ocurrio
  navegador: string = '';
  //Eventos de usuario que hizo antes del error
  eventosUsuario: any;
  //Nombre del usuario
  nombre: string = '';
  //Email del usuario
  email: string = '';
  //Descripcion del usuario
  descripcion: string = '';
  //Status
  status: number = -1;
  idBackend: number = -1;

  /**

Crea una nueva instancia de AlertDialog.
@param data Los datos para inicializar el diálogo. Incluye un mensaje, un icono opcional, un texto para el botón, información de seguimiento, la dirección IP del usuario, el objeto mensaje, la hora en que se produjo el error, el navegador utilizado, y eventos de usuario.
@param dialogRef Una referencia al cuadro de diálogo que se está mostrando.
*/
  constructor(
    @Inject(MAT_DIALOG_DATA)
    private data: {
      message: string;
      icon: string;
      buttonText: string;
      ip: string;
      mensajeobject: string;
      trazabilidad: string;
      tiempo: Time;
      navegador: string;
      eventosUsuario: any;
      status: number;
      idBackend: number;
    },
    private dialogRef: MatDialogRef<AlertDialog>
  ) {
    if (data?.icon) this.icon = data.icon;
    if (data?.message) this.message = data.message;
    if (data?.buttonText) this.buttonText = data.buttonText;
    if (data?.ip) this.ip = data.ip;
    if (data?.trazabilidad) this.trazabilidad = data.trazabilidad;
    if (data?.mensajeobject) this.mensajeobject = data.mensajeobject;
    if (data?.tiempo) this.tiempo = data.tiempo;
    if (data?.navegador) this.navegador = data.navegador;
    if (data?.eventosUsuario) this.eventosUsuario = data.eventosUsuario;
    if (data?.status) this.status = data.status;
    if (data?.idBackend) this.idBackend = data.idBackend;
  }

  //Metodo que se ejecuta para mandar la información al backend y el reporte
  reportar() {
    let aplicacionError: aplicacion_error;
    aplicacionError = {
      tituloError: this.message,
      descripcionError: this.descripcion,
      nombreAplicacion: '',
      correoUsuario: this.email,
      ip: this.ip,
      navegador: this.navegador,
    };

    let trazabilidadCodigo: trazabilidad_codigo;
    trazabilidadCodigo = {
      tiempo: this.tiempo,
      trazaError: this.trazabilidad,
      categoria: '',
    };

    if (this.status == 409) {
      console.log(sendAPIBackend(1, trazabilidadCodigo, this.eventosUsuario));
    } else {
      sendAPIFront(aplicacionError, trazabilidadCodigo, this.eventosUsuario);
    }

    console.log(trazabilidadCodigo);
    console.log(aplicacionError);
    this.dialogRef.close();
  }
  //Metodo que se ejecuta para cerrar el dialogo y mandar la información del error al backend
  cerrar() {
    let aplicacionError: aplicacion_error;
    aplicacionError = {
      tituloError: this.message,
      descripcionError: this.descripcion,
      nombreAplicacion: '',
      correoUsuario: this.email,
      ip: this.ip,
      navegador: this.navegador,
    };

    let trazabilidadCodigo: trazabilidad_codigo;
    trazabilidadCodigo = {
      tiempo: this.tiempo,
      trazaError: this.trazabilidad,
      categoria: '',
    };

    if (this.status == 409) {
      console.log(
        sendAPIBackend(this.idBackend, trazabilidadCodigo, this.eventosUsuario)
      );
    } else {
      console.log(
        sendAPIFront(aplicacionError, trazabilidadCodigo, this.eventosUsuario)
      );
    }

    this.dialogRef.close();
  }
}

/**
 * Clase que implementa la interfaz XhrFactory para proporcionar una fábrica personalizada para HttpClient de Angular.
 */
class MyXhrFactory implements XhrFactory {
  /**
   * Crea y devuelve una nueva instancia de XMLHttpRequest.
   * @returns Una nueva instancia de XMLHttpRequest.
   */
  build(): XMLHttpRequest {
    return new XMLHttpRequest();
  }
}

/**
 * Envía una solicitud de API para guardar los errores de la aplicación y los eventos del usuario en la parte frontal.
 * @param aplicacionError - Objeto que contiene información sobre el error de la aplicación.
 * @param trazabilidad_codigo - Código de trazabilidad para el seguimiento del error.
 * @param eventosUsuario - Objeto que contiene información sobre los eventos del usuario.
 * @returns Un Observable que emite una respuesta de la API.
 */
function sendAPIFront(
  aplicacionError: aplicacion_error,
  trazabilidad_codigo: trazabilidad_codigo,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi
    .persistAplicacionErrorFrontEnd(
      aplicacionError,
      trazabilidad_codigo,
      eventosUsuario
    )
    .subscribe({
      next: (response) => {
        response;
      },
      error: (err) => {
        err;
      },
    });
}

/**
 * Envía una solicitud de API para guardar los eventos del usuario en el backend.
 * @param idaplicacionError - ID del error de la aplicación.
 * @param trazabilidad_codigo - Código de trazabilidad para el seguimiento del error.
 * @param eventosUsuario - Objeto que contiene información sobre los eventos del usuario.
 * @returns Un Observable que emite una respuesta de la API.
 */
function sendAPIBackend(
  idaplicacionError: number,
  trazabilidad_codigo: trazabilidad_codigo,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi
    .saveTrazabilitiyandUserevents(
      idaplicacionError,
      trazabilidad_codigo,
      eventosUsuario
    )
    .subscribe({
      next: (response) => {
        response;
      },
      error: (err) => {
        err;
      },
    });
}
