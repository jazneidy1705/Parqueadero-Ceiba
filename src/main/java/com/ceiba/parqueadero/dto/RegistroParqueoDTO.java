package com.ceiba.parqueadero.dto;

import java.util.Date;

import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;



public class RegistroParqueoDTO {

	private Date fechaEntrada;

	private Date fechaSalida;
	
	private double valorFacturado;
	
	private String tiempo;
	
	private EstadoRegistroParqueoEnum estadoRegistroParqueo;
	
	private VehiculoDTO vehiculo;
	
	
	public double getValorFacturado() {
		return valorFacturado;
	}

	public void setValorFacturado(double valorFacturado) {
		this.valorFacturado = valorFacturado;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public Date getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public VehiculoDTO getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoDTO vehiculo) {
		this.vehiculo = vehiculo;
	}

	public EstadoRegistroParqueoEnum getEstadoRegistroParqueo() {
		return estadoRegistroParqueo;
	}

	public void setEstadoRegistroParqueo(EstadoRegistroParqueoEnum estadoRegistroParqueo) {
		this.estadoRegistroParqueo = estadoRegistroParqueo;
	}

	
	
}
