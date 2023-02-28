import { Component, OnInit } from '@angular/core';
import { AccionUsuarioDTO } from '../../model/AccionUsuarioDTO.interface';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  accionesUsuario: AccionUsuarioDTO[] = [
    {
      fechaHoraAccion: new Date('2022-02-28T10:15:00'),
      accionUsuario: 'Crear',
      nombreNivel: 'Nivel 1',
      nombreAccion: 'Navegacion'
    },
    {
      fechaHoraAccion: new Date('2022-02-27T14:30:00'),
      accionUsuario: 'Modificar',
      nombreNivel: 'Nivel 2',
      nombreAccion: 'Input'
    },
    {
      fechaHoraAccion: new Date('2022-02-26T09:45:00'),
      accionUsuario: 'Eliminar',
      nombreNivel: 'Nivel 3',
      nombreAccion: 'Boton'
    },
    {
      fechaHoraAccion: new Date('2022-02-25T13:20:00'),
      accionUsuario: 'Consultar',
      nombreNivel: 'Nivel 2',
      nombreAccion: 'Request'
    },
    {
      fechaHoraAccion: new Date('2022-02-24T11:10:00'),
      accionUsuario: 'Crear',
      nombreNivel: 'Nivel 1',
      nombreAccion: 'Excepcion'
    }
  ];
  first = 0;

  rows = 10;

  getUserActions() {
   //pedir los valores
  }

next() {
    this.first = this.first + this.rows;
}

prev() {
    this.first = this.first - this.rows;
}

reset() {
    this.first = 0;
}

isLastPage(): boolean {
    return this.accionesUsuario ? this.first === (this.accionesUsuario.length - this.rows): true;
}

isFirstPage(): boolean {
    return this.accionesUsuario ? this.first === 0 : true;
}
}
