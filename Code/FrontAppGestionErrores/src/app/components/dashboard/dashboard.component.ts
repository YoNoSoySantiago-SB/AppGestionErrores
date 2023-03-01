import {HttpService} from '../../services/httpService.service';
import { AplicacionErrorDTO } from './../../model/AplicacionErrorDTO.interface';
import { Component, OnInit } from '@angular/core';
import { TrazabilidadCodigoDTO } from 'src/app/model/TrazabilidadCodigoDTO.interface';
import { AccionUsuarioDTO } from '../../model/AccionUsuarioDTO.interface';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private HttpService:HttpService,private messageService: MessageService){}

  applicationError: AplicacionErrorDTO= {} as AplicacionErrorDTO;
  actionsUser: AccionUsuarioDTO[] = [];
  trazability: TrazabilidadCodigoDTO[] = [];
  trazaFrontend: string = '';
  trazaBackend: string = '';
  errorBackAndFront: boolean = false;
  showInformation: boolean = false;
  idApplicationError: string = '';
  first = 0;
  rows = 5;

  ngOnInit(): void {
    this.showInformation=false;
  }

  getApplicationError(idApplicationError:string) {
    console.log("id",idApplicationError)
    this.HttpService.getApplicationError(idApplicationError).subscribe({
      next: (response) => {
       this.applicationError=response
       this.getUserActions(idApplicationError)
       this.getTrazability(idApplicationError)
       this.showInformation=true;
      },
      error: (error) => {
        this.messageService.add({severity:'error', summary:'ERROR', detail:'El registro del error no existe'});
      }
    });
   }

  getUserActions(idApplicationError:string) {
    this.HttpService.getUserActions(idApplicationError).subscribe((response) => {
      this.actionsUser=response
    });
  }
  
  getTrazability(idApplicationError:string) {
    this.HttpService.getTrazability(idApplicationError).subscribe((response) => {
      this.trazability=response
      if (this.trazability.length === 2) {
        for(let i = 0; i < this.trazability.length; i++) {
          if (this.trazability[i].origen == 'backend') {
            this.trazaBackend = this.trazability[i].trazaError;
            this.trazaBackend=this.trazaBackend.replace(/\n/g, '<br>');
          } else if (this.trazability[i].origen == 'frontend') {
            this.trazaFrontend = this.trazability[i].trazaError;
            this.trazaFrontend=this.trazaFrontend.replace(/\n/g, '<br>');
          }
        }
        this.errorBackAndFront= true;
      }else{
        this.trazaFrontend = this.trazability[0].trazaError;
        this.trazaFrontend=this.trazaFrontend.replace(/\n/g, '<br>');
        this.errorBackAndFront= false;
      }
      console.log("showInformation",this.errorBackAndFront)
    });
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
    return this.actionsUser ? this.first === (this.actionsUser.length - this.rows): true;
  }

  isFirstPage(): boolean {
    return this.actionsUser ? this.first === 0 : true;
  }
  clear() {
    this.messageService.clear();
}
}
