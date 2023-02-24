package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;


import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(Exception ex, String applicationName) throws BadRequestDataException;

	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario) throws BadRequestDataException;

	Long persistAplicacionErrorFrontEnd(AplicacionErrorDTO aplicacionError, TrazabilidadCodigoDTO trazabilidadCodigo,
			List<AccionUsuarioDTO> accionesUsuario) throws BadRequestDataException;

	void saveTrazabilitiyandUserevents(Long idAplicationError, TrazabilidadCodigoDTO trazabilidadCodigoDto,
			List<AccionUsuarioDTO> accionesUsuariodto) throws BadRequestDataException;
}
