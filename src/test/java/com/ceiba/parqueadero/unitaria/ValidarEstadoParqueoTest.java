package com.ceiba.parqueadero.unitaria;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.dominio.validacionreglasnegocio.ValidarEstadoParqueo;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarEstadoParqueoTest {
	
	
	@Autowired
	ValidarEstadoParqueo validarEstado;
	
	@Autowired
	RegistroParqueoRepository registroRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	@Test
	public void validarEstadoRegistroParqueo() {
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("ABC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		String msg = validarEstado.ejecutarValidacionesEntrada(
				modelMapper.map(registroParqueo, RegistroParqueoDTO.class), registroRepository);
		assertEquals(null, msg);
		
	}
	
	@Test
	public void validarRegistroParqueoActivo() {
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		String msg = validarEstado.ejecutarValidacionesEntrada(
				modelMapper.map(registroParqueo, RegistroParqueoDTO.class), registroRepository);
		assertEquals("El vehiculo tiene actualmente un registro de parqueo Activo", msg);
		
	}
}
