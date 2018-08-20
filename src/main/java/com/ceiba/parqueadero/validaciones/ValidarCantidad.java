package com.ceiba.parqueadero.validaciones;

import org.springframework.beans.factory.annotation.Autowired;

import com.ceiba.parqueadero.dto.RegistroParqueoDTO;
import com.ceiba.parqueadero.repository.RegistroParqueoRepository;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;

public class ValidarCantidad implements IValidacionEntrada {
	
	@Autowired
	RegistroParqueoRepository registroParqueoRepository;
	
	public static final int CANTIDAD_CARROS= 20;
	public static final int CANTIDAD_MOTOS= 10;

	@Override
	public String ejecutarValidaciones(RegistroParqueoDTO registroParqueoDto) {
		
		Long cantidad = registroParqueoRepository.contarVehiculos(registroParqueoDto.getVehiculo().getTipoVehiculo());
		
		if(registroParqueoDto.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.CARRO)) {
			if(cantidad>=CANTIDAD_CARROS) {
				return "El Parqueadero a alcanzado el Maximo numero de Carros  permitido";
			}
			 
		}else  if(registroParqueoDto.getVehiculo().getTipoVehiculo().equals(TipoVehiculoEnum.MOTO)) {
			
			if(cantidad>=CANTIDAD_MOTOS) {
				return "El Parqueadero a alcanzado el Maximo numero de Motos permitidas";	
			}
		}
		
		return null;
	}
	
	

}
