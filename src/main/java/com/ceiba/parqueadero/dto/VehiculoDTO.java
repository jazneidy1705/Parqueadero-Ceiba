package com.ceiba.parqueadero.dto;

import com.ceiba.parqueadero.util.TipoVehiculoEnum;

public class VehiculoDTO {
	
	private Long id;
	private int cilindraje;
	private String placa;
	private TipoVehiculoEnum tipoVehiculo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public int getCilindraje() {
		return cilindraje;
	}
	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}
	public TipoVehiculoEnum getTipoVehiculo() {
		return tipoVehiculo;
	}
	public void setTipoVehiculo(TipoVehiculoEnum tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	
}
