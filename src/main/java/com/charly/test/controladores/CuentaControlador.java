package com.charly.test.controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charly.test.entidades.Cuenta;
import com.charly.test.entidades.dtos.RespuestaTransferenciaDTO;
import com.charly.test.entidades.dtos.TransaccionDTO;
import com.charly.test.servicios.CuentaServicio;


@RestController	
@RequestMapping("/api/cuentas")	
public class CuentaControlador {

	@Autowired
	CuentaServicio cuentaServicio;
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public Cuenta verDetalles(@PathVariable Long id) {	
		return cuentaServicio.buscarCuentaPorId(id);	
	}
	
	@PostMapping("/transferir")	
	public ResponseEntity<?> transferirDinero(@RequestBody TransaccionDTO transaccionDTO) {	
		
	
		cuentaServicio.transferirDinero(transaccionDTO);
		
		
		RespuestaTransferenciaDTO rtaTransfDTO = new RespuestaTransferenciaDTO();
		
		rtaTransfDTO.setDate(LocalDate.now()); 
		rtaTransfDTO.setStatus("OK");
		rtaTransfDTO.setMensaje("Transferencia realizada con exito");
		rtaTransfDTO.setTransaccion(transaccionDTO);
		
		return ResponseEntity.ok(rtaTransfDTO); 
												
	}
				  
	@GetMapping("/listarCuentas")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Cuenta> listarCuentas() {	
		return cuentaServicio.listarCuentas();	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
		return cuentaServicio.guardarCuenta(cuenta);
	}
}