import { Time } from '@angular/common';
export interface AplicacionErrorDto {
  tituloError: string;
  descripcionError: string;
  nombreAplicacion: string;
  correoUsuario?: string;
  horaError: Date;
  ipUsuario: string;
  navegadorUsuario: string;
}
export interface TrazabilidadCodigoDto {
  trazaError: string;
}
