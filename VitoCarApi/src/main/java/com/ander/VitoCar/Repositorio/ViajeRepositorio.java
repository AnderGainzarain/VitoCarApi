package com.ander.VitoCar.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ander.VitoCar.model.Viaje;

import jakarta.transaction.Transactional;

@Repository
public interface ViajeRepositorio extends CrudRepository<Viaje,Integer> {
		// Get all viajes from the database
		List<Viaje> findAll();
		// Create the query to get all the viajes between two fechas
		@Modifying
		@Transactional
		@Query("SELECT p FROM Viaje p WHERE p.fechaSalida >= :from AND p.fechaSalida <= :to")
		List<Viaje> findBetween(@Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to);
		// Create the query to get all viajes with a specific origen, destino and fecha salida
		@Modifying
		@Transactional
		@Query("SELECT p FROM Viaje p WHERE p.fechaSalida>=:fecha AND p.origen=:origen AND p.destino=:destino")
		List<Viaje> findViaje(@Param(value = "origen") String origen, @Param(value = "destino") String destino, @Param(value="fecha") LocalDateTime fecha);		
		void deletePasajero(int dni);
}