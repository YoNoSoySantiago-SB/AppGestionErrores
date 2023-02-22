import { observable, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from './environment/environment';

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
      aplicacionError: aplicacionError,
      trazabilidadError: trazabilidadError,
      accionesUsuario: accionesUsuario,
    };
    return this.http.post(
      `${this.baseUrl}aplicacionFrontEndError/save`,
      payload
    );
  }

  /**

Stores traceability and user actions for a specific application error.
@param {number} applicationError - The ID of the application error.
@param {Object} tracebackError - Object representing the error traceback.
@param {Array} useractions - Array of objects representing user actions.
@returns {Promise} A promise that resolves to the request response data.
*/
  saveTrazabilitiyandUserevents(
    idaplicacionError: number,
    trazabilidadError: object,
    accionesUsuario: Array<object>
  ): Observable<any> {
    const payload = {
      trazabilidadError: trazabilidadError,
      accionesUsuario: accionesUsuario,
    };
    return this.http.post(
      `${this.baseUrl}saveTrazabilitiyandUserevents/${idaplicacionError}`,
      payload
    );
  }
}
