package com.ceiba.parqueadero.parametrizacion;

import com.ceiba.parqueadero.util.TipoVehiculoEnum;

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
