package com.ceiba.parqueadero.service;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
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
import com.ceiba.parqueadero.parametrizacion.IParametrizacion;
import com.ceiba.parqueadero.parametrizacion.ParametrizacionFabrica;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.repository.VehiculoRepository;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;
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
	ParametrizacionFabrica fabricaParametrizacion;

	private final ModelMapper modelMapper = new ModelMapper();

	public final static int INICIO_DIA = 9;
	public final static int FIN_DIA = 24;

	// private final ParametrizacionFabrica parametrizacionFabrica = new
	// ParametrizacionFabrica();

	/**
	 * Constantes de la clase
	 */

	@Override
	@Transactional
	public void crearRegistroEntrada(RegistroParqueoDTO registroParqueoDto) {

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
	   
		Type listaParqueos = new TypeToken<List<RegistroParqueoDTO>>() {
		}.getType();
		return modelMapper.map(registroParqueoRepository.listaRegistrosActivos(EstadoRegistroParqueoEnum.ACTIVO),listaParqueos);

	}

	@Override
	public String realizarvalidacionesDeEntrada(RegistroParqueoDTO registroParqueoDto) {
		ArrayList<IValidacionEntrada> listaValidaciones = new ArrayList<>();
		String msg = null;
		listaValidaciones.add(new ValidarPlaca());
		listaValidaciones.add(new ValidarCantidad());
		listaValidaciones.add(new ValidarEstadoParqueo());
		for (IValidacionEntrada validacionEntrada : listaValidaciones) {
			msg = validacionEntrada.ejecutarValidacionesEntrada(registroParqueoDto, registroParqueoRepository);
		}
		return msg;
	}

	/**
	 * 
	 * @param registroParqueoDto
	 */
	@Override
	public void crearRegistroSalida(String placa) {

		Optional<RegistroParqueo> registroParqueoEncontrado = registroParqueoRepository
				.findByVehiculoPlacaAndEstadoRegistroParqueo(placa, EstadoRegistroParqueoEnum.ACTIVO);

		if (registroParqueoEncontrado.isPresent()) {

			RegistroParqueo registroParqueo = registroParqueoEncontrado.get();
			double tarifaCobrar = cobrarTiempoParqueadero(registroParqueo);
			//
			// Date fechaEntrada = registroParqueo.getFechaEntrada();
			Date fechaSalida = new Date();
			// Long tiempoParqueo = TimeUnit.HOURS.convert(Math.abs(fechaEntrada.getTime() -
			// fechaSalida.getTime()),
			// TimeUnit.MILLISECONDS);

			registroParqueo.setEstadoRegistroParqueo(EstadoRegistroParqueoEnum.FACTURADO);
			registroParqueo.setFechaSalida(fechaSalida);
			registroParqueo.setValorFacturado(tarifaCobrar);
			// registroParqueo.setTiempo(tiempoParqueo.intValue());

			registroParqueoRepository.save(registroParqueo);

		}
	}

	/**
	 * 
	 * @param placa
	 * @return
	 */
	@Override
	public double cobrarTiempoParqueadero(RegistroParqueo registroParqueo) {

		Date fechaEntrada = registroParqueo.getFechaEntrada();
		Instant instant = Instant.ofEpochMilli(fechaEntrada.getTime());
		LocalDateTime fechaEntradaCasteada = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		Date fechaSalida = new Date();
		Instant instantFechaSalida = Instant.ofEpochMilli(fechaSalida.getTime());
		LocalDateTime fechaSalidaCasteada = LocalDateTime.ofInstant(instantFechaSalida, ZoneId.systemDefault());

		java.time.Duration duracion = java.time.Duration.between(fechaEntradaCasteada, fechaSalidaCasteada);

		Period periodo = Period.between(fechaEntradaCasteada.toLocalDate(), fechaSalidaCasteada.toLocalDate());
		long seconds = duracion.getSeconds();

		// Diferencia de Horas
		int años = periodo.getYears();
		int meses = periodo.getMonths();
		//int dias = periodo.getDays();
		//System.out.println("---> Dias " +dias);

		int dias=0;
		// Diferencia de tiempo
		int horasParqueo = (int) (seconds / 3600);
		int minutes = (int) ((seconds % 3600) / 60);
		int secs = (int) (seconds % 60);

		System.out.println("--------> tiempo : " + horasParqueo);

		// Se cobran por horas
		if (horasParqueo < INICIO_DIA) {
			if (minutes > 5) {
				horasParqueo = horasParqueo + 1;
			}
			return calcularValorTarifaHora(horasParqueo, registroParqueo.getVehiculo().getTipoVehiculo(),
					registroParqueo.getVehiculo().getCilindraje());

		} else if (horasParqueo > INICIO_DIA && horasParqueo < FIN_DIA) {
			// Se cobran por Dias
			dias++;
			System.out.println("-----> "+dias);
			double valorPorDia = calcularValorTarifaDia(dias, registroParqueo.getVehiculo().getTipoVehiculo(),
					registroParqueo.getVehiculo().getCilindraje());
			double valorHora = calcularValorTarifaHora(horasParqueo-INICIO_DIA, registroParqueo.getVehiculo().getTipoVehiculo(),
					registroParqueo.getVehiculo().getCilindraje());

			double resultado = valorPorDia + valorHora;
			System.out.println("---->Resultado por DIA+HORA "+ resultado);
			return resultado;

		}
		return 0;
	}

	/**
	 * metodo para calcular el valor de la Tarifa por Hora
	 * 
	 * @param tiempoParqueo
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @return
	 */
	public Double calcularValorTarifaHora(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo, int cilindraje) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		double resul = tiempoParqueo * tarifa.tarifaValorHora(cilindraje);
		System.out.println("-----> TARIFA HORA" + resul);
		return resul;
	}

	/**
	 * Metodo para calcular el valor de la Tarifa por dia
	 * 
	 * @param tiempoParqueo
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @return
	 */
	public Double calcularValorTarifaDia(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo, int cilindraje) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		double resul = tiempoParqueo * tarifa.tarifaValorDia(cilindraje);
		System.out.println("-----------> TARIFA DIA" + resul);
		return resul;
	}
}
