package com.segurosbolivar.refactoring.techcamp.errors.service.interfaces;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;

public interface AccionUsuarioServiceI {

	List<AccionUsuarioDTO> findAllActionsUserByAplicacionErrorIdAplicacionError(Long id) throws NoResultException, BadRequestDataException;

}
