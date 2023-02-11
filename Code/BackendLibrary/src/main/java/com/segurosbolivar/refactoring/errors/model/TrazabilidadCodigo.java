package com.segurosbolivar.refactoring.errors.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class TrazabilidadCodigo {
	@Id
	@SequenceGenerator(name = "id_trazabilidad_codigo_generator", allocationSize = 1, sequenceName = "seq_trazabilidad_codigo")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_trazabilidad_codigo_generator")
	private Long idTrazabilidad;
	private String trazaError;
	
	@ManyToOne
	@JoinColumn(name="id_applicacion_error")
	private AplicacionError aplicacionError;
	
	@ManyToOne
	@JoinColumn(name="id_origen_error")
	private OrigenError origenError;
	
}
