package com.ander.VitoCar.Repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ander.VitoCar.model.User;
import com.ander.VitoCar.model.Viaje;

@Repository
public interface ViajeRepositorio extends CrudRepository<Viaje,Integer> {
		// Get all viajes from the database
		List<Viaje> findAll();
		// Create the method to get all the viajes between two fechas
		@Query("SELECT p FROM viajes p WHERE p.fechaSalida >= :from AND p.fechaSalida <= :to")
		public List<Viaje> findBetween(@Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to);
		
		// Create the method to get all the viajes between two fechas from an user
		@Query("SELECT p FROM viajes p WHERE p.fechaSalida >= :from AND p.fechaSalida <= :to and conductor_id=:id")
		public List<Viaje> findBetweenId(@Param(value = "from") LocalDateTime from, @Param(value = "to") LocalDateTime to, @Param(value="id") Integer id);
}