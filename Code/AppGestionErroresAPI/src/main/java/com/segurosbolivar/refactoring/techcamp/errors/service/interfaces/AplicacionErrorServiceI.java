package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.ExceptionDto;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(ExceptionDto exceptionDto) throws BadRequestDataException;

	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario) throws BadRequestDataException;

	public Long persistAplicacionErrorFrontEnd(AplicacionErrorDTO aplicacionError, TrazabilidadCodigoDTO trazabilidadCodigo,
			List<AccionUsuarioDTO> accionesUsuario) throws BadRequestDataException;

	public Long saveTrazabilitiyandUserevents(Long idAplicationError, TrazabilidadCodigoDTO trazabilidadCodigoDto,
			List<AccionUsuarioDTO> accionesUsuariodto) throws BadRequestDataException;

	public AplicacionErrorDTO findById(Long id) throws BadRequestDataException, NoResultException;
}
