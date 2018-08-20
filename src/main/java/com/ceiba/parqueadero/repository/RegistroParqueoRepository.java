package com.ceiba.parqueadero.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceiba.parqueadero.entity.RegistroParqueo;
import com.ceiba.parqueadero.util.TipoVehiculoEnum;

public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {
	
	@Query("SELECT "
			+ "RE "
			+ "FROM RegistroParqueo RE "
			+ "WHERE RE.vehiculo.placa = ?1 ")
	RegistroParqueo buscarRegistroParqueoParafacturar(String placa);
	
	
	@Query("SELECT "
			+ "COUNT(RE) "
			+ "FROM RegistroParqueo RE "
			+ "WHERE RE.vehiculo.tipoVehiculo = ?1 "
			+ "AND RE.estadoRegistroParqueo = 'ACTIVO' ")
	Long contarVehiculos(TipoVehiculoEnum tipoVehiculo);


		

}
