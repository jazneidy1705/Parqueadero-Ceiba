package com.ceiba.parqueadero.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ceiba.parqueadero.dominio.configuracionreglasnegocio.IParametrizacion;
import com.ceiba.parqueadero.dominio.configuracionreglasnegocio.ParametrizacionFabrica;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;

@Component
public class CalcularTarifa {
	
	
	public static final int DIVISION_MIL = 1000;
	public static final int DIA_MILISEGUNDO = 86400;
	public static final int HORA_MILISEGUNDO = 3600;
	public static final int MINUTO_MILISEGUNDO = 60;
	public static final int HORA_INICIO= 9;
	public static final int HORA_FIN= 24;
	public static final int COSTO_ADICIONAL = 2000;
	public static final int CILINDRAJE = 500;
	
	
	private int diasParqueo;
	private int horasParqueo;
	private int minutosParqueo;
	
	@Autowired
	ParametrizacionFabrica fabricaParametrizacion;
	
	public Calendar fechaACtual() {
		return Calendar.getInstance();
	}
	
	public double calcularTarifaACobrarParqueadero(RegistroParqueo registroParqueo) {

		double valorCobrado = 0.0;
		this.calcularTiempoParqueo(registroParqueo.getFechaEntrada(), registroParqueo.getFechaSalida());
		
		
		if(minutosParqueo>0) {
			horasParqueo++;
			minutosParqueo = 0;
						
		}
		if(horasParqueo >= HORA_INICIO) {
			if(horasParqueo <= HORA_FIN) {
				diasParqueo ++;
				horasParqueo=0;
			}else {
				diasParqueo ++;
				horasParqueo-=HORA_FIN;
			}
			
		}
		
		valorCobrado = calcularValorTarifaHora(horasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
		if (registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)
				&& registroParqueo.getVehiculo().getCilindraje() > CILINDRAJE) {
			valorCobrado = valorCobrado + COSTO_ADICIONAL;
		}
		valorCobrado += calcularValorTarifaDia(diasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());

		return valorCobrado;

	}
	
	
	/**
	 * metodo para calcular el valor de la Tarifa por Hora
	 * 
	 * @param tiempoParqueo
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @return
	 */
	public Double calcularValorTarifaHora(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		return tiempoParqueo * tarifa.tarifaValorHora();

	}

	/**
	 * Metodo para calcular el valor de la Tarifa por dia
	 * 
	 * @param tiempoParqueo
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @return
	 */
	public Double calcularValorTarifaDia(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		return tiempoParqueo * tarifa.tarifaValorDia();

	}
		
	public void calcularTiempoParqueo(Date fechaEntrada, Date fechaSalida) {

		int diferencia = (int) ((fechaSalida.getTime() - fechaEntrada.getTime()) /  DIVISION_MIL);
		
		if (diferencia >= DIA_MILISEGUNDO ) {
			this.diasParqueo = (diferencia / DIA_MILISEGUNDO );
			diferencia -=(diasParqueo * DIA_MILISEGUNDO );
		}
		if (diferencia >= HORA_MILISEGUNDO) {
			this.horasParqueo = (diferencia / HORA_MILISEGUNDO);
			diferencia -= (horasParqueo * HORA_MILISEGUNDO);
		}
		if (diferencia >= MINUTO_MILISEGUNDO ) { 
			this.minutosParqueo = (diferencia / MINUTO_MILISEGUNDO );
		}
	}

	public int getDiasParqueo() {
		return diasParqueo;
	}

	public void setDiasParqueo(int diasParqueo) {
		this.diasParqueo = diasParqueo;
	}

	public int getHorasParqueo() {
		return horasParqueo;
	}

	public void setHorasParqueo(int horasParqueo) {
		this.horasParqueo = horasParqueo;
	}

	public int getMinutosParqueo() {
		return minutosParqueo;
	}

	public void setMinutosParqueo(int minutosParqueo) {
		this.minutosParqueo = minutosParqueo;
	}
	
}
