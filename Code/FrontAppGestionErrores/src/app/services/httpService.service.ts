import { TrazabilidadCodigoDTO } from '../model/TrazabilidadCodigoDTO.interface';
import { AplicacionErrorDTO } from '../model/AplicacionErrorDTO.interface';
import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccionUsuarioDTO } from '../model/AccionUsuarioDTO.interface';

@Injectable({
  providedIn: 'root'
})
export class HttpService {
  baseUrl =environment.url
  constructor(private httpClient:HttpClient) { }

  /**
  Gets an ApplicationErrorDTO object according to the given identifier.
  @param idApplicationError The identifier of the application error.
  @returns An Observable object containing the application error information.
  */
  getApplicationError(idApplicationError:string):Observable<AplicacionErrorDTO>{ 
    return this.httpClient.get<AplicacionErrorDTO>(this.baseUrl+"getApplicationError/"+idApplicationError);
  }

  /**
  Gets a list of ActionUserDTO objects associated with the given application error.
  @param idApplicationError Identifier of the application error.
  @returns An Observable object containing a list of user actions associated with the application error.
  */
  getUserActions(idApplicationError:string):Observable<AccionUsuarioDTO[]>{ 
    return this.httpClient.get<AccionUsuarioDTO[]>(this.baseUrl+"getUserActions/"+idApplicationError);
  }

  /**
  Gets a list of objects TrazabilityDTOCode associated with the given application error.
  @param idApplicationError Identifier of the application error.
  @returns An Observable object containing a list of code traces associated with the application error.
  */
  getTrazability(idApplicationError:string):Observable<TrazabilidadCodigoDTO[]>{ 
    return this.httpClient.get<TrazabilidadCodigoDTO[]>(this.baseUrl+"getTrazability/"+idApplicationError);
  }
}
