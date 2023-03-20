package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import java.time.ZonedDateTime;

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
	private String horaError;
	private String navegadorUsuario;
	private String ipUsuario;
	private UserInfo userinfo;
	
	public AplicacionError setInfo(AplicacionError aplicacionError) {
		aplicacionError.setTituloError(this.tituloError);
		aplicacionError.setDescripcionError(this.descripcionError);
		aplicacionError.setNombreAplicacion(this.nombreAplicacion);
		aplicacionError.setHoraError(ZonedDateTime.parse(horaError));
		aplicacionError.setNavegadorUsuario(this.navegadorUsuario);
		aplicacionError.setIpUsuario(this.ipUsuario);
		aplicacionError.setCorreoUsuario(userinfo.email);
		aplicacionError.setNombreUsuario(userinfo.fullname);
		return aplicacionError;
		
	}

	public AplicacionErrorDTO setInfoDTO(AplicacionError aplicacionError) {
		this.tituloError=aplicacionError.getTituloError();
		this.descripcionError=aplicacionError.getDescripcionError();
		this.nombreAplicacion=aplicacionError.getNombreAplicacion();
		this.horaError=aplicacionError.getHoraError().toString();
		this.navegadorUsuario=aplicacionError.getNavegadorUsuario();
		this.ipUsuario=aplicacionError.getIpUsuario();
		this.userinfo.email=aplicacionError.getCorreoUsuario();
		this.userinfo.fullname = aplicacionError.getNombreUsuario();
		return this;
		
	}
	
	@Data
	public class UserInfo{
		String fullname;
		String email;
	}
}
