package com.ceiba.parqueadero.validaciones;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;

@Component
public class ValidarPlaca implements IValidacionEntrada {

	@Override
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,RegistroParqueoRepository registroParqueoRepository) {

		Calendar hoy = Calendar.getInstance();
		char primeraLetra;
		String placa = registroParqueoDto.getVehiculo().getPlaca();

		for (int i = placa.length() - 1; i >= 0; i--) {

			primeraLetra = placa.charAt(i);

			if (primeraLetra == 'A' && hoy.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
					&& hoy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				return "No esta autorizado a ingresar";
			}
		}
		return null;
	}

}