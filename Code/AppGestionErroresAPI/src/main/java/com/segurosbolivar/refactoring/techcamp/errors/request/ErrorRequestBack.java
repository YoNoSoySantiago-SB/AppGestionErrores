package com.segurosbolivar.refactoring.techcamp.errors.request;

import com.segurosbolivar.refactoring.techcamp.errors.dtos.AccionUsuarioDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.AplicacionErrorDTO;
import com.segurosbolivar.refactoring.techcamp.errors.dtos.TrazabilidadCodigoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ErrorRequestBack {

    private TrazabilidadCodigoDTO trazabilidadCodigoDto;
    private List<AccionUsuarioDTO> accionesUsuarioDto;

}

