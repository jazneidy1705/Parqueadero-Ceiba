package com.ceiba.parqueadero.parametrizacion;

import org.springframework.stereotype.Component;

@Component
public class ParametrizacionMoto implements IParametrizacion {

	private double valorHoraMoto;

	private double valorDiaMoto;

	private int cantidadMoto;

	private int valorAdicionalCilindraje;

	public ParametrizacionMoto() {
		this.valorHoraMoto = 500;
		this.valorDiaMoto = 4000;
		this.cantidadMoto = 10;
		this.valorAdicionalCilindraje = 2000;
	}

	@Override
	public double tarifaValorHora(int cilindraje) {

		if (cilindraje > 500) {
			return valorHoraMoto + valorAdicionalCilindraje;
		}
		return valorHoraMoto;
	}

	@Override
	public double tarifaValorDia(int cilindraje) {
		
		if (cilindraje > 500) {
			return  valorDiaMoto+valorAdicionalCilindraje;
		}
		return valorDiaMoto;
	}

	@Override
	public int cantidadVehiculos() {
		return cantidadMoto;
	}

}
