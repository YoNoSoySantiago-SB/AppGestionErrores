package com.segurosbolivar.refactoring.techcamp.errors.service.implementation;

import java.io.PrintWriter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.segurosbolivar.refactoring.techcamp.errors.customexceptions.BadRequestDataException;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.ExceptionDto;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.NivelError;
import com.segurosbolivar.refactoring.techcamp.errors.model.OrigenError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TipoAccion;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AccionUsuarioRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AplicacionErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.NivelErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.OrigenErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TipoAccionRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TrazabilidadCodigoRepository;
import com.segurosbolivar.refactoring.techcamp.errors.service.interfaces.AplicacionErrorServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AplicacionErrorServiceImp implements AplicacionErrorServiceI{
	
	@Autowired
	private AplicacionErrorRepository aplicacionErrorRespository;
	@Autowired
	private OrigenErrorRepository origenErrorRepository;
	@Autowired
	private TrazabilidadCodigoRepository trazabilidadCodigoRepository;
	@Autowired
	private NivelErrorRepository nivelErrorRepository;
	@Autowired
	private TipoAccionRepository tipoAccionRepository;
	@Autowired
	private AccionUsuarioRepository accionUsuarioRepository;
	
	public AplicacionErrorServiceImp(
			AplicacionErrorRepository aer,
			OrigenErrorRepository oer,
			TrazabilidadCodigoRepository tcr,
			NivelErrorRepository ner,
			TipoAccionRepository tar,
			AccionUsuarioRepository aur) {
		
		this.aplicacionErrorRespository = aer;
		this.origenErrorRepository = oer;
		this.trazabilidadCodigoRepository = tcr;
		this.nivelErrorRepository = ner;
		this.tipoAccionRepository = tar;
		this.accionUsuarioRepository = aur;
	}
	
	@Override
	public Long persistAplicacionErrorBackend(ExceptionDto exceptionDto) throws BadRequestDataException {
		
		String applicationName = exceptionDto.getApplicationName();
		Exception ex = exceptionDto.getException();
		
		if(applicationName==null || applicationName.isEmpty() || ex == null) {
			throw new BadRequestDataException();
		}
		
		AplicacionError aplicacionError = new AplicacionError();
		TrazabilidadCodigo trazabilidadCodigo = new TrazabilidadCodigo();
		
		aplicacionError.setNombreAplicacion(applicationName);
		aplicacionError = aplicacionErrorRespository.save(aplicacionError);
		
		StringWriter stackTraceWriter = new StringWriter();
    	ex.printStackTrace(new PrintWriter(stackTraceWriter));
    	String stackTrace = stackTraceWriter.toString();
    	OrigenError origenError = origenErrorRepository.findByNombreOrigen(OrigenError.ORIGEN_ERROR_BACKEND);
		
		trazabilidadCodigo.setTrazaError(stackTrace);
		trazabilidadCodigo.setAplicacionError(aplicacionError);
		trazabilidadCodigo.setOrigenError(origenError);
		
		trazabilidadCodigoRepository.save(trazabilidadCodigo);
		
		return aplicacionError.getIdAplicacionError();
	}

	@Override
	public Long persistAplicacionErrorFrontEnd(AplicacionErrorDTO aplicacionErrorDto,TrazabilidadCodigoDTO trazabilidadCodigoDto, List<AccionUsuarioDTO> accionesUsuarioDto) throws BadRequestDataException {
		if(aplicacionErrorDto==null || trazabilidadCodigoDto== null || accionesUsuarioDto==null) {
			throw new BadRequestDataException();
		}else {
			AplicacionError aplicacionError=new AplicacionError();
			aplicacionError=aplicacionErrorDto.setInfo(aplicacionError);
			System.out.println(aplicacionError.getNavegadorUsuario());
			aplicacionError = aplicacionErrorRespository.save(aplicacionError);
			OrigenError origenError = origenErrorRepository.findByNombreOrigen(OrigenError.ORIGEN_ERROR_FRONTEND);
			TrazabilidadCodigo trazabilidadCodigo=new TrazabilidadCodigo();
			trazabilidadCodigo=trazabilidadCodigoDto.setInfo(trazabilidadCodigo);
			trazabilidadCodigo.setAplicacionError(aplicacionError);
			trazabilidadCodigo.setOrigenError(origenError);
			
			trazabilidadCodigoRepository.save(trazabilidadCodigo);
			List<AccionUsuario> acciones=new ArrayList<AccionUsuario>();
			for(AccionUsuarioDTO accionDto : accionesUsuarioDto) {
				AccionUsuario accion= new AccionUsuario();
				NivelError nivelError=new NivelError();
				TipoAccion tipoAccion= new TipoAccion();
				accion.setNivelError(nivelError);
				accion.setTipoAccion(tipoAccion);
				accion=accionDto.setInfo(accion);
				accion.setAplicacionError(aplicacionError);
				acciones.add(accion);				
			}
			acciones=categorizeUserEvents(acciones);
			
			accionUsuarioRepository.saveAll(acciones);
			return aplicacionError.getIdAplicacionError();
		}
		
	}

	@Override
	public void saveTrazabilitiyandUserevents(Long idAplicationError, TrazabilidadCodigoDTO trazabilidadCodigoDto,
			List<AccionUsuarioDTO> accionesUsuarioDto) throws BadRequestDataException {
		if(idAplicationError==null || trazabilidadCodigoDto== null || accionesUsuarioDto==null) {
			throw new BadRequestDataException();
		}else {
			Optional<AplicacionError> aplicacionError = aplicacionErrorRespository.findById(idAplicationError);
			if(aplicacionError.isEmpty()) {
				throw new BadRequestDataException();
			}else {
				TrazabilidadCodigo trazabilidadCodigo=new TrazabilidadCodigo();
				trazabilidadCodigo=trazabilidadCodigoDto.setInfo(trazabilidadCodigo);
				trazabilidadCodigo.setAplicacionError(aplicacionError.get());
				OrigenError origenError = origenErrorRepository.findByNombreOrigen(OrigenError.ORIGEN_ERROR_FRONTEND);
				trazabilidadCodigo.setOrigenError(origenError);
				
				trazabilidadCodigoRepository.save(trazabilidadCodigo);
				List<AccionUsuario> acciones=new ArrayList<AccionUsuario>();
				for(AccionUsuarioDTO accionDto : accionesUsuarioDto) {
					AccionUsuario accion= new AccionUsuario();
					NivelError nivelError=new NivelError();
					TipoAccion tipoAccion= new TipoAccion();
					accion.setNivelError(nivelError);
					accion.setTipoAccion(tipoAccion);
					accion=accionDto.setInfo(accion);
					acciones.add(accion);				
				}
				acciones=categorizeUserEvents(acciones);
				
				accionUsuarioRepository.saveAll(acciones);
			}
		}
	}

	@Override
	public List<AccionUsuario> categorizeUserEvents(List<AccionUsuario> accionesUsuario) throws BadRequestDataException {
		if (accionesUsuario == null) {
	        throw new BadRequestDataException();
	    }else {
	    	for(AccionUsuario acciones : accionesUsuario) {
	        	if(acciones.getNivelError().getNombreNivel().equals(NivelError.NIVEL_ERROR_INFO) || acciones.getNivelError().getNombreNivel().equals(NivelError.NIVEL_ERROR_EXCEPTION)) {
					if(acciones.getNivelError().getNombreNivel().equals(NivelError.NIVEL_ERROR_INFO)) {
						NivelError nivelError = nivelErrorRepository.findByNombreNivel(NivelError.NIVEL_ERROR_INFO);
						acciones.setNivelError(nivelError);
					}else{
						NivelError nivelError = nivelErrorRepository.findByNombreNivel(NivelError.NIVEL_ERROR_EXCEPTION);
						acciones.setNivelError(nivelError);
					}
		    	}else {
		    		 throw new BadRequestDataException();
		    	}
				if(acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_BOTON) || acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_INPUT) ||
				acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_REQUEST) || acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_NAVEGACION) ||
				acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_EXCEPCION)) {
					if(acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_BOTON)) {
						TipoAccion tipoAccion = tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_BOTON);
						acciones.setTipoAccion(tipoAccion);
					}
					else if(acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_INPUT)) {
						TipoAccion tipoAccion = tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_INPUT);
						acciones.setTipoAccion(tipoAccion);
					}else if(acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_REQUEST)) {
						TipoAccion tipoAccion = tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_REQUEST);
						acciones.setTipoAccion(tipoAccion);
					}
					else if(acciones.getTipoAccion().getNombreAccion().equals(TipoAccion.TIPO_ACCION_NAVEGACION)) {
						TipoAccion tipoAccion = tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_NAVEGACION);
						acciones.setTipoAccion(tipoAccion);
					}
					else {
						TipoAccion tipoAccion = tipoAccionRepository.findByNombreAccion(TipoAccion.TIPO_ACCION_EXCEPCION);
						acciones.setTipoAccion(tipoAccion);
					}
				}else {
					 throw new BadRequestDataException();
				}
			}
	    }
		return accionesUsuario;
	}
}
