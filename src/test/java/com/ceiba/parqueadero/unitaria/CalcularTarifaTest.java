package com.ceiba.parqueadero.unitaria;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.dominio.util.CalcularTarifa;
import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalcularTarifaTest {

	
	@Autowired
	CalcularTarifa Tarifa;
	
	@Autowired
	RegistroParqueoRepository registroParqueoRepository;


	RegistroParqueo registroParqueo;
	
	
	 @Before
	    public void setUp() {
	        registroParqueo=mock(RegistroParqueo.class);
	        
	    }

		
	@Test
	public void calcularTarifaPorHoraMoto() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaIngreso = sdf.parse("2018-08-28 06:00:00");
		Date fechaSalida = sdf.parse("2018-08-28 10:00:00");
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.MOTO)
				.conCilindraje(600).build();
		
		when(registroParqueo.getFechaSalida()).thenReturn(fechaSalida);
		
		registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(fechaIngreso).conVehiculo(vehiculo).conFechaSalida(fechaSalida)
				.build();
		
		
		registroParqueoRepository.saveAndFlush(registroParqueo);
		
		double valor= Tarifa.calcularTarifaACobrarParqueadero(registroParqueo);
		
		assertEquals(4000.0, valor, 1);

	}
	
	@Test
	public void calcularTarifaPorHoraCarro() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaIngreso = sdf.parse("2018-08-28 06:00:00");
		Date fechaSalida = sdf.parse("2018-08-28 10:00:00");
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PAI123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(2000).build();
		
		when(registroParqueo.getFechaSalida()).thenReturn(fechaSalida);
		
		registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(fechaIngreso).conVehiculo(vehiculo).conFechaSalida(fechaSalida)
				.build();
		
		registroParqueoRepository.saveAndFlush(registroParqueo);
		
		double valor= Tarifa.calcularTarifaACobrarParqueadero(registroParqueo);
		
		assertEquals(4000.0, valor, 1);

	}
	
	
	@Test
	public void calcularTarifaPorDiaCarro() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fechaIngreso = sdf.parse("2018-08-27 06:00:00");
		Date fechaSalida = sdf.parse("2018-08-28 10:00:00");
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PAC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(2000).build();
		
		when(registroParqueo.getFechaSalida()).thenReturn(fechaSalida);
		
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(fechaIngreso).conVehiculo(vehiculo).conFechaSalida(fechaSalida)
				.build();
				
		registroParqueoRepository.saveAndFlush(registroParqueo);
		
		double valor= Tarifa.calcularTarifaACobrarParqueadero(registroParqueo);
		
		assertEquals(12000.0, valor, 1);

	}


}
