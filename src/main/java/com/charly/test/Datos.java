package com.charly.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.charly.test.entidades.Banco;
import com.charly.test.entidades.Cuenta;

public class Datos {

	public static Optional<Cuenta> crearCuenta001(){
		return Optional.of(new Cuenta(1L,"charly",new BigDecimal("1000")));
	}
	
	public static Optional<Cuenta> crearCuenta002(){
		return Optional.of(new Cuenta(2L,"esteban",new BigDecimal("2000")));
	}
	
	public static Optional<Banco> crearBanco001(){
		return Optional.of(new Banco(1L, "rio", 0));
	}
	
	public static Optional<Banco> crearBanco002(){
		return Optional.of(new Banco(2L, "nacion", 0));
	}
	public static List<Cuenta> getListaCuentas(){
		return Arrays.asList(	Datos.crearCuenta001().orElseThrow(),
								Datos.crearCuenta002().orElseThrow() );
	}
}
