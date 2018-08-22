package com.ceiba.parqueadero.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;


public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {

	@Query("SELECT " + "RE " + "FROM RegistroParqueo RE " + "WHERE RE.vehiculo.placa = ?1 ")
	RegistroParqueo buscarRegistroParqueoParafacturar(String placa);

	Integer countByVehiculoTipoVehiculoAndFechaSalidaIsNull(TipoVehiculoEnum tipoVehiculo);
	
	Optional<RegistroParqueo> findByVehiculoPlacaAndEstadoRegistroParqueo(String placa,EstadoRegistroParqueoEnum estadoParqueo);
	
	@Query("SELECT "
			+ "RE "
			+ "FROM RegistroParqueo RE "
			+ "WHERE RE.estadoRegistroParqueo =?1 ")
	List<RegistroParqueo> listaRegistrosActivos(EstadoRegistroParqueoEnum estadoRegistro);


}
