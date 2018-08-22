package com.ceiba.parqueadero.service;

import java.util.List;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;

public interface VigilanteServiceInterface {


	List<RegistroParqueoDTO> listarRegistroParqueos();

	void crearRegistroEntrada(RegistroParqueoDTO registroParqueoDTO);

	String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto);

	double calcularTiempoCobrarParqueadero(RegistroParqueo registroParqueo);

	void crearRegistroSalida(String placa);

}
