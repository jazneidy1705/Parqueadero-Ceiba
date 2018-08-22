package com.ceiba.parqueadero.parametrizacion;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.util.TipoVehiculoEnum;

@Component
public class ParametrizacionFabrica {

	public IParametrizacion conexionFabrica(TipoVehiculoEnum tipoVehiculo) {

		if (tipoVehiculo.equals(TipoVehiculoEnum.CARRO)) {
			return new ParametrizacionMoto();
		}
		if (tipoVehiculo.equals(TipoVehiculoEnum.MOTO)) {
			return new ParametrizacionCarro();
		}
		return null;
	}

}
