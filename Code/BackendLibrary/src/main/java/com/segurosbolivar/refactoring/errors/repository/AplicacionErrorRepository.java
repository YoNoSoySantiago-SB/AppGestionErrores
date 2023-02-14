package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.AplicacionError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplicacionErrorRepository extends JpaRepository<AplicacionError, Long>{

}
