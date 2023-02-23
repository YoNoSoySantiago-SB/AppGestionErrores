import { Time } from '@angular/common';
export interface aplicacion_error {
  tituloError: string;
  descripcionError: string;
  nombreAplicacion: string;
  correoUsuario?: string;
  ip: string;
  navegador: string;
}
export interface trazabilidad_codigo {
  tiempo?: Time;
  trazaError: string;
  categoria: string;
}
