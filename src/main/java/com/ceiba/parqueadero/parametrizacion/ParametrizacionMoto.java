package com.ceiba.parqueadero.parametrizacion;

import org.springframework.stereotype.Component;

@Component
public class ParametrizacionMoto implements IParametrizacion {

	private double valorHoraMoto;

	private double valorDiaMoto;

	private int cantidadMoto;


	public ParametrizacionMoto() {
		this.valorHoraMoto = 500;
		this.valorDiaMoto = 4000;
		this.cantidadMoto = 10;
	}

	@Override
	public double tarifaValorHora() {

		return valorHoraMoto;
	}

	@Override
	public double tarifaValorDia() {
		
		return valorDiaMoto;
	}

	@Override
	public int cantidadVehiculos() {
		return cantidadMoto;
	}

}
