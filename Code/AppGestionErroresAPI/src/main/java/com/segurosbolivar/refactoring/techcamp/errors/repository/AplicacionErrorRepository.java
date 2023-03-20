package com.segurosbolivar.refactoring.techcamp.errors.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.segurosbolivar.refactoring.techcamp.errors.model.AplicacionError;

public interface AplicacionErrorRepository extends JpaRepository<AplicacionError, Long>{
	
}
