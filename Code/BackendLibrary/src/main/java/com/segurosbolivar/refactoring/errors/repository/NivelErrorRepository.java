package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.NivelError;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NivelErrorRepository extends JpaRepository<NivelError, Long>{

}
