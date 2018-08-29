package com.ceiba.parqueadero.dominio.configuracionreglasnegocio;

import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;

@Component
public class ParametrizacionFabrica {

	public IParametrizacion conexionFabrica(TipoVehiculoEnum tipoVehiculo) {

		if (tipoVehiculo.equals(TipoVehiculoEnum.CARRO)) {
			return new ParametrizacionCarro();
		}
		if (tipoVehiculo.equals(TipoVehiculoEnum.MOTO)) {
			return new ParametrizacionMoto();
		}
		return null;
	}

}
