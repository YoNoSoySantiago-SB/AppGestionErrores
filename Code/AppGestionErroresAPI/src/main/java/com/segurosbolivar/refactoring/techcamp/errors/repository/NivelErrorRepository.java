package com.segurosbolivar.refactoring.techcamp.errors.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.segurosbolivar.refactoring.techcamp.errors.model.NivelError;

public interface NivelErrorRepository extends JpaRepository<NivelError, Long>{

}
