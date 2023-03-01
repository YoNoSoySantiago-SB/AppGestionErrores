package com.segurosbolivar.refactoring.techcamp.errors.model;

import java.sql.Time;
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
public class AplicacionError {
	
	@Id
	@SequenceGenerator(name = "id_aplicacion_error_generator", allocationSize = 1, sequenceName = "seq_aplicacion_error")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_aplicacion_error_generator")
	private Long idAplicacionError;
	
	private String tituloError;
	private String descripcionError;
	private String nombreAplicacion;
	
	private Time horaError;
	private String navegadorUsuario;
	private String ipUsuario;
	
	@OneToMany(mappedBy = "aplicacionError")
	private List<TrazabilidadCodigo> trazas_codigo;
	
	@OneToMany(mappedBy = "aplicacionError")
	private List<AccionUsuario> accion_usuario;
}
