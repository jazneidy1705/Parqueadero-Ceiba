package com.ceiba.parqueadero.parametrizacion;


public interface IParametrizacion {
	
	public double tarifaValorHora(int cilindraje);
	
	public double tarifaValorDia(int cilindraje);
	
	public int cantidadVehiculos();

}
