package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.AplicacionErrorControllerI;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.request.ErrorRequest;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;

@RestController
@CrossOrigin("*")
public class AplicacionErrorControllerImp implements AplicacionErrorControllerI{
	
	@Autowired
	AplicacionErrorServiceI aplicacionErrorService;

	@Override
	@PostMapping("/aplicacionBackendError/save")
	public ResponseEntity<Long> saveBackendError(@RequestBody Exception ex, @RequestBody String applicationName) {
		Long newAplicacionError;
		try {
			newAplicacionError = aplicacionErrorService.persistAplicacionErrorBackend(ex, applicationName);
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@RequestMapping(value = "/aplicacionFrontEndError/save", method = RequestMethod.POST)
	public ResponseEntity<Long> saveFrontEndError(@RequestBody ErrorRequest errorRequest) throws BadRequestDataException {
		Long newAplicacionError=aplicacionErrorService.persistAplicacionErrorFrontEnd(errorRequest.getAplicacionErrorDto(),errorRequest.getTrazabilidadCodigoDto(),errorRequest.getAccionesUsuarioDto());
		return new ResponseEntity(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@RequestMapping(value = "/saveTrazabilitiyandUserevents/{idAplicationError}", method = RequestMethod.POST)
	public void saveTrazabilitiyandUserevents(@PathVariable("id") Long idAplicationError ,@RequestBody TrazabilidadCodigoDTO trazabilidadCodigoDto,@RequestBody List<AccionUsuarioDTO> accionesUsuarioDto) throws BadRequestDataException {
		aplicacionErrorService.saveTrazabilitiyandUserevents(idAplicationError,trazabilidadCodigoDto,accionesUsuarioDto);
	}

}
