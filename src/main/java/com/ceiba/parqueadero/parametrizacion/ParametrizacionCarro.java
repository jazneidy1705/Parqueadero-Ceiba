package com.ceiba.parqueadero.parametrizacion;

import org.springframework.stereotype.Component;


@Component
public class ParametrizacionCarro implements IParametrizacion{
	
	private double valorHoraCarro;
	
	private double valorDiaCarro; 
	
	private int cantidadCarros;
	
	public ParametrizacionCarro() {
		this.valorHoraCarro = 1000;
		this.valorDiaCarro = 8000;
		this.cantidadCarros=20;
		
	}
	
	@Override
	public double tarifaValorHora(int cilindraje) {
		return valorHoraCarro;
	}

	@Override
	public double tarifaValorDia(int cilindraje) {
		return valorDiaCarro;
	}

	@Override
	public int cantidadVehiculos() {
		return cantidadCarros;
	}


}
