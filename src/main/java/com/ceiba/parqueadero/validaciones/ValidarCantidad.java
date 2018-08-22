package com.ceiba.parqueadero.validaciones;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.parametrizacion.IParametrizacion;
import com.ceiba.parqueadero.parametrizacion.ParametrizacionFabrica;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;

@Component
public class ValidarCantidad implements IValidacionEntrada {

	ParametrizacionFabrica fabricaParametrizacion= new ParametrizacionFabrica();

	@Override
	public String ejecutarValidacionesEntrada(RegistroParqueoDTO registroParqueoDto,RegistroParqueoRepository registroParqueoRepository) {

		Integer cantidad =registroParqueoRepository
				.countByVehiculoTipoVehiculoAndFechaSalidaIsNull(registroParqueoDto.getVehiculo().getTipoVehiculo());
		
		IParametrizacion parametrizacion = fabricaParametrizacion
				.conexionFabrica(registroParqueoDto.getVehiculo().getTipoVehiculo());

		if (cantidad >= parametrizacion.cantidadVehiculos()) {
			return "El Parqueadero a alcanzado el Maximo numero de Vehiculos  permitido";
		}

		return null;
	}

}
