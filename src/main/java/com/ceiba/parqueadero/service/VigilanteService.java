package com.ceiba.parqueadero.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parqueadero.dto.ParqueaderoExceptionDTO;
import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.entity.Vehiculo;
import com.ceiba.parqueadero.excepciones.ParqueaderoException;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.repository.VehiculoRepository;
import com.ceiba.parqueadero.util.CalcularTarifa;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.validaciones.IValidacionEntrada;
import com.ceiba.parqueadero.validaciones.ValidarCantidad;
import com.ceiba.parqueadero.validaciones.ValidarEstadoParqueo;
import com.ceiba.parqueadero.validaciones.ValidarPlaca;

@Service
public class VigilanteService implements VigilanteServiceInterface {

	@Autowired
	RegistroParqueoRepository registroParqueoRepository;

	@Autowired
	VehiculoRepository vehiculoRepository;

	@Autowired
	CalcularTarifa calculoTarifa;

	private final ModelMapper modelMapper = new ModelMapper();

	/**
	 * Constantes de la clase
	 */

	@Override
	@Transactional
	public RegistroParqueoDTO crearRegistroEntrada(RegistroParqueoDTO registroParqueoDto) {

		if (registroParqueoDto.getVehiculo() == null) {

			return null;
		} else {

			this.realizarvalidacionesDeEntrada(registroParqueoDto);
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

			RegistroParqueo registro = registroParqueoRepository.save(registroParqueo);

			return modelMapper.map(registro, RegistroParqueoDTO.class);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RegistroParqueoDTO> listarRegistroParqueos() {

		Type listaParqueos = new TypeToken<List<RegistroParqueoDTO>>() {
		}.getType();
		return modelMapper.map(registroParqueoRepository.listaRegistrosActivos(EstadoRegistroParqueoEnum.ACTIVO),
				listaParqueos);

	}

	public String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto) {
		ArrayList<IValidacionEntrada> listaValidaciones = new ArrayList<>();
		String msg = null;
		listaValidaciones.add(new ValidarPlaca());
		listaValidaciones.add(new ValidarCantidad());
		listaValidaciones.add(new ValidarEstadoParqueo());
		for (IValidacionEntrada validacionEntrada : listaValidaciones) {
			msg = validacionEntrada.ejecutarValidacionesEntrada(registroParqueoDto, registroParqueoRepository);
			if (msg != null) {
				break;
			}
		}
		return msg;
	}

	/**
	 * 
	 * @param registroParqueoDto
	 */
	@Override
	public RegistroParqueoDTO crearRegistroSalida(String placa) throws ParqueaderoExceptionDTO{

		RegistroParqueo registroParqueo= new RegistroParqueo();
		try {
			Optional<RegistroParqueo> registroParqueoEncontrado = registroParqueoRepository
					.findByVehiculoPlacaAndEstadoRegistroParqueo(placa, EstadoRegistroParqueoEnum.ACTIVO);
			
			

			if (registroParqueoEncontrado.isPresent()) {
				registroParqueo = registroParqueoEncontrado.get();
				double tarifaCobrar = calculoTarifa.calcularTarifaACobrarParqueadero(registroParqueo);
				registroParqueo.setEstadoRegistroParqueo(EstadoRegistroParqueoEnum.FACTURADO);
				registroParqueo.setFechaSalida(new Date());
				registroParqueo.setValorFacturado(tarifaCobrar);
				
			}
			RegistroParqueo registroActualizado = registroParqueoRepository.save(registroParqueo);
			return modelMapper.map(registroActualizado, RegistroParqueoDTO.class);
		
		} catch (MappingException e) {
			throw new ParqueaderoException("0001","No Se ha Actualizado el Registro de manera exitosa");
		}
	}


	@Override
	public RegistroParqueoDTO buscarVehiculoParqueado(String placa) {
		Optional<RegistroParqueo> registroEncontrado = registroParqueoRepository
				.findByVehiculoPlacaAndEstadoRegistroParqueo(placa, EstadoRegistroParqueoEnum.ACTIVO);
		if (registroEncontrado.isPresent()) {
			return modelMapper.map(registroEncontrado, RegistroParqueoDTO.class);
		}
		return null;

	}

}
