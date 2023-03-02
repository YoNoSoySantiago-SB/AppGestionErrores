package com.segurosbolivar.refactoring.techcamp.errors.model;

import java.time.ZonedDateTime;
import java.util.Date;

import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class AccionUsuario {
	
	public AccionUsuario(TipoAccion tipoAccion, NivelError nivelError) {
		this.nivelError = nivelError;
		this.tipoAccion = tipoAccion;
	}
	
	@Id
	@SequenceGenerator(name = "id_accion_ususario_generator", allocationSize = 1, sequenceName = "seq_accion_usuario")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_accion_ususario_generator")
	private Long idAccionUsuario;
	private ZonedDateTime fechaHoraAccion;
	private String accionUsuario;
	
	@ManyToOne
	@JoinColumn(name="id_aplicacion_error")
	private AplicacionError aplicacionError;
	
	@ManyToOne
	@JoinColumn(name="id_nivel_error")
	private NivelError nivelError;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_accion")
	private TipoAccion tipoAccion;	
	
}
