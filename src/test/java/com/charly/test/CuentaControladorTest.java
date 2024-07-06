package com.charly.test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.charly.test.controladores.CuentaControlador;
import com.charly.test.entidades.Cuenta;
import com.charly.test.entidades.dtos.RespuestaTransferenciaDTO;
import com.charly.test.entidades.dtos.TransaccionDTO;
import com.charly.test.servicios.CuentaServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(CuentaControlador.class) 
class CuentaControladorTest {

	@Autowired
	private MockMvc mockMvc; 
	

	@MockBean 
	private CuentaServicio cuentaServicio;	

	void testVerDetalles() throws Exception{ 

		when(cuentaServicio.buscarCuentaPorId(2L))
			.thenReturn( Datos.crearCuenta002().orElseThrow());

		mockMvc.perform(get("/api/cuentas/2").contentType(MediaType.APPLICATION_JSON)) 
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.persona").value("esteban"))
				.andExpect(jsonPath("$.saldo").value("2000"));
		
		verify(cuentaServicio).buscarCuentaPorId(2L);	
	}													
														

	ObjectMapper objectMapper;
	
	@BeforeEach
	void configurar() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void testTransferirDinero() throws Exception {
		
		TransaccionDTO transaccionDTO = new TransaccionDTO();
		
		transaccionDTO.setCuentaOrigenId(1L);
		transaccionDTO.setCuentaDestinoId(2L);
		transaccionDTO.setBancoId(1L);				   
		transaccionDTO.setMonto(new BigDecimal(5000)); 
		
		
		RespuestaTransferenciaDTO respuestaDto = 
		
		
				RespuestaTransferenciaDTO.builder().date(LocalDate.now())				
						   .status("OK")
						   .mensaje("Transferencia realizada con exito")
						   .transaccion(transaccionDTO)
						   .build();
		
			doNothing().when(cuentaServicio).transferirDinero(any(TransaccionDTO.class)); 

		mockMvc.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
															.content( objectMapper.writeValueAsString(transaccionDTO) ) )
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
			
			.andExpect(jsonPath("$.mensaje").value("Transferencia realizada con exito"))

			.andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(transaccionDTO.getCuentaOrigenId()))
			.andExpect(jsonPath("$.transaccion.cuentaDestinoId").value(transaccionDTO.getCuentaDestinoId()))

			.andExpect(jsonPath("$.transaccion.monto").value(transaccionDTO.getMonto().intValue()))
            .andExpect(jsonPath("$.transaccion.bancoId").value(transaccionDTO.getBancoId()));
			
		
	}
	
	@Test	
			
	void testListarCuentas() throws Exception { 
		
				when(cuentaServicio.listarCuentas())						
					.thenReturn( Datos.getListaCuentas());					

				
				mockMvc.perform(get("/api/cuentas/listarCuentas").contentType(MediaType.APPLICATION_JSON)) 
						.andExpect(status().isOk())
						.andExpect(content().contentType(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$[0].persona").value("charly"))
						.andExpect(jsonPath("$[0].saldo").value("1000")) 									 
						.andExpect(jsonPath("$[1].persona").value("esteban"))								 
						.andExpect(jsonPath("$[1].saldo").value("2000"))									 
																											 
						.andExpect(jsonPath("$",hasSize(2)))	
						
						.andExpect(content().json((objectMapper.writeValueAsString( Datos.getListaCuentas() ) )) );  
																													
				
				verify(cuentaServicio).listarCuentas();			
	}
	@Test
	void guardarCuenta() throws Exception{
		Cuenta cuenta = new Cuenta(null,"Noe", new BigDecimal(3000));
		
		when(cuentaServicio.guardarCuenta(cuenta))
				.then( invocation -> {
								Cuenta c = invocation.getArgument(0); 
								c.setId(3L); 
								return c;	
							}
					 ); 
				

		mockMvc.perform( post("/api/cuentas") 
							.contentType( MediaType.APPLICATION_JSON )
									.content( objectMapper.writeValueAsString(cuenta) ) ) 
			
							.andExpect( content().contentType(MediaType.APPLICATION_JSON) );
						
		verify(cuentaServicio).guardarCuenta(any());
	}
}
