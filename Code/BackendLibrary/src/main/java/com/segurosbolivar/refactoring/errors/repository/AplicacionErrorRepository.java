package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.AplicacionError;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AplicacionErrorRepository extends JpaRepository<AplicacionError, Long>{

}
