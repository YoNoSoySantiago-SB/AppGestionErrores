package com.segurosbolivar.refactoring.techcamp.errors.request;

import java.util.List;

import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;

import lombok.Data;

@Data
public class ErrorRequest {
    private AplicacionErrorDTO aplicacionErrorDto;
    private TrazabilidadCodigoDTO trazabilidadCodigoDto;
    private List<AccionUsuarioDTO> accionesUsuarioDto;

}

