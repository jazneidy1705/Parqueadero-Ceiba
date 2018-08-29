package com.ceiba.parqueadero.testdatabuilder;

import java.util.Date;

import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;

public class RegistroParqueoTestDataBuilder {
	
	private Date fechaEntrada;
	private EstadoRegistroParqueoEnum estadoRegistro;
	private Vehiculo vehiculo;
	private Date fechaSalida;
	
	public RegistroParqueoTestDataBuilder() {
		this.fechaEntrada= new Date();
		this.estadoRegistro = EstadoRegistroParqueoEnum.ACTIVO;
		this.vehiculo= new Vehiculo();
	}

	
	public RegistroParqueo build() {
		RegistroParqueo registroParqueoNuevo=  new RegistroParqueo();
		registroParqueoNuevo.setFechaEntrada(fechaEntrada);
		registroParqueoNuevo.setEstadoRegistroParqueo(estadoRegistro);
		registroParqueoNuevo.setVehiculo(vehiculo);
		registroParqueoNuevo.setFechaSalida(fechaSalida);
		return registroParqueoNuevo;
	}

	/**
	 * @param fechaEntrada the fechaEntrada to set
	 */
	public RegistroParqueoTestDataBuilder conFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}


	/**
	 * @param estadoRegistro the estadoRegistro to set
	 */
	public RegistroParqueoTestDataBuilder conEstadoRegistro(EstadoRegistroParqueoEnum estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
		return this;
	}

	/**
	 * @param vehiculo the vehiculo to set
	 */
	public RegistroParqueoTestDataBuilder conVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}
	
	public RegistroParqueoTestDataBuilder conFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}
	
	
	

	
	

}
