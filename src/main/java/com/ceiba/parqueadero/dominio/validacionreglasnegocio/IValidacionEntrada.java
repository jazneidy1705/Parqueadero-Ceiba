package com.ceiba.parqueadero.dominio.validacionreglasnegocio;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;

public interface IValidacionEntrada {
	
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,RegistroParqueoRepository registroParqueoRepository) ;

}
