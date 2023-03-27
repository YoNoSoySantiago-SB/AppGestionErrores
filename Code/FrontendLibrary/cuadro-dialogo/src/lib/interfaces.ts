import { Time } from '@angular/common';
export interface AplicacionErrorDto {
  tituloError: string;
  descripcionError: string;
  nombreAplicacion: string;
  horaError: string;
  ipUsuario: string;
  navegadorUsuario: string;
  userinfo: UserInfo;
}
export interface TrazabilidadCodigoDto {
  trazaError: string;
  origen: string;
}

export interface Issue {
  summary: string;
  description: string;
  projectname: string;
  parent: string;
  idError: string;
}

export interface UserInfo {
  fullname: string;
  email: string;
}
