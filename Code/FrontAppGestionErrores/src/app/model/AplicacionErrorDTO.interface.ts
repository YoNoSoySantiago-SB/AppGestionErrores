import { Time } from '@angular/common';

export interface AplicacionErrorDTO {
  idAplicacionError: string;
  tituloError: string;
  descripcionError: string;
  nombreAplicacion: string;
  correoUsuario: string;
  horaError: Time;
  navegadorUsuario: string;
  ipUsuario: string;
}
