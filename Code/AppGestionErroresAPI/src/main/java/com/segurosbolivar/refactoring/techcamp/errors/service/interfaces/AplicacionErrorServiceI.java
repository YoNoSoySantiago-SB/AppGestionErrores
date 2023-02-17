package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(Exception ex);

	public Long persistAplicacionErrorFrontEnd(AplicacionError aplicacionError,List<TrazabilidadCodigo> trazabilidadCodigo, List<AccionUsuario> accionesUsuario);
}
