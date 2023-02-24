package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import java.sql.Date;

import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor


public class AccionUsuarioDTO {
	
	private Date fechaHoraAccion;
	private String accionUsuario;
	private String nombreNivel;
	private String nombreAccion;
	
	public AccionUsuarioDTO(String nombreNivel,String nombreAccion) {
		this.nombreAccion=nombreAccion;
		this.nombreNivel=nombreNivel;
	}
	
	public AccionUsuario setInfo(AccionUsuario accionUsuario) {
		accionUsuario.setFechaHoraAccion(this.fechaHoraAccion);
		accionUsuario.setAccionUsuario(this.accionUsuario);
		accionUsuario.getNivelError().setNombreNivel(this.nombreNivel);;
		accionUsuario.getTipoAccion().setNombreAccion(this.nombreAccion);;
		return accionUsuario;
		
	}
	

}
