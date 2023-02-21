package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(Exception ex, String applicationName);

	public Long persistAplicacionErrorFrontEnd(AplicacionError aplicacionError,TrazabilidadCodigo trazabilidadCodigo, List<AccionUsuario> accionesUsuario) throws BadRequestDataException;
	
	public void saveTrazabilitiyandUserevents(Long idAplicationError,TrazabilidadCodigo trazabilidadCodigo, List<AccionUsuario> accionesUsuario) throws BadRequestDataException;
	
	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario) throws BadRequestDataException;
}
