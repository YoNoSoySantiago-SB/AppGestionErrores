package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import java.sql.Date;

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
	private String correoUsuario;
	private Date horaError;
	private String navegadorUsuario;
	private String ipUsuario;
	
	public AplicacionError setInfo(AplicacionError aplicacionError) {
		aplicacionError.setTituloError(this.tituloError);
		aplicacionError.setDescripcionError(this.descripcionError);
		aplicacionError.setNombreAplicacion(this.nombreAplicacion);
		aplicacionError.setCorreoUsuario(this.correoUsuario);
		aplicacionError.setHoraError(this.horaError);
		aplicacionError.setNavegadorUsuario(this.navegadorUsuario);
		aplicacionError.setIpUsuario(this.ipUsuario);
		return aplicacionError;
		
	}

}
