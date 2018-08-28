package com.ceiba.parqueadero.integracion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.controller.RegistroParqueoController;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.entity.Vehiculo;
import com.ceiba.parqueadero.repository.VehiculoRepository;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrearRegistroParqueoTest {

	@Autowired
	RegistroParqueoController registroParqueoController;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Before
	public void datosIniciales() {
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		vehiculoRepository.save(vehiculo);
		
	}

	@Test
	public void creacionRegistroParqueoConVehiculoExistenteTest() {

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
	
		assertNotNull(registro);
	}
	
	
	@Test
	public void creacionRegistroParqueoVehiculoNoExistenteTest() {

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("EBC123").conTipoVehiculo(TipoVehiculoEnum.MOTO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
	
		assertNotNull(registro);
	}
	
	
	@Test
	public void registroParqueoSinVehiculoTest() {

		
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(null)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
		
		assertNull(registro);
	}
	
	
}
