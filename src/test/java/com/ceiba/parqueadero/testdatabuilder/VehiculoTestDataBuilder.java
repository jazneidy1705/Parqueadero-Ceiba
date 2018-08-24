package com.ceiba.parqueadero.testdatabuilder;

import com.ceiba.parqueadero.entity.Vehiculo;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;

public class VehiculoTestDataBuilder {

	private final static int CILINDRAJE = 300;
	private final static String PLACA = "OYS12C";

	private int cilindraje;
	private String placa;
	private TipoVehiculoEnum tipoVehiculo;

	public VehiculoTestDataBuilder() {
		this.cilindraje = CILINDRAJE;
		this.placa = PLACA;
		this.tipoVehiculo = TipoVehiculoEnum.CARRO;
	}
	
	public Vehiculo build() {
		Vehiculo vehiculoNuevo= new Vehiculo();
		vehiculoNuevo.setCilindraje(cilindraje);
		vehiculoNuevo.setPlaca(placa);
		vehiculoNuevo.setTipoVehiculo(tipoVehiculo);
		return vehiculoNuevo;
	}

	/**
	 * @param cilindraje the cilindraje to set
	 */
	public VehiculoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}

	/**
	 * @param placa the placa to set
	 */
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}

	/*
	/**
	 * @param tipoVehiculo the tipoVehiculo to set
	 */
	public VehiculoTestDataBuilder conTipoVehiculo(TipoVehiculoEnum tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	
}
