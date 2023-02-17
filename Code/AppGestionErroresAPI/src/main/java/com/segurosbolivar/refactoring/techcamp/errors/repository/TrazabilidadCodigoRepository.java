package com.segurosbolivar.refactoring.techcamp.errors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.segurosbolivar.refactoring.techcamp.errors.model.TrazabilidadCodigo;

public interface TrazabilidadCodigoRepository extends JpaRepository<TrazabilidadCodigo, Long>{
	
	@Query("SELECT tc FROM TrazabilidadCodigo tc WHERE tc.aplicacionError.idAplicacionError=:idAplicationError")
	public List<TrazabilidadCodigo> findAllByAplicacionErrorIdAplicacionError(@Param("idAplicationError") Long idAplicationError);

}
