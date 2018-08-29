package com.ceiba.parqueadero.presentacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parqueadero.aplicacion.service.VigilanteServiceInterface;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class RegistroParqueoController {

	@Autowired
	VigilanteServiceInterface vigilanteService;

	@GetMapping("/registrosParqueo")
	public List<RegistroParqueoDTO> listarRegistrosParqueos() {
		return vigilanteService.listarRegistroParqueos();
	}

	@PostMapping("/registrosParqueo")
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroParqueoDTO  crearRegistroParqueo(@RequestBody RegistroParqueoDTO registroParqueoDto) {

		return  vigilanteService.crearRegistroEntrada(registroParqueoDto);
	}

	@PutMapping("/registrosParqueo/{placa}")
	@ResponseStatus(HttpStatus.CREATED)
	public RegistroParqueoDTO crearRegistroSalida(@PathVariable(value = "placa") String placa) {
		return vigilanteService.crearRegistroSalida(placa);
	}

	@GetMapping("/registrosParqueo/{placa}")
	public RegistroParqueoDTO show(@PathVariable(value = "placa") String placa) {
		return vigilanteService.buscarVehiculoParqueado(placa);
	}

}
