package com.charly.test.entidades;

import java.math.BigDecimal;

import com.charly.test.excepciones.DineroInsuficienteException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta { 
					  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String persona;
	
	private BigDecimal saldo;
	
	public void realizarDebito(BigDecimal monto) {
		BigDecimal nuevoSaldo = this.saldo.subtract(monto); 
		if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new DineroInsuficienteException("Dinero Insuficiente en la cuenta.");
		}
		this.saldo = nuevoSaldo;
	}
	
	public void realizarCredito(BigDecimal monto) { 
		this.saldo = this.saldo.add(monto);			
	}
	
	
} 
