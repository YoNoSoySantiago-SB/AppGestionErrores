package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.OrigenError;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrigenErrorRepository extends JpaRepository<OrigenError, Long>{
	public OrigenError findByNombreOrigen(String nombreOrigen);
}
