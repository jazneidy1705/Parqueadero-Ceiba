package com.ceiba.parqueadero.aplicacion.service;

import java.util.List;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.excepciones.ParqueaderoException;

public interface VigilanteServiceInterface {

	/**
	 * metodo para listar los registros de parqueos que estan activos, que estan
	 * pendientes por facturar
	 * 
	 * @return lista de registros parqueos activos
	 */
	List<RegistroParqueoDTO> listarRegistroParqueos();

	/**
	 * metodo para crear el registro de entrada a un parqueadero
	 * 
	 * @param registroParqueoDTO,
	 *            registro parqueo a crear, este parametro a su vez tiene el
	 *            vehiculo a parquear @return, el registro parqueo que se crea
	 * @throws ParqueaderoException
	 */
	RegistroParqueoDTO crearRegistroEntrada(RegistroParqueoDTO registroParqueoDTO) ;

	/**
	 * metodo que crea el registro de salida del parqueadero, en este metodo se llama el la clase que realiza el calculo
	 * @param placa, placa del vehiculo que va a salir del parqueadero
	 * @return retorna el registro a actualizar
	 * @throws ParqueaderoException
	 */
	RegistroParqueoDTO crearRegistroSalida(String placa) ;

	/**
	 * metodo para buscar un vehiculo por placa
	 * @param placa, placa del vehiculo
	 * @return, el vehiculo que existe por esta placa
	 */
	RegistroParqueoDTO buscarVehiculoParqueado(String placa);

}
