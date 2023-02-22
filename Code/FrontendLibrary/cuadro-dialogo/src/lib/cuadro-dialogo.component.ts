import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, Injectable, OnInit } from '@angular/core';
import { aplicacion_error, trazabilidad_codigo } from './interfaces';
import { ServicehttpAPIError } from './httpservice';
import { HttpClient, HttpBackend, HttpXhrBackend } from '@angular/common/http';
import { XhrFactory } from '@angular/common';

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
  tiempo: string = '';
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
      tiempo: string;
      navegador: string;
      eventosUsuario: any;
      status: number;
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
  }

  //Metodo que se ejecuta para mandar la información al backend y el reporte
  reportar() {
    console.log(this.data);
    let aplicacionError: aplicacion_error;
    aplicacionError = {
      titulo_error: this.message,
      descripcion_error: this.descripcion,
      nombre_aplicacion: '',
      correo_usuario: this.email,
    };

    let trazabilidadCodigo: trazabilidad_codigo;
    trazabilidadCodigo = {
      time: this.tiempo,
      frame: this.trazabilidad,
      category: '',
    };

    if (this.status == 409) {
      sendAPIBackend(aplicacionError, trazabilidadCodigo, this.eventosUsuario);
    } else {
      sendAPIFront(1, trazabilidadCodigo, this.eventosUsuario);
    }

    console.log(aplicacionError);
    console.log(trazabilidadCodigo);
    this.dialogRef.close();
  }
  //Metodo que se ejecuta para cerrar el dialogo y mandar la información del error al backend

  cerrar() {
    let aplicacionError: aplicacion_error;
    aplicacionError = {
      titulo_error: this.message,
      descripcion_error: this.descripcion,
      nombre_aplicacion: '',
      correo_usuario: this.email,
    };

    let trazabilidadCodigo: trazabilidad_codigo;
    trazabilidadCodigo = {
      time: this.tiempo,
      frame: this.trazabilidad,
      category: '',
    };

    this.dialogRef.close();
  }
}

class MyXhrFactory implements XhrFactory {
  build(): XMLHttpRequest {
    return new XMLHttpRequest();
  }
}
function sendAPIBackend(
  aplicacionError: aplicacion_error,
  trazabilidad_codigo: trazabilidad_codigo,
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

function sendAPIFront(
  idaplicacionError: number,
  trazabilidad_codigo: trazabilidad_codigo,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.saveTrazabilitiyandUserevents(
    idaplicacionError,
    trazabilidad_codigo,
    eventosUsuario
  );
}
