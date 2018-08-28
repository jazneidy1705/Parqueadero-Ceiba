package com.ceiba.parqueadero.service;

import java.util.List;

import com.ceiba.parqueadero.dto.ParqueaderoExceptionDTO;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.excepciones.ParqueaderoException;

public interface VigilanteServiceInterface {

	List<RegistroParqueoDTO> listarRegistroParqueos();

	RegistroParqueoDTO crearRegistroEntrada(RegistroParqueoDTO registroParqueoDTO) throws ParqueaderoException;

   String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto);

   RegistroParqueoDTO crearRegistroSalida(String placa) throws ParqueaderoExceptionDTO;

   RegistroParqueoDTO buscarVehiculoParqueado(String placa);

}
