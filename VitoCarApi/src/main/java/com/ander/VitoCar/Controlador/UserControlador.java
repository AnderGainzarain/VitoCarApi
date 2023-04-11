package com.ander.VitoCar.Controlador;

import java.util.List;
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
		
		@GetMapping("")
		public ResponseEntity<List<User>> obtenerUsuarios(){
				return new ResponseEntity<>(userRepositorio.findAll(),HttpStatus.OK);
		}
		
		@GetMapping("/{dni}")
		public ResponseEntity<Object> obtenerUser(@PathVariable int dni){
			Optional<User> usuarioOpcional = userRepositorio.findById(dni);
			if(!usuarioOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(userRepositorio.findById(dni),HttpStatus.OK);
		}
		
		@GetMapping("/{dni}/viajes")
		public ResponseEntity<List<Viaje>> viajesUsuarioPorDni(@PathVariable int dni){
			Optional<User> usuarioOpcional = userRepositorio.findById(dni);
			if(!usuarioOpcional.isPresent()) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(usuarioOpcional.get().getViajes(),HttpStatus.OK);
		}
}
