package com.ceiba.parqueadero.infraestructura.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ceiba.parqueadero.infraestructura.entity.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

	/**
	 * Metodo para buscar un vehiculo por Placa
	 * @param placa, placa del vehiculo a buscar
	 * @return Opcional Vehiculo encontrado en la busqueda
	 */
	@Query("SELECT " + "VE " + "FROM Vehiculo VE " + "WHERE VE.placa =?1 ")
	Optional<Vehiculo> buscarVehiculoPorPlaca(String placa);

}
