package com.ceiba.parqueadero.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	private int diasParqueo;
	private int horasParqueo;
	private int minutosParqueo;

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
		return modelMapper.map(registroParqueoRepository.listaRegistrosActivos(EstadoRegistroParqueoEnum.ACTIVO),
				listaParqueos);

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
			registroParqueo.setEstadoRegistroParqueo(EstadoRegistroParqueoEnum.FACTURADO);
			registroParqueo.setFechaSalida(new Date());
			registroParqueo.setValorFacturado(tarifaCobrar);

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

		double valorCobrado = 0.0;
		Date fechaSalida = new Date();
		this.calcularTiempoParqueo(registroParqueo.getFechaEntrada(), fechaSalida);

		//Cobro por Horas
		if (horasParqueo <= 9) {
			if (minutosParqueo > 0) {
				horasParqueo = horasParqueo + 1;
			}
			valorCobrado = calcularValorTarifaHora(horasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
			if (registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)
					&& registroParqueo.getVehiculo().getCilindraje() > 500) {
				valorCobrado = valorCobrado + 2000;
			}
		}
		//Cobro por Dias
		
		if (horasParqueo >= 9 && horasParqueo <= 24 ) {
			diasParqueo = diasParqueo + 1;
			valorCobrado=calcularValorTarifaDia(diasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
			if (registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)
					&& registroParqueo.getVehiculo().getCilindraje() > 500) {
				valorCobrado = valorCobrado + 2000;
			}
		}
		
		//Cobro por Dia y Hora
		if(diasParqueo>0 && horasParqueo <= 9 ) {
			if (minutosParqueo > 0) {
				horasParqueo = horasParqueo + 1;
			}
			valorCobrado=calcularValorTarifaHora(horasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
			valorCobrado+=calcularValorTarifaDia(diasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
			if (registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)
					&& registroParqueo.getVehiculo().getCilindraje() > 500) {
				valorCobrado = valorCobrado + 2000;
			}
		}
//		//Corbro por Dias + Dias de las Horas
//		if(diasParqueo>0 && horasParqueo >= 9 && horasParqueo <= 24 ) {
//			diasParqueo=diasParqueo+1;
//			valorCobrado=calcularValorTarifaDia(diasParqueo, registroParqueo.getVehiculo().getTipoVehiculo());
//			if (registroParqueo.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)
//					&& registroParqueo.getVehiculo().getCilindraje() > 500) {
//				valorCobrado = valorCobrado + 2000;
//			}
		//}
		return valorCobrado;
			
	}

	/**
	 * metodo para calcular el valor de la Tarifa por Hora
	 * 
	 * @param tiempoParqueo
	 * @param tipoVehiculo
	 * @param cilindraje
	 * @return
	 */
	public Double calcularValorTarifaHora(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		double resul = tiempoParqueo * tarifa.tarifaValorHora();
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
	public Double calcularValorTarifaDia(int tiempoParqueo, TipoVehiculoEnum tipoVehiculo) {
		IParametrizacion tarifa = fabricaParametrizacion.conexionFabrica(tipoVehiculo);
		double resul = tiempoParqueo * tarifa.tarifaValorDia();
		return resul;
	}

	public void calcularTiempoParqueo(Date fechaEntrada, Date fechaSalida) {

		int diferencia = (int) ((fechaSalida.getTime() - fechaEntrada.getTime()) / 1000);
		System.out.println("--------> diferencia " + diferencia);

		if (diferencia >= 86400) {
			this.diasParqueo = (int) Math.floor(diferencia / 86400);
			diferencia = diferencia - (diasParqueo * 86400);
		}
		if (diferencia >= 3600) {
			this.horasParqueo = (int) Math.floor(diferencia / 3600);
			diferencia = diferencia - (horasParqueo * 3600);
		}
		if (diferencia >= 60) {
			this.minutosParqueo = (int) Math.floor(diferencia / 60);
			diferencia = diferencia - (minutosParqueo * 60);
		}
		System.out.println(" diferencia " + diferencia + "," + " dias Parqueo " + diasParqueo + "," + " horas Parqueo "
				+ horasParqueo + "," + " minutos Parqueo" + minutosParqueo);
	}

	public int getDiasParqueo() {
		return diasParqueo;
	}

	public void setDiasParqueo(int diasParqueo) {
		this.diasParqueo = diasParqueo;
	}

	public int getHorasParqueo() {
		return horasParqueo;
	}

	public void setHorasParqueo(int horasParqueo) {
		this.horasParqueo = horasParqueo;
	}

	public int getMinutosParqueo() {
		return minutosParqueo;
	}

	public void setMinutosParqueo(int minutosParqueo) {
		this.minutosParqueo = minutosParqueo;
	}

}