package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Date;

import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AplicacionErrorResponseDTO {
    private Long idAplicacionError;
    private String tituloError;
    private String descripcionError;
    private String nombreAplicacion;
    private String horaError;
    private String navegadorUsuario;
    private String ipUsuario;

    public AplicacionError setInfo(AplicacionError aplicacionError) {
        aplicacionError.setIdAplicacionError(this.idAplicacionError);
        aplicacionError.setTituloError(this.tituloError);
        aplicacionError.setTituloError(this.tituloError);
        aplicacionError.setDescripcionError(this.descripcionError);
        aplicacionError.setNombreAplicacion(this.nombreAplicacion);
        aplicacionError.setHoraError(ZonedDateTime.parse(horaError));
        aplicacionError.setNavegadorUsuario(this.navegadorUsuario);
        aplicacionError.setIpUsuario(this.ipUsuario);
        return aplicacionError;

    }

    public AplicacionErrorResponseDTO setInfoDTO(AplicacionError aplicacionError) {
        this.idAplicacionError=aplicacionError.getIdAplicacionError();
        this.tituloError=aplicacionError.getTituloError();
        this.descripcionError=aplicacionError.getDescripcionError();
        this.nombreAplicacion=aplicacionError.getNombreAplicacion();
        this.horaError=aplicacionError.getHoraError().toString();
        this.navegadorUsuario=aplicacionError.getNavegadorUsuario();
        this.ipUsuario=aplicacionError.getIpUsuario();
        return this;

    }

}