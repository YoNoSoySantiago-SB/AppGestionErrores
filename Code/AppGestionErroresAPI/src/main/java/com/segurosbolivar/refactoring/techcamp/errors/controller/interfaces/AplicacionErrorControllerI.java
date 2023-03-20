package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import com.segurosbolivar.refactoring.techcamp.errors.dtos.*;
import org.springframework.http.ResponseEntity;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequest;

import jakarta.mail.MessagingException;

public interface AplicacionErrorControllerI {
	
	ResponseEntity<Long> saveBackendError(ExceptionDto exceptionDto);

	ResponseEntity<Long> saveTrazabilitiyandUserevents(Long idAplicationError, ErrorRequest errorRequestBack) throws BadRequestDataException, MessagingException;

	ResponseEntity<Long> saveFrontEndError(ErrorRequest errorRequest) throws BadRequestDataException, MessagingException;

	ResponseEntity<AplicacionErrorResponseDTO> getApplicationError(Long idAplicationError)
			throws BadRequestDataException, NoResultException;
	
}
