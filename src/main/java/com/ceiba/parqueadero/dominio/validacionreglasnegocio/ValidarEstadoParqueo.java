package com.ceiba.parqueadero.dominio.validacionreglasnegocio;


import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;

@Component
public class ValidarEstadoParqueo implements IValidacionEntrada {

	@Override
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,
			RegistroParqueoRepository registroParqueoRepository) {

		RegistroParqueo registroExistente = registroParqueoRepository.findByVehiculoPlacaAndEstadoRegistroParqueo(
				registroParqueoDto.getVehiculo().getPlaca(), EstadoRegistroParqueoEnum.ACTIVO);
		
		if(registroExistente!=null) {
			return "El vehiculo tiene actualmente un registro de parqueo Activo";
		}

		return null;
	}

}
