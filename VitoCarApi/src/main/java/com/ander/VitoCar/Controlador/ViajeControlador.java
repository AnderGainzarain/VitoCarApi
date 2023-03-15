package com.ander.VitoCar.Controlador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ander.VitoCar.Repositorio.UserRepositorio;
import com.ander.VitoCar.Repositorio.ViajeRepositorio;
import com.ander.VitoCar.model.User;
import com.ander.VitoCar.model.Viaje;

@RestController
@RequestMapping("/api/viajes")
public class ViajeControlador {

	@Autowired
	UserRepositorio userRepositorio;
	@Autowired
	ViajeRepositorio viajeRepositorio;
	
	// Returns all the viajes from the database
	@GetMapping("")
		public ResponseEntity<List<Viaje>> obtenerViajes(){
			return new ResponseEntity<>(viajeRepositorio.findAll(),HttpStatus.OK);
	}
	
	// Returns the viaje with the matching id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarViaje(@PathVariable Integer id){
		viajeRepositorio.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	// Returns the viajes between two dates
	@GetMapping("/fecha")
	public ResponseEntity<List<Viaje>> obtenerFechas(@RequestParam(name="from") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime from,
			@RequestParam(name="to") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime to){
		return ResponseEntity.ok(viajeRepositorio.findBetween(from,to));
	}
	// Returns all the viajes between two dates from a specific user
	@GetMapping("/{id}/fecha")
	public ResponseEntity<List<Viaje>> obtenerFechasId(@RequestParam(name="from") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime from,
			@RequestParam(name="to") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime to,
			@RequestParam(name="id") Integer id){
		return ResponseEntity.ok(viajeRepositorio.findBetweenId(from,to,id));
	}
}