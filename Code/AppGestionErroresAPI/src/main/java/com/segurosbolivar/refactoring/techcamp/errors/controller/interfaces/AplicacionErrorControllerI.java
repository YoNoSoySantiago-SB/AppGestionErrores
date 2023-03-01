package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.ExceptionDto;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequest;

public interface AplicacionErrorControllerI {
	
	ResponseEntity<Long> saveBackendError(ExceptionDto exceptionDto);

	ResponseEntity<Long> saveTrazabilitiyandUserevents(Long idAplicationError, TrazabilidadCodigoDTO trazabilidadCodigoDto,
			List<AccionUsuarioDTO> accionesUsuarioDto) throws BadRequestDataException;

	ResponseEntity<Long> saveFrontEndError(ErrorRequest errorRequest) throws BadRequestDataException;

	ResponseEntity<AplicacionErrorDTO> getApplicationError(Long idAplicationError)
			throws BadRequestDataException, NoResultException;
	
}
