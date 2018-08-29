package com.ceiba.parqueadero.unitaria;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.parqueadero.dominio.util.CalcularTarifa;
import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.dominio.validacionreglasnegocio.ValidarPlaca;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;
import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;
import com.ceiba.parqueadero.infraestructura.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.testdatabuilder.RegistroParqueoTestDataBuilder;
import com.ceiba.parqueadero.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidarPlacaTest {

	@Autowired
	ValidarPlaca validarPlaca;

	@MockBean
	CalcularTarifa calendario;

	@Autowired
	RegistroParqueoRepository registroRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	@Test
	public void validarDiaHabilEntradaParqueadero() {

		Calendar lunes = Calendar.getInstance();
		lunes.set(2018, 8, 27);
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca("ABC123").conTipoVehiculo(TipoVehiculoEnum.CARRO)
				.conCilindraje(500).build();
		RegistroParqueo registroParqueo = new RegistroParqueoTestDataBuilder()
				.conEstadoRegistro(EstadoRegistroParqueoEnum.ACTIVO).conFechaEntrada(new Date()).conVehiculo(vehiculo)
				.build();

		when(calendario.fechaACtual()).thenReturn(lunes);
		//when(calendario.fechaACtual()).thenCallRealMethod();
		String msg = validarPlaca.ejecutarValidacionesEntrada(
				modelMapper.map(registroParqueo, RegistroParqueoDTO.class), registroRepository);
		assertEquals("No esta autorizado a ingresar", msg);
	}

}
