package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.AplicacionErrorControllerI;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AccionUsuarioServiceI;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;

@RestController
@CrossOrigin("*")
public class AplicacionErrorControllerImp implements AplicacionErrorControllerI{
	
	@Autowired
	AplicacionErrorServiceI aplicacionErrorService;

	@Override
	@RequestMapping(value = "/aplicacionBackendError/save", method = RequestMethod.POST)
	public ResponseEntity<Long> saveBackendError(@RequestBody Exception ex) {
		Long newAplicacionError=aplicacionErrorService.persistAplicacionErrorBackend(ex);
		return new ResponseEntity(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@RequestMapping(value = "/aplicacionFrontEndError/save", method = RequestMethod.POST)
	public ResponseEntity<Long> saveFrontEndError(@RequestBody AplicacionError aplicacionError,@RequestBody TrazabilidadCodigo trazabilidadCodigo,@RequestBody List<AccionUsuario> accionesUsuario) {
		Long newAplicacionError=aplicacionErrorService.persistAplicacionErrorFrontEnd(aplicacionError,trazabilidadCodigo,accionesUsuario);
		return new ResponseEntity(newAplicacionError, HttpStatus.CREATED);
	}

	@Override
	@RequestMapping(value = "/saveTrazabilitiyandUserevents/{idAplicationError}", method = RequestMethod.POST)
	public void saveTrazabilitiyandUserevents(@PathVariable("id") Long idAplicationError ,@RequestBody TrazabilidadCodigo trazabilidadCodigo,@RequestBody List<AccionUsuario> accionesUsuario) {
		aplicacionErrorService.saveTrazabilitiyandUserevents(idAplicationError,trazabilidadCodigo,accionesUsuario);
	}

	@Override
	@RequestMapping(value = "/generateReport/{idAplicationError}", method = RequestMethod.POST)
	public void generateReport(@PathVariable("id") Long idAplicationError) {
		// TODO Auto-generated method stub
		
	}

}
