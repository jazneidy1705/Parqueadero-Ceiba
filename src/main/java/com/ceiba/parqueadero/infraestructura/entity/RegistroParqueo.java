package com.ceiba.parqueadero.infraestructura.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;

@Entity
@Table(name = "REGISTRO_PARQUEOS")
public class RegistroParqueo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm:ss dd/MM/yyyy")
	private Date fechaEntrada;

	@Column(nullable=true)
	@DateTimeFormat(pattern = "HH:mm:ss dd/MM/yyyy")
	private Date fechaSalida;

	@Enumerated(EnumType.STRING)
	private EstadoRegistroParqueoEnum estadoRegistroParqueo;

	
	@Column(nullable=true)
	private double valorFacturado;

	@Column(nullable=true)
	private int tiempo;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Vehiculo vehiculo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EstadoRegistroParqueoEnum getEstadoRegistroParqueo() {
		return estadoRegistroParqueo;
	}

	public void setEstadoRegistroParqueo(EstadoRegistroParqueoEnum estadoRegistroParqueo) {
		this.estadoRegistroParqueo = estadoRegistroParqueo;
	}

	public double getValorFacturado() {
		return valorFacturado;
	}

	public void setValorFacturado(double valorFacturado) {
		this.valorFacturado = valorFacturado;
	}

	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	
}
