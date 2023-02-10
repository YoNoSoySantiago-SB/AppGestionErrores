package com.segurosbolivar.refactoring.errors.model;

import lombok.Data;

@Data
public class TrazabilidadCodigo {
	private Long idTrazabilidad;
	private Long idAplicacionError;
	private Long origenError;
	private String trazaError;
}
