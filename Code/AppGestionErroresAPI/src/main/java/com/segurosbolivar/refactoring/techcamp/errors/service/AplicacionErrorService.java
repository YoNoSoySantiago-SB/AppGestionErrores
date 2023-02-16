package com.segurosbolivar.refactoring.techcamp.errors.service;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.OrigenError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;
import com.segurosbolivar.refactoring.techcamp.errors.repository.AplicacionErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.OrigenErrorRepository;
import com.segurosbolivar.refactoring.techcamp.errors.repository.TrazabilidadCodigoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AplicacionErrorService {
	
	@Autowired
    private Environment environment;
	
	@Autowired
	private AplicacionErrorRepository aplicacionErrorRespository;
	@Autowired
	private OrigenErrorRepository origenErrorRepository;
	@Autowired
	private TrazabilidadCodigoRepository trazabilidadCodigoRepository;
	
	@Transactional
	public Long persistAplicacionError(Exception ex) {
		AplicacionError aplicacionError = new AplicacionError();
		TrazabilidadCodigo trazabilidadCodigo = new TrazabilidadCodigo();
    	
    	String applicationName = environment.getProperty("spring.application.name");
		applicationName = applicationName==null || applicationName=="null" ? "Desconocido":applicationName;
		
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
}
