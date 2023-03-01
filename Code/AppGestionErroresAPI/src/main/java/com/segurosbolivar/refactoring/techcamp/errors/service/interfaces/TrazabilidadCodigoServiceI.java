package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;

public interface TrazabilidadCodigoServiceI {
	
	List<TrazabilidadCodigoDTO> findAllTrazabilitiesByAplicacionErrorIdAplicacionError(Long id) throws NoResultException, BadRequestDataException;


}
