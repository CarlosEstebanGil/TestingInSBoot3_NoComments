package com.charly.test.entidades.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaTransferenciaDTO {

	 private LocalDate date;
	 private String status;
	 private String mensaje;
	 private TransaccionDTO transaccion;
	    
}
