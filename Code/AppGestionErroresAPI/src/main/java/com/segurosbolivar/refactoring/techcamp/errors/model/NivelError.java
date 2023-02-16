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
public class NivelError {
	@Id
	@SequenceGenerator(name = "id_nivel_error_generator", allocationSize = 1, sequenceName = "seq_nivel_error")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_nivel_error_generator")
	private Long idNivelError;
	
	private String nombreNivel;
	
	@OneToMany(mappedBy = "nivelError")
	private List<AccionUsuario> accionesUsuario;
}
