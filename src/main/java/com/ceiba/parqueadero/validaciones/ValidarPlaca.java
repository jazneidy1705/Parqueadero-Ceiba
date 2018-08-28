package com.ceiba.parqueadero.validaciones;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.util.CalcularTarifa;

@Component
public class ValidarPlaca implements IValidacionEntrada {

	CalcularTarifa calendario= new CalcularTarifa();

	
	@Override
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,RegistroParqueoRepository registroParqueoRepository) {

		Calendar hoy = calendario.fechaACtual();
		String placa = registroParqueoDto.getVehiculo().getPlaca();
		if (placa.startsWith("A") && hoy.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				&& hoy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			return "No esta autorizado a ingresar";
		}

		return null;
	}

}
