import {
  Component,
  ErrorHandler,
  ComponentFactoryResolver,
  ViewContainerRef,
  Injectable,
  ViewChild,
  Injector,
  ReflectiveInjector,
  Directive,
  Inject,
} from '@angular/core';
import { errorInfo, metodos } from './interfaz';
import {
  HttpErrorResponse,
  HttpClient,
  HttpHandler,
} from '@angular/common/http';
/**

Tries to unpack an error, checking if it has the angular property ngOriginalError, which creates a copy of the error before it is manipulated.
@param error The error to unpack.
@return If the error has the ngOriginalError property, it returns the value of that property; otherwise, it returns the original error.
*/
function tryToUnwrapZonejsError(error: unknown): unknown | Error {
  return error && (error as { ngOriginalError: Error }).ngOriginalError
    ? (error as { ngOriginalError: Error }).ngOriginalError
    : error;
}

/**

Extracts error information from an HTTP response.
@param error The HTTP response.
@return If the error is an object that conforms to the Error interface, returns the object; if it is an error event and has a message, returns the message; if it is a string, returns a string indicating the status code and body of the response; otherwise, returns the original error.
*/
function extraerHttpErrro(error: HttpErrorResponse): string | Error {
  if (EsErrorOErrorObjeto(error.error)) {
    return error.error;
  }

  if (error.error instanceof ErrorEvent && error.error.message) {
    return error.error.message;
  }
  if (typeof error.error === 'string') {
    return `Servidor retorno el codigo  ${error.status} con cuerpo "${error.error}"`;
  }

  return error;
}

type ErrorCandidate = {
  name?: unknown;
  message?: unknown;
  stack?: unknown;
};
/**

Checks if a value is an Error object or not.
@param value The value to check.
@return True if the value is an Error object, False otherwise.
*/
function EsErrorOErrorObjeto(value: unknown): value is Error {
  if (value instanceof Error) {
    return true;
  }

  if (value === null || typeof value !== 'object') {
    return false;
  }

  const candidate = value as ErrorCandidate;

  return (
    typeof candidate.name === 'string' &&
    typeof candidate.name === 'string' &&
    typeof candidate.message === 'string' &&
    (undefined === candidate.stack || typeof candidate.stack === 'string')
  );
}

/**

Creates an object that contains information about an error, including the error message and call stack.
@param error The error to analyze.
@return An object containing the error message and call stack of the error.
*/
export function crearMensajeObjeto(error: Error) {
  //get stack error
  const stack: any = error.stack;
  if (!stack) {
  }
  const lines: string[] = stack.split('\n');

  const methodRegex: any = /at (.*) \((.*):(\d+):(\d+)\)/;

  let lineasAt: string = '';
  lines.forEach((line) => {
    lineasAt += line;
  });
  //return message and callstack
  const errorInfo: errorInfo = {
    message: error.toString(),
    method: lineasAt,
  };

  return errorInfo;
}

/**

This class implements the ErrorHandler interface and is responsible for handling errors that occur
in the application. It provides a function to extract information about the error and generate an error
message to display to the user.
*/
@Injectable({
  providedIn: 'root',
})
export class ControlErroresService implements ErrorHandler {
  /**

Constructor of the ControlErroresService class.
*/
  constructor() {}

  /**

This function handles errors that occur in the application.
If the error is not a HttpErrorResponse, it extracts information about the error and generates an error message
using the function createMessageObject(). If it is a HttpErrorResponse, it extracts information
about the error using the function extractHttpError() and returns an empty error message.
@param error The error that occurred.
@returns A tuple containing information about the error and an error message to display to the user.
*/
  handleError(error: Error): [unknown, unknown] {
    if (!(error instanceof HttpErrorResponse)) {
      return [this.extractorErrorTipo(error), crearMensajeObjeto(error)];
    } else {
      return [this.extractorErrorTipo(error), ''];
    }
  }

  /**

* This function is responsible for extracting information from the error depending on its type.
* If the error is an HttpErrorResponse, it uses the function extractHttpError() to extract information
* about the error. If the error is a string or an error object, it simply returns the error as is.
*@param errorCandidate The error from which to extract information.
*@returns The extracted information from the error.
*/
  protected extractorErrorTipo(errorCandidate: unknown): unknown {
    const error = tryToUnwrapZonejsError(errorCandidate);

    if (error instanceof HttpErrorResponse) {
      return extraerHttpErrro(error);
    }

    if (typeof error === 'string' || EsErrorOErrorObjeto(error)) {
      return error;
    }

    // No extrae nada, devuelve el error por defecto
    return null;
  }
}

export function createError(config?: Error): ControlErroresService {
  return new ControlErroresService();
}
