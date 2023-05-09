package com.ander.VitoCar.Controlador;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ander.VitoCar.Repositorio.UserRepositorio;
import com.ander.VitoCar.Repositorio.ViajeRepositorio;
import com.ander.VitoCar.model.User;
import com.ander.VitoCar.model.Viaje;


@RestController
@RequestMapping("/api/usuarios")
public class UserControlador {

		@Autowired
		UserRepositorio userRepositorio;
		@Autowired
		ViajeRepositorio viajeRepositorio;
		// Get all users
		@GetMapping("")
		public ResponseEntity<List<User>> obtenerUsuarios(){
				return new ResponseEntity<>(userRepositorio.findAll(),HttpStatus.OK);
		}
		// Returns all the viajes whete the user is a pasajero
		@GetMapping("/{idViaje}/conductor")
		public ResponseEntity<List<String>>getDriverData(@PathVariable Integer idViaje){
			Optional<Viaje> viajeOpcional = viajeRepositorio.findById(idViaje);
			if(!viajeOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			List<String> data = new ArrayList<>();
			data.add(String.valueOf(viajeOpcional.get().getConductor().getDNI()));
			data.add(viajeOpcional.get().getConductor().getMail());
			return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
		}
		// Get a specific user via their dni
		@GetMapping("/{dni}")
		public ResponseEntity<Object> obtenerUser(@PathVariable int dni){
			Optional<User> usuarioOpcional = userRepositorio.findById(dni);
			if(!usuarioOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(userRepositorio.findById(dni),HttpStatus.OK);
		}
		// Get a specific user via their mail
		@GetMapping("/mail/{mail}")
		public ResponseEntity<Object> obtenerUserMail(@PathVariable String mail){
			Optional<User> usuarioOpcional = userRepositorio.findByMail(mail);
			if(!usuarioOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(userRepositorio.findByMail(mail),HttpStatus.OK);
		}
		// Get the viajes an user is offering
		@GetMapping("/{dni}/viajes")
		public ResponseEntity<List<Viaje>> viajesUsuarioPorDni(@PathVariable int dni){
			Optional<User> usuarioOpcional = userRepositorio.findById(dni);
			if(!usuarioOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(usuarioOpcional.get().getViajes(),HttpStatus.OK);
		}
		// return all the pasajeros from a viaje
		@GetMapping("/{idViaje}/pasajeros")
		public ResponseEntity<List<User>>obtenerViajesPasajero(@PathVariable Integer idViaje){
			Optional<Viaje> viajeOpcional = viajeRepositorio.findById(idViaje);
			if(!viajeOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(viajeOpcional.get().getUsuarios2(),HttpStatus.OK);
		}
}
