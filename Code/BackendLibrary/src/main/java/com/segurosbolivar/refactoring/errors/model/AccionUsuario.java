package com.segurosbolivar.refactoring.errors.model;

import java.util.Date;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AccionUsuario {
	@Id
	@SequenceGenerator(name = "id_accion_ususario_generator", allocationSize = 1, sequenceName = "seq_accion_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_accion_ususario_generator")
	private Long idAccionUsuario;
	
	private AplicacionError aplicacionError;
	private NivelError nivelError;
	private TipoAccion tipoAccion;	
	private Date fechaHoraAccion;
	private String accionUsuario;
}
