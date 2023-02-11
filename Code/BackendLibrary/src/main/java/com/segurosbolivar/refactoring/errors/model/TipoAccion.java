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
public class TipoAccion {
	@Id
	@SequenceGenerator(name = "id_tipo_accion_generator", allocationSize = 1, sequenceName = "seq_tipo_accion")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_tipo_accion_generator")
	private Long idTipoAccion;
	private String nombreAccion;
	
	@OneToMany(mappedBy = "tipoAccion")
	private List<AccionUsuario> accionesUsuario;
	
}
