package com.ceiba.parqueadero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.service.VigilanteServiceInterface;

@RestController
@RequestMapping("/Parqueadero")
public class RegistroParqueoController {

	@Autowired
	VigilanteServiceInterface vigilanteService;

	@GetMapping("/listarRegistrosParqueo")
	public List<RegistroParqueoDTO> listarParametrizaciones() {
		return vigilanteService.listarRegistroParqueos();
	}

	@PostMapping("/crearRegistroParqueo")
	public void crearRegistroParqueo(@RequestBody RegistroParqueoDTO registroParqueoDto) {
		String mensaje = vigilanteService.realizarvalidacionesDeEntrada(registroParqueoDto);
		if(mensaje!=null) {
			throw new UnsupportedOperationException(mensaje);
		}else {	
			vigilanteService.crearRegistro(registroParqueoDto);
		}
	}

}
