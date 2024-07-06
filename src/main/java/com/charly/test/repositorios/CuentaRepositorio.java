package com.charly.test.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.charly.test.entidades.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long>{

	Optional<Cuenta> findByPersona(String persona); 
	
}													
