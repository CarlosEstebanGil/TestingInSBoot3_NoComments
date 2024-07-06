package com.charly.test.servicios;

import java.math.BigDecimal;
import java.util.List;


import com.charly.test.entidades.Cuenta;
import com.charly.test.entidades.dtos.TransaccionDTO;



public interface CuentaServicio { 
								  

	public List<Cuenta> listarCuentas(); 
	
	public Cuenta buscarCuentaPorId(Long id);
	public Cuenta guardarCuenta( Cuenta cuenta);
	
	public int revisarTotalDeTransferencias ( Long bancoId);
	
	public BigDecimal revisarSaldo(Long cuentaId);
	
	public void transferirDinero(TransaccionDTO transaccionDTO); 
	
	
}	
