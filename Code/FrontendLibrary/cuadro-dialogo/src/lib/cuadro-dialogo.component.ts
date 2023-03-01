import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Component, Inject, Injectable, OnInit, NgZone } from '@angular/core';
import { AplicacionErrorDto, TrazabilidadCodigoDto } from './interfaces';
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
  ipUsuario: string = '';
  //Trazabilidad del error
  trazabilidad: string = '';
  //Mensaje del objeto
  mensajeobject: string = '';
  //Tiempo en el que sucedio el problema
  tiempo: Date = new Date();
  //navegador y sistema operativo que ocurrio
  navegadorUsuario: string = '';
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
  showDialog: boolean = false;
  errorDialog: boolean = false;
  public resp: any;

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
      ipUsuario: string;
      mensajeobject: string;
      trazabilidad: string;
      tiempo: Date;
      navegadorUsuario: string;
      eventosUsuario: any;
      status: number;
      idBackend: number;
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
  }
  //Metodo que se ejecuta para cerrar el dialogo y mandar la información del error al backend
  async enviar() {
    let aplicacionError: AplicacionErrorDto;
    aplicacionError = {
      tituloError: this.nombre,
      descripcionError: this.descripcion,
      nombreAplicacion: '',
      correoUsuario: this.email,
      horaError: this.tiempo,
      ipUsuario: this.ipUsuario,
      navegadorUsuario: this.navegadorUsuario,
    };

    let trazabilidadCodigo: TrazabilidadCodigoDto;
    trazabilidadCodigo = {
      trazaError: this.trazabilidad,
    };

    //Evalua si el error viene de backend con status 409
    if (this.status == 409) {
      sendAPIBackend(
        this.idBackend,
        trazabilidadCodigo,
        this.eventosUsuario
      ).subscribe({
        next: (response) => {
          //Muestra la solicitud exitosa y el id del error

          this.resp = this.idBackend;
          response;
          this.showDialog = true;
        },
        error: (err) => {
          //Muestra la solicitud exitosa y el id del error
          this.errorDialog = true;
          err;
        },
      });
    } else {
      //En caso de que el error sea de front lo persistira como de front y hace el llamado al servicio necesario
      sendAPIFront(
        aplicacionError,
        trazabilidadCodigo,
        this.eventosUsuario
      ).subscribe({
        next: (response) => {
          //Muestra la solicitud exitosa y el id del error
          this.resp = response;
          this.showDialog = true;
        },
        error: (err) => {
          //Muestra que hubo error en la solicitud
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
 * Envía una solicitud de API para guardar los eventos del usuario en el backend.
 * @param idaplicacionError - ID del error de la aplicación.
 * @param trazabilidad_codigo - Código de trazabilidad para el seguimiento del error.
 * @param eventosUsuario - Objeto que contiene información sobre los eventos del usuario.
 * @returns Un Observable que emite una respuesta de la API.
 */
function sendAPIBackend(
  idaplicacionError: number,
  trazabilidad_codigo: TrazabilidadCodigoDto,
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
