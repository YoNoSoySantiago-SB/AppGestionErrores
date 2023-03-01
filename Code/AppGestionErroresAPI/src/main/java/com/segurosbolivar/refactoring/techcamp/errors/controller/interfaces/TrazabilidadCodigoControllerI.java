package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;

public interface TrazabilidadCodigoControllerI {

	ResponseEntity<List<TrazabilidadCodigoDTO>> getTrazability(Long idAplicationError)
			throws BadRequestDataException, NoResultException;

}
