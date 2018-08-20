package com.ceiba.parqueadero.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.entity.Vehiculo;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.repository.VehiculoRepository;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.validaciones.IValidacionEntrada;
import com.ceiba.parqueadero.validaciones.ValidarCantidad;
import com.ceiba.parqueadero.validaciones.ValidarPlaca;

@Service
public class VigilanteService implements VigilanteServiceInterface {

	@Autowired
	RegistroParqueoRepository registroParqueoRepository;

	@Autowired
	VehiculoRepository vehiculoRepository;

	private final ModelMapper modelMapper = new ModelMapper();

//	private final ParametrizacionFabrica parametrizacionFabrica = new ParametrizacionFabrica();

	/**
	 * Constantes de la clase
	 */

	@Override
	@Transactional
	public void crearRegistro(RegistroParqueoDTO registroParqueoDto) {

		RegistroParqueo registroParqueo = modelMapper.map(registroParqueoDto, RegistroParqueo.class);
		Vehiculo vehiculo = modelMapper.map(registroParqueoDto.getVehiculo(), Vehiculo.class);

		Optional<Vehiculo> vehiculoEncontrado = vehiculoRepository.buscarVehiculoPorPlaca(vehiculo.getPlaca());

		if (vehiculoEncontrado.isPresent()) {
			registroParqueo.setVehiculo(vehiculoEncontrado.get());
		} else {
			Vehiculo vehiculoNuevo = vehiculoRepository.saveAndFlush(vehiculo);
			registroParqueo.setVehiculo(vehiculoNuevo);
		}
		registroParqueo.setFechaEntrada(new Date());
		registroParqueo.setEstadoRegistroParqueo(EstadoRegistroParqueoEnum.ACTIVO);
		registroParqueoRepository.save(registroParqueo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RegistroParqueoDTO> listarRegistroParqueos() {
		Iterable<RegistroParqueo> listaParqueos = registroParqueoRepository.findAll();
		List<RegistroParqueo> listaParametrizacion = StreamSupport.stream(listaParqueos.spliterator(), false)
				.collect(Collectors.toList());
		Type listaParqueosDTO = new TypeToken<List<RegistroParqueo>>() {
		}.getType();
		return modelMapper.map(listaParametrizacion, listaParqueosDTO);

	}

	// public void cobrarTiempoParqueadero(String placa) {
	//
	// RegistroParqueo registroParqueo=
	// registroParqueoRepository.buscarRegistroParqueoParafacturar(placa);
	//
	// Long
	// tiempoParqueo=registroParqueo.getFechaEntrada().getTime()-registroParqueo.getFechaSalida().getTime();
	//
	// if(registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.CARRO))
	// {
	//
	// }
	// }

	@Override
	public String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto) {
		ArrayList<IValidacionEntrada> listaValidaciones = new ArrayList<>();
		String msg = null;
		listaValidaciones.add(new ValidarPlaca());
		listaValidaciones.add(new ValidarCantidad());
		for (IValidacionEntrada validacionEntrada : listaValidaciones) {
			msg = validacionEntrada.ejecutarValidaciones(registroParqueoDto);
		}
		return msg;
	}

}
