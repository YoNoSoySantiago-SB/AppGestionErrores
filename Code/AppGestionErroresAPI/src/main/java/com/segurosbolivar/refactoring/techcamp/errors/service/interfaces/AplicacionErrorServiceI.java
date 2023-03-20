package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.*;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;

import jakarta.mail.MessagingException;

public interface AplicacionErrorServiceI {
	
	public Long persistAplicacionErrorBackend(ExceptionDto exceptionDto) throws BadRequestDataException;

	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario) throws BadRequestDataException;

	public Long persistAplicacionErrorFrontEnd(AplicacionErrorDTO aplicacionError, TrazabilidadCodigoDTO trazabilidadCodigo,
			List<AccionUsuarioDTO> accionesUsuario) throws BadRequestDataException, MessagingException;

	public Long saveTrazabilitiyandUserevents(Long idAplicationError, AplicacionErrorDTO aplicacionError, TrazabilidadCodigoDTO trazabilidadCodigoDto,
			List<AccionUsuarioDTO> accionesUsuariodto) throws BadRequestDataException, MessagingException;

	public AplicacionErrorResponseDTO findById(Long id) throws BadRequestDataException, NoResultException;
	
}
