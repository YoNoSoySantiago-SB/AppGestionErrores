package com.segurosbolivar.refactoring.errors.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class OrigenError {
	public final static String ORIGEN_ERROR_BACKEND ="backend";
	public final static String ORIGEN_ERROR_FRONTEND = "frontend";
	
	@Id
	@SequenceGenerator(name = "id_origen_error_generator", allocationSize = 1, sequenceName = "seq_origen_error")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_origen_error_generator")
	private Long idOrigenError;
	private String nombreOrigen;
	
	@OneToMany(mappedBy = "origenError")
	private List<TrazabilidadCodigo> trazasCodigo;
	
}
