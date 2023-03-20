package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import com.segurosbolivar.refactoring.techcamp.errors.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.AplicacionErrorControllerI;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequest;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;

import jakarta.mail.MessagingException;

@RestController
@CrossOrigin("*")
public class AplicacionErrorControllerImp implements AplicacionErrorControllerI{
	
	@Autowired
	AplicacionErrorServiceI aplicacionErrorService;

	@Override
	@PostMapping("/aplicacionBackendError/save")
	public ResponseEntity<Long> saveBackendError(@RequestBody ExceptionDto exceptionDto) {
		Long newAplicacionError;
		try {
			newAplicacionError = aplicacionErrorService.persistAplicacionErrorBackend(exceptionDto);
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@PostMapping("/aplicacionFrontEndError/save")
	public ResponseEntity<Long> saveFrontEndError(@RequestBody ErrorRequest errorRequest) throws BadRequestDataException, MessagingException {
        Long newAplicacionError;
		try {
			newAplicacionError = aplicacionErrorService.persistAplicacionErrorFrontEnd(errorRequest.getAplicacionErrorDto(),errorRequest.getTrazabilidadCodigoDto(),errorRequest.getAccionesUsuarioDto());
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}catch (MessagingException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@PostMapping("/saveTrazabilitiyandUserevents/{idAplicationError}")
	public ResponseEntity<Long> saveTrazabilitiyandUserevents(@PathVariable("idAplicationError") Long idAplicationError, @RequestBody ErrorRequest errorRequest) throws BadRequestDataException, MessagingException {
		Long newAplicacionError;
		try {
			newAplicacionError=aplicacionErrorService.saveTrazabilitiyandUserevents(idAplicationError,errorRequest.getAplicacionErrorDto(), errorRequest.getTrazabilidadCodigoDto(),errorRequest.getAccionesUsuarioDto());
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}catch (MessagingException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newAplicacionError, HttpStatus.CREATED);
	}
	
	@Override
	@GetMapping("/getApplicationError/{idAplicationError}")
	public ResponseEntity<AplicacionErrorResponseDTO> getApplicationError(@PathVariable("idAplicationError") Long idAplicationError ) throws BadRequestDataException,NoResultException {
		AplicacionErrorResponseDTO aplicacionError;
		try {
		aplicacionError=aplicacionErrorService.findById(idAplicationError);
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (NoResultException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(aplicacionError, HttpStatus.CREATED);
	}

}
