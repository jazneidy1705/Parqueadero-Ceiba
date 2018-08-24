package com.ceiba.parqueadero.util;

import java.util.Calendar;

import org.springframework.stereotype.Component;

@Component
public class Calendario {
	
	public Calendar fechaACtual() {
		return Calendar.getInstance();
	}
	
}
