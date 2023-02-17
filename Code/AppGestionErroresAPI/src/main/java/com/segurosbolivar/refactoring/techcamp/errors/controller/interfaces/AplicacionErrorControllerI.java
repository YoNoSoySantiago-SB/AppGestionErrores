package com.segurosbolivar.refactoring.techcamp.errors.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;
import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

public interface AplicacionErrorControllerI {
	
	public ResponseEntity<Long> saveBackendError(Exception ex);
	
	public ResponseEntity<Long> saveFrontEndError(AplicacionError aplicacionError,TrazabilidadCodigo trazabilidadCodigo,List<AccionUsuario> accionesUsuario);
	
	public void saveTrazabilitiyandUserevents(Long idAplicationError,TrazabilidadCodigo trazabilidadCodigo,List<AccionUsuario> accionesUsuario);
	
	public void generateReport(Long idAplicationError);
	


}
