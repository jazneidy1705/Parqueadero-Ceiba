package com.ceiba.parqueadero.service;

import java.util.List;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.excepciones.ParqueaderoException;

public interface VigilanteServiceInterface {

	List<RegistroParqueoDTO> listarRegistroParqueos();

	RegistroParqueoDTO crearRegistroEntrada(RegistroParqueoDTO registroParqueoDTO) throws ParqueaderoException;

//	String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto);

	double calcularTiempoCobrarParqueadero(RegistroParqueo registroParqueo);

	void crearRegistroSalida(String placa);

	RegistroParqueoDTO buscarVehiculoParqueado(String placa);

}
