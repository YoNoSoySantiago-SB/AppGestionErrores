package com.segurosbolivar.refactoring.techcamp.errors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segurosbolivar.refactoring.techcamp.errors.model.OrigenError;

public interface OrigenErrorRepository extends JpaRepository<OrigenError, Long>{
	public OrigenError findByNombreOrigen(String nombreOrigen);
}
