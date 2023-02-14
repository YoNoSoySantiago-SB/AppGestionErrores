package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.TipoAccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoAccionRepository extends JpaRepository<TipoAccion, Long>{

}
