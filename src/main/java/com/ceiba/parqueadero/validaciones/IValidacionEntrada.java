package com.ceiba.parqueadero.validaciones;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;

public interface IValidacionEntrada {
	
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,RegistroParqueoRepository registroParqueoRepository) ;

}
