import { AplicacionErrorDto, Issue, TrazabilidadCodigoDto } from './interfaces';
import { observable, Observable } from 'rxjs';
import { HttpClient, HttpXhrBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from './environment/environment';
import { XhrFactory } from '@angular/common';

const url = environment.url;

@Injectable({
  providedIn: 'root',
})
export class ServicehttpAPIError {
  public baseUrl = url;
  constructor(private http: HttpClient) {}

  /**  An application error persists in the FrontEnd, along with its traceability and user actions.
@param {Object} applicationError - Object representing the application error.
@param {Object} traceabilityError - Object representing the traceability of the error.
@param {Array} userActions - Array of objects representing user actions.
@returns {Promise} A promise that resolves to the request response data.
**/
  persistAplicacionErrorFrontEnd(
    aplicacionError: object,
    trazabilidadError: object,
    accionesUsuario: Array<object>
  ): Observable<any> {
    const payload = {
      aplicacionErrorDto: aplicacionError,
      trazabilidadCodigoDto: trazabilidadError,
      accionesUsuarioDto: accionesUsuario,
    };
    console.log(payload);
    return this.http.post(
      `${this.baseUrl}aplicacionFrontEndError/save`,
      payload
    );
  }

  /**

Stores traceability and user actions for a specific application error.
@param {number} applicationError - The ID of the application error.
@param {Object} applicationError - Object representing the application error.
@param {Object} tracebackError - Object representing the error traceback.
@param {Array} useractions - Array of objects representing user actions.
@returns {Promise} A promise that resolves to the request response data.
*/
  saveTrazabilitiyandUserevents(
    idaplicacionError: number,

    aplicacionError: object,
    trazabilidadError: object,
    accionesUsuario: Array<object>
  ): Observable<any> {
    const payload = {
      aplicacionErrorDto: aplicacionError,
      trazabilidadCodigoDto: trazabilidadError,
      accionesUsuarioDto: accionesUsuario,
    };
    console.log(payload);
    return this.http.post(
      `${this.baseUrl}saveTrazabilitiyandUserevents/${idaplicacionError}`,
      payload
    );
  }

  /**

This function sends a POST request to the specified URL ${this.baseUrl}createIssue/save with the given issue object using
the Angular http client instance. It returns an Observable that can be subscribed to for handling the response from the server.
@param {Issue} issue - The issue object that contains the information to be saved.
@return {Observable<any>} An Observable that emits the response from the server when the request is complete.
*/
  saveApiJira(issue: Issue): Observable<any> {
    return this.http.post(`${this.baseUrl}createIssue/save`, issue);
  }

  /**

This function sends a GET request to the specified URL 'https://api.ipify.org?format=json' using the Angular http client instance.
It returns an Observable that can be subscribed to for handling the response from the server, which contains the client's IP address.
@return {Observable<any>} An Observable that emits the client's IP address when the request is complete.
*/
  obtenerDireccionIP(): Observable<any> {
    return this.http.get('https://api.ipify.org?format=json');
  }
}

/**

Class that implements the XhrFactory interface to provide a custom factory for Angular's HttpClient.
*/

export class MyXhrFactory implements XhrFactory {
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
export function sendAPIFront(
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

Sends an API request to save user events on the backend.
@param applicationErrorId - ID of the application error.
@param applicationError - Object containing information about the application error.
@param traceabilityCode - Traceability code for tracking the error.
@param userEvents - Object containing information about user events.
@returns An Observable that emits an API response.
*/
export function sendAPIBackend(
  idaplicacionError: number,
  aplicacionError: AplicacionErrorDto,
  trazabilidad_codigo: TrazabilidadCodigoDto,
  eventosUsuario: any
) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.saveTrazabilitiyandUserevents(
    idaplicacionError,
    aplicacionError,
    trazabilidad_codigo,
    eventosUsuario
  );
}

/**

This function sends a Jira issue to the server for saving, by creating an instance of MyXhrFactory, HttpXhrBackend, and ServicehttpAPIError
and then calling the saveApiJira function of ServicehttpAPIError with the given issue object. It returns an Observable that can be
subscribed to for handling the response from the server.
@param {Issue} issue - The issue object that contains the information to be saved.
@return {Observable<any>} An Observable that emits the response from the server when the request is complete.
*/
export function sendJira(issue: Issue) {
  const xhrFactory = new MyXhrFactory();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.saveApiJira(issue);
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

This function returns the client's IP address using an instance of ServicehttpAPIError that communicates with the server
via an HttpClient instance that uses an HttpXhrBackend which in turn uses a MyXhrFactory1 to create XMLHttpRequest instances.
@return {Promise<string>} A promise that resolves with the client's IP address if the request is successful, or rejects
with an error if the request fails.
*/

export function getDireccionIp() {
  const xhrFactory = new MyXhrFactory1();
  const httpBackend = new HttpXhrBackend(xhrFactory);
  const serviceApi = new ServicehttpAPIError(new HttpClient(httpBackend));
  return serviceApi.obtenerDireccionIP();
}
