package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.TrazabilidadCodigo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrazabilidadCodigoRepository extends JpaRepository<TrazabilidadCodigo, Long>{

}
