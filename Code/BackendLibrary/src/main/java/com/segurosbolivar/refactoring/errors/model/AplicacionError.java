package com.segurosbolivar.refactoring.errors.model;

import lombok.Data;

@Data
public class AplicacionError {
	
	private Long idAplicacionError;
	private String tituloError;
	private String descriptionError;
	private String nombreAplicacion;
	private String correoUsuario;
}
