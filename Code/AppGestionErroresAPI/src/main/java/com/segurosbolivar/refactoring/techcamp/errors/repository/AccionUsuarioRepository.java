package com.segurosbolivar.refactoring.techcamp.errors.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.segurosbolivar.refactoring.techcamp.errors.model.AccionUsuario;

public interface AccionUsuarioRepository extends JpaRepository<AccionUsuario, Long>{
	
	@Query("SELECT au FROM AccionUsuario au WHERE au.aplicacionError.idAplicacionError=:idAplicationError")
	public List<AccionUsuario> findAllByAplicacionErrorIdAplicacionError(@Param("idAplicationError") Long idAplicationError);


}
