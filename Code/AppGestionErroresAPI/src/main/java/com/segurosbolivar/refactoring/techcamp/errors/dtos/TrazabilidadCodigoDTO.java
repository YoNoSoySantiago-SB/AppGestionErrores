package com.segurosbolivar.refactoring.techcamp.errors.dtos;


import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrazabilidadCodigoDTO {
	
	private String trazaError;
	
	public TrazabilidadCodigo setInfo(TrazabilidadCodigo trazabilidadCodigo) {
		trazabilidadCodigo.setTrazaError(this.trazaError);
		return trazabilidadCodigo;
		
	}

}
