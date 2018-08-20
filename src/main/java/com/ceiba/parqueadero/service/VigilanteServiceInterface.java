package com.ceiba.parqueadero.service;

import java.util.List;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;

public interface VigilanteServiceInterface {


	List<RegistroParqueoDTO> listarRegistroParqueos();

	void crearRegistro(RegistroParqueoDTO registroParqueoDTO);

	String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto);

}
