package com.ceiba.parqueadero.dominio.validacionreglasnegocio;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dominio.util.CalcularTarifa;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;

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
