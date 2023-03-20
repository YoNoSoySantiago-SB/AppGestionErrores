import { MatDialog } from '@angular/material/dialog';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Injectable, NgZone } from '@angular/core';
import { catchError, Observable, of, tap, throwError } from 'rxjs';
import { saveRequestHTTP } from 'event-logs';
import { crearCuadroError } from './cuadro-dialogo.service';

@Injectable()
export class MyInterceptor implements HttpInterceptor {
  constructor(private matDialog: MatDialog, private ngZone: NgZone) {}
  /**
   * Intercepts an outgoing HTTP request and logs the request method and URL.
   * @param {HttpRequest<any>} req - The outgoing HTTP request.
   * @param {HttpHandler} next - The next interceptor in the chain, or the backend if no more interceptors remain.
   * @returns {Observable<HttpEvent<any>>} An observable of the HTTP events, including the response.
   */
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    this.logRequest(req.method, req.url);

    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        //Manejo de errores que traerá el backend
        if (err.status >= 500 || err.status == 0) {
          const errorWithStack = new Error(err.message);
          //create cuadroError capture the HttpErrorResponse
          crearCuadroError(
            this.matDialog,
            this.ngZone,
            errorWithStack.stack
          ).handleError(err);
          throw err;
        } else {
          throw err;
        }
        // return httpErrorResponse
      })
    );
  }

  /**
   * Logs the request method and URL.
   * @param {string} method - The HTTP method of the request.
   * @param {string} url - The URL of the request.
   */
  private logRequest(method: string, url: string) {
    var request = `${method} a ${url}`;
    saveRequestHTTP(request);
  }
}
