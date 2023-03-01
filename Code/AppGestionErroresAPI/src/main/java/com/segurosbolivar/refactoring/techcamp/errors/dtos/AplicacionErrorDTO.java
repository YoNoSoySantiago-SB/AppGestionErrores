package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import java.sql.Date;
import java.sql.Time;

import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AplicacionErrorDTO {

	private String tituloError;
	private String descripcionError;
	private String nombreAplicacion;
	private Time horaError;
	private String navegadorUsuario;
	private String ipUsuario;
	
	public AplicacionError setInfo(AplicacionError aplicacionError) {
		aplicacionError.setTituloError(this.tituloError);
		aplicacionError.setDescripcionError(this.descripcionError);
		aplicacionError.setNombreAplicacion(this.nombreAplicacion);
		aplicacionError.setHoraError(this.horaError);
		aplicacionError.setNavegadorUsuario(this.navegadorUsuario);
		aplicacionError.setIpUsuario(this.ipUsuario);
		return aplicacionError;
		
	}

	public AplicacionErrorDTO setInfoDTO(AplicacionError aplicacionError) {
		this.tituloError=aplicacionError.getTituloError();
		this.descripcionError=aplicacionError.getDescripcionError();
		this.nombreAplicacion=aplicacionError.getNombreAplicacion();
		this.horaError=aplicacionError.getHoraError();
		this.navegadorUsuario=aplicacionError.getNavegadorUsuario();
		this.ipUsuario=aplicacionError.getIpUsuario();
		return this;
		
	}
	
}
