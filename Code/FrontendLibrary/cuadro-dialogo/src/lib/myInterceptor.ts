import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { saveRequestHTTP } from 'event-logs';

@Injectable()
export class MyInterceptor implements HttpInterceptor {
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
    return next.handle(req);
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
