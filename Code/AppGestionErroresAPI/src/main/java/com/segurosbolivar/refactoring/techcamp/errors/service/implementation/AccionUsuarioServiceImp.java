package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AccionUsuarioRepository;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AccionUsuarioServiceI;
@Service
public class AccionUsuarioServiceImp implements AccionUsuarioServiceI {

	@Autowired
	private AccionUsuarioRepository accionUsuarioRepository;
	
	public AccionUsuarioServiceImp (AccionUsuarioRepository aur) {
		this.accionUsuarioRepository = aur;
	}
	@Override
	public List<AccionUsuarioDTO> findAllActionsUserByAplicacionErrorIdAplicacionError(Long id) throws NoResultException, BadRequestDataException {
		if(id==null) {
			throw new BadRequestDataException();
		}else {
			List<AccionUsuarioDTO> actions= new ArrayList<AccionUsuarioDTO>();;
			List<AccionUsuario> list=accionUsuarioRepository.findAllByAplicacionErrorIdAplicacionError(id);
			if (list.isEmpty()) {
				throw new NoResultException();
			}else {
				for(int s=0;s<list.size();s++) {
					AccionUsuarioDTO dto=new AccionUsuarioDTO();
					dto=dto.setInfoDTO(list.get(s));
					actions.add(dto);
				}
				
				return actions;
			}
		}
		
	}

}
