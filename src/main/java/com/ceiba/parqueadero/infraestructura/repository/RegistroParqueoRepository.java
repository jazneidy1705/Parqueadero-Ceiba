package com.ceiba.parqueadero.infraestructura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceiba.parqueadero.dominio.util.EstadoRegistroParqueoEnum;
import com.ceiba.parqueadero.dominio.util.TipoVehiculoEnum;
import com.ceiba.parqueadero.infraestructura.entity.RegistroParqueo;


public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {

	@Query("SELECT " + "RE " + "FROM RegistroParqueo RE " + "WHERE RE.vehiculo.placa = ?1 ")
	RegistroParqueo buscarRegistroParqueoParafacturar(String placa);

	Integer countByVehiculoTipoVehiculoAndFechaSalidaIsNull(TipoVehiculoEnum tipoVehiculo);
	
	RegistroParqueo findByVehiculoPlacaAndEstadoRegistroParqueo(String placa,EstadoRegistroParqueoEnum estadoParqueo);
	
	@Query("SELECT "
			+ "RE "
			+ "FROM RegistroParqueo RE "
			+ "WHERE RE.estadoRegistroParqueo =?1 ")
	List<RegistroParqueo> listaRegistrosActivos(EstadoRegistroParqueoEnum estadoRegistro);


}
