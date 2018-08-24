package com.ceiba.parqueadero.unitaria;


import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.entity.Vehiculo;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.validaciones.ValidarCantidad;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarCantidadTest {
	
	
	@Autowired
	ValidarCantidad validarCantidad;
	
	@Autowired
	RegistroParqueoRepository registroParqueoRepository;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Test
	public void validarDisponibilidadEntradaParqueadero() {
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		String msg = validarCantidad.ejecutarValidacionesEntrada(
				modelMapper.map(registroParqueo, RegistroParqueoDTO.class), registroParqueoRepository);

		assertEquals(null, msg);
	}
	
	

}