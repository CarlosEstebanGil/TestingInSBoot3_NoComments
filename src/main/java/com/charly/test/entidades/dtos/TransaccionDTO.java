package com.charly.test.entidades.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder					  
public class TransaccionDTO { 
	
	private Long cuentaOrigenId;
	private Long cuentaDestinoId;
	
	private BigDecimal monto;

	private Long bancoId;
}
