package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.NoResultException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TrazabilidadCodigoRepository;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.TrazabilidadCodigoServiceI;
@Service
public class TrazabilidadCodigoServiceImp implements TrazabilidadCodigoServiceI {

	@Autowired
	private TrazabilidadCodigoRepository trazabilidadCodigoRepository;
	
	public TrazabilidadCodigoServiceImp(TrazabilidadCodigoRepository tcr) {	
		this.trazabilidadCodigoRepository = tcr;
	}
	
	@Override
	public List<TrazabilidadCodigoDTO> findAllTrazabilitiesByAplicacionErrorIdAplicacionError(Long id) throws NoResultException, BadRequestDataException {
		if(id==null) {
			throw new BadRequestDataException();
		}else {
			List<TrazabilidadCodigoDTO> trazabilities= new ArrayList<TrazabilidadCodigoDTO>();;
			List<TrazabilidadCodigo> list=trazabilidadCodigoRepository.findAllByAplicacionErrorIdAplicacionError(id);
			if (list.isEmpty()) {
				throw new NoResultException();
			}else {
				for(int s=0;s<list.size();s++) {
					TrazabilidadCodigoDTO dto=new TrazabilidadCodigoDTO();
					dto=dto.setInfoDTO(list.get(s));
					trazabilities.add(dto);
				}
				
				return trazabilities;
			}
		}
	}
	

}
