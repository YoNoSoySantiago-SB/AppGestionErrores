package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.dtos.*;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequestBack;
import org.springframework.http.ResponseEntity;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequest;

public interface AplicacionErrorControllerI {
	
	ResponseEntity<Long> saveBackendError(ExceptionDto exceptionDto);

	ResponseEntity<Long> saveTrazabilitiyandUserevents(Long idAplicationError, ErrorRequestBack errorRequestBack) throws BadRequestDataException;

	ResponseEntity<Long> saveFrontEndError(ErrorRequest errorRequest) throws BadRequestDataException;

	ResponseEntity<AplicacionErrorResponseDTO> getApplicationError(Long idAplicationError)
			throws BadRequestDataException, NoResultException;
	
}
