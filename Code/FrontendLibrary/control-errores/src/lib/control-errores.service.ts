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
 * Trata de desempaquetar un error, verificando si tiene la propiedad de angular ngOriginalError, la cual crea una copia del error antes de ser manipulado.
 *
 * @param error El error a desempaquetar.
 * @return Si el error tiene la propiedad ngOriginalError, devuelve el valor de dicha propiedad; de lo contrario, devuelve el error original.
 */
function tryToUnwrapZonejsError(error: unknown): unknown | Error {
  return error && (error as { ngOriginalError: Error }).ngOriginalError
    ? (error as { ngOriginalError: Error }).ngOriginalError
    : error;
}

/**
 * Extrae información del error de una respuesta HTTP.
 *
 * @param error La respuesta HTTP.
 * @return Si el error es un objeto que cumple con la interfaz Error, devuelve el objeto; si es un evento de error y tiene un mensaje, devuelve el mensaje; si es una cadena, devuelve una cadena que indica el código de estado y el cuerpo de la respuesta; de lo contrario, devuelve el error original.
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
 * Comprueba si un valor es un objeto de tipo Error o no.
 *
 * @param value El valor a comprobar.
 * @return True si el valor es un objeto de tipo Error, False en caso contrario.
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
 * Crea un objeto que contiene información sobre un error, incluido el mensaje de error y el callstack.
 *
 * @param error El error a analizar.
 * @return Un objeto que contiene el mensaje de error y el callstack del error.
 */
export function crearMensajeObjeto(error: Error) {
  //Obtiene los stack del error
  const stack: any = error.stack;
  if (!stack) {
  }
  const lines: string[] = stack.split('\n');

  const methodRegex: any = /at (.*) \((.*):(\d+):(\d+)\)/;
  //Los separa por linea a cada error
  let lineasAt: string = '';
  lines.forEach((line) => {
    lineasAt += line;
  });
  //retorna el mensaje del error y el callstack
  const errorInfo: errorInfo = {
    message: error.toString(),
    method: lineasAt,
  };

  return errorInfo;
}

/**
 * Esta clase implementa la interfaz ErrorHandler y se encarga de manejar los errores que se producen
 * en la aplicación. Proporciona una función para extraer información sobre el error y generar un mensaje
 * de error para mostrar al usuario.
 */
@Injectable({
  providedIn: 'root',
})
export class ControlErroresService implements ErrorHandler {
  /**
   * Constructor de la clase ControlErroresService.
   */
  constructor() {}

  /**
   * Esta función se encarga de manejar los errores que se producen en la aplicación.
   * Si el error no es una HttpErrorResponse, extrae información sobre el error y genera un mensaje
   * de error utilizando la función crearMensajeObjeto(). Si es una HttpErrorResponse, extrae información
   * sobre el error utilizando la función extraerHttpErrro() y devuelve un mensaje de error vacío.
   *
   * @param error El error que se ha producido.
   * @returns Una tupla que contiene información sobre el error y un mensaje de error para mostrar al usuario.
   */
  handleError(error: Error): [unknown, unknown] {
    if (!(error instanceof HttpErrorResponse)) {
      return [this.extractorErrorTipo(error), crearMensajeObjeto(error)];
    } else {
      return [this.extractorErrorTipo(error), ''];
    }
  }

  /**
   * Esta función se encarga de extraer información sobre el error dependiendo del tipo que sea.
   * Si el error es una HttpErrorResponse, utiliza la función extraerHttpErrro() para extraer información
   * sobre el error. Si el error es una cadena o un objeto de error, simplemente devuelve el error tal cual.
   *
   * @param errorCandidate El error del que se quiere extraer información.
   * @returns La información extraída del error.
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
