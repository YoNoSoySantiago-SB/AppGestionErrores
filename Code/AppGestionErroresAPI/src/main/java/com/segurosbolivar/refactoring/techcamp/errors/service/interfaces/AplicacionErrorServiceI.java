package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(Exception ex);

	public Long persistAplicacionErrorFrontEnd(AplicacionError aplicacionError,TrazabilidadCodigo trazabilidadCodigo, List<AccionUsuario> accionesUsuario);
	
	public Long saveTrazabilitiyandUserevents(Long idAplicationError,TrazabilidadCodigo trazabilidadCodigo, List<AccionUsuario> accionesUsuario);
	
	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario);
}
