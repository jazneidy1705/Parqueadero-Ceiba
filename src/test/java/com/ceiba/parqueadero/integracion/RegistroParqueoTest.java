package com.ceiba.parqueadero.integracion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.excepciones.ParqueaderoException;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.presentacion.controller.RegistroParqueoController;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistroParqueoTest {

	@Autowired
	RegistroParqueoController registroParqueoController;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
    RegistroParqueoRepository registroParqueoRepository;

	@Test
	public void crearRegistroParqueoConVehiculoExistenteTest() {

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
	
		assertNotNull(registro);
	}
	
	
	@Test
	public void crearRegistroParqueoVehiculoNoExistenteTest() {

		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("EBC123").conTipoVehiculo(TipoVehiculoEnum.MOTO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
	
		assertNotNull(registro);
	}
	
	
	@Test
	public void crearRegistroParqueoSinVehiculoTest() {

		
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(null)
				.build();
		
		RegistroParqueoDTO registro=  registroParqueoController.crearRegistroParqueo(modelMapper.map(registroParqueo, RegistroParqueoDTO.class));
		
		assertNull(registro);
	}

	@Test
	public void listarVehiculoExistentesActivos() {
		
		List<RegistroParqueoDTO> listaVehiculos= registroParqueoController.listarRegistrosParqueos();
		
		assertTrue(listaVehiculos.size() > 0);
	}
	
	@Test
	public void crearRegistroSalida() throws ParqueaderoException {
		
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("PBC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		 RegistroParqueo registroParqueoInicio = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();
		registroParqueoRepository.saveAndFlush(registroParqueoInicio);
				
		RegistroParqueoDTO registroActualizado= registroParqueoController.crearRegistroSalida("PBC123");
		
		assertNotNull(registroActualizado);
	}
	
	
}
