package com.charly.test.servicios;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charly.test.entidades.Banco;
import com.charly.test.entidades.Cuenta;
import com.charly.test.entidades.dtos.TransaccionDTO;
import com.charly.test.excepciones.ResourceNotFoundException;
import com.charly.test.repositorios.BancoRepositorio;
import com.charly.test.repositorios.CuentaRepositorio;

@Service
public class CuentaServicioImpl implements CuentaServicio{

	@Autowired
	CuentaRepositorio cuentaRepositorio;  
	
	@Autowired
	BancoRepositorio bancoRepositorio; 
	
	

	@Override						
	@Transactional(readOnly = true) 
	public List<Cuenta> listarCuentas() { 
		return cuentaRepositorio.findAll();
   	}

	@Override
	public Cuenta buscarCuentaPorId(Long id) { 
		return cuentaRepositorio.findById(id).orElseThrow(RuntimeException::new);
	} 

	@Override
	@Transactional
	public Cuenta guardarCuenta(Cuenta cuenta) { 
		return cuentaRepositorio.save(cuenta); 
	}

	@Override
	public int revisarTotalDeTransferencias(Long bancoId) { 
		Banco banco= bancoRepositorio.findById(bancoId).orElseThrow(); 
		return banco.getTotalTransferencias();
	}
	
	 
	@Override
	@Transactional(readOnly = true) 
	public BigDecimal revisarSaldo(Long cuentaId) {
		Cuenta cuenta = cuentaRepositorio.findById(cuentaId).orElseThrow();
		return cuenta.getSaldo();
	}

	@Override 
	@Transactional
	public void transferirDinero(TransaccionDTO transaccionDTO) {
	    
		Long numeroCuentaOrigen; Long numeroCuentaDestino; BigDecimal monto; Long bancoId;	
		
		monto=transaccionDTO.getMonto();
		
	    if (monto.compareTo(BigDecimal.ZERO) <= 0) {
	        throw new IllegalArgumentException("El monto debe ser positivo");
	    }
	    
	    numeroCuentaOrigen = transaccionDTO.getCuentaOrigenId();
		numeroCuentaDestino= transaccionDTO.getCuentaDestinoId();
		bancoId=transaccionDTO.getBancoId();
	    
		Cuenta cuentaOrigen = cuentaRepositorio.findById(numeroCuentaOrigen).orElseThrow(() -> new ResourceNotFoundException("Cuenta origen no encontrada")); 
		Cuenta cuentaDestino= cuentaRepositorio.findById(numeroCuentaDestino).orElseThrow(() -> new ResourceNotFoundException("Cuenta destino no encontrada"));
		
		cuentaOrigen.realizarDebito(monto);
		
		cuentaDestino.realizarCredito(monto);
		
		cuentaRepositorio.save(cuentaOrigen);
		cuentaRepositorio.save(cuentaDestino);
		
		Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
		banco.setTotalTransferencias(banco.getTotalTransferencias() +1 );
		
		bancoRepositorio.save(banco); 
		
	}
	
}
