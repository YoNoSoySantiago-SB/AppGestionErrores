package com.segurosbolivar.refactoring.errors.repository;

import com.segurosbolivar.refactoring.errors.model.AccionUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

interface AccionUsuarioRepository extends JpaRepository<AccionUsuario, Long>{

}
