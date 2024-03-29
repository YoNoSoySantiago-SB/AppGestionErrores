package com.segurosbolivar.refactoring.techcamp.errors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segurosbolivar.refactoring.techcamp.errors.model.TipoAccion;

public interface TipoAccionRepository extends JpaRepository<TipoAccion, Long>{
	
	public TipoAccion findByNombreAccion(String nombreTipoAccion);

}
