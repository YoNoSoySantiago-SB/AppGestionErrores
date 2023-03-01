package com.segurosbolivar.refactoring.techcamp.errors.controller.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces.AccionUsuarioControllerI;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AccionUsuarioServiceI;

@RestController
@CrossOrigin("*")
public class AccionUsuarioControllerImp  implements AccionUsuarioControllerI{

	
	@Autowired
	AccionUsuarioServiceI accionUsuarioService;
	
	@Override
	@GetMapping("/getUserActions/{idAplicationError}")
	public ResponseEntity<List<AccionUsuarioDTO>> getUserActions(@PathVariable("idAplicationError") Long idAplicationError ) throws BadRequestDataException,NoResultException {
		List<AccionUsuarioDTO> list;
		try {
			list=accionUsuarioService.findAllActionsUserByAplicacionErrorIdAplicacionError(idAplicationError);
		} catch (BadRequestDataException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (NoResultException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.CREATED);
	}
}
