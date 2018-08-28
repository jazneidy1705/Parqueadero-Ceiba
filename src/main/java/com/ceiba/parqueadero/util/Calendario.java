package com.ceiba.parqueadero.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Calendario {
	
	
	public static final int DIVISION_MIL = 1000;
	public static final int DIA_MILISEGUNDO = 86400;
	public static final int HORA_MILISEGUNDO = 3600;
	public static final int MINUTO_MILISEGUNDO = 60;
	
	private int diasParqueo;
	private int horasParqueo;
	private int minutosParqueo;
	
	public Calendar fechaACtual() {
		return Calendar.getInstance();
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
			diferencia -= (MINUTO_MILISEGUNDO  * 60);
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
