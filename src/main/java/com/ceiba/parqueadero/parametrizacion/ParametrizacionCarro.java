package com.ceiba.parqueadero.parametrizacion;


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
	public double tarifaValorHora() {
		return valorHoraCarro;
	}

	@Override
	public double tarifaValorDia() {
		return valorDiaCarro;
	}

	@Override
	public int cantidadVehiculos() {
		return cantidadCarros;
	}


}
