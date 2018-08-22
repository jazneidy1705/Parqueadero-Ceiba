package com.ceiba.parqueadero.validaciones;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;

@Component
public class ValidarEstadoParqueo implements IValidacionEntrada {

	@Override
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,
			RegistroParqueoRepository registroParqueoRepository) {

		Optional<RegistroParqueo> registroExistente = registroParqueoRepository.findByVehiculoPlacaAndEstadoRegistroParqueo(
				registroParqueoDto.getVehiculo().getPlaca(), EstadoRegistroParqueoEnum.ACTIVO);
		
		if(registroExistente.isPresent()) {
			return "El vehiculo tiene actualmente un registro de parqueo Activo";
		}

		return null;
	}

}
