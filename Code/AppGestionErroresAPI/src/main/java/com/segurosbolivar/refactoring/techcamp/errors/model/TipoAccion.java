package com.segurosbolivar.refactoring.techcamp.errors.model;

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
public class TipoAccion {
	
	public final static String TIPO_ACCION_BOTON ="Boton";
	public final static String TIPO_ACCION_INPUT = "Input";
	public final static String TIPO_ACCION_REQUEST ="Request";
	public final static String TIPO_ACCION_NAVEGACION = "Navegacion";
	public final static String TIPO_ACCION_EXCEPCION = "Excepcion";
	
	public TipoAccion() {
		
	}
	public TipoAccion(String nombreAccion) {
		this.nombreAccion = nombreAccion;
	}
	
	@Id
	@SequenceGenerator(name = "id_tipo_accion_generator", allocationSize = 1, sequenceName = "seq_tipo_accion")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_tipo_accion_generator")
	private Long idTipoAccion;
	private String nombreAccion;
	
	@OneToMany(mappedBy = "tipoAccion")
	private List<AccionUsuario> accionesUsuario;
	
}
