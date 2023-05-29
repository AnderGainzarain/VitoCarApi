package com.ander.VitoCar.Controlador;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	// Deletes the viaje with the matching id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarViaje(@PathVariable Integer id){
		viajeRepositorio.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	// Returns the viajes with a specific origen, destino and fechaSalida
	@GetMapping("/viajeConcreto")
	public ResponseEntity<List<Viaje>> obtenerViajeConcreto(
			@RequestParam(name="origen") String origen,
			@RequestParam(name="destino") String destino,
			@RequestParam(name="fecha") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime fecha,
			@RequestParam(name="DNI") Integer DNI){
		List<Viaje> viajes = viajeRepositorio.findViaje(origen,destino,fecha);
		// Delete the viajes with 3 reservas
		viajes = viajes.stream().filter(v-> v.getNumPasajeros()!=3).toList();
		// DNI del usuario logueado, 0 si no esta logueado
		if(DNI!=0) {
			// Delete the viajes the user has posted
			viajes = viajes.stream().filter(v-> !v.getConductor().getDNI().equals(DNI)).toList();
			// Delete the viajes the user has reserved
			Optional<User> user = userRepositorio.findById(DNI);
			List<Viaje> reservas = user.get().getViajes2();
			for(Viaje reserva:reservas) {
				viajes = viajes.stream().filter(v->v.getIdViaje()!=reserva.getIdViaje()).toList();
			}
		}
		return ResponseEntity.ok(viajes);
	}
	// Returns all the viajes whete the user is a pasajero
	@GetMapping("/{id}/pasajero")
	public ResponseEntity<List<Viaje>>obtenerViajesPasajero(@PathVariable Integer id){
		Optional<User> usuarioOpcional = userRepositorio.findById(id);
		if(!usuarioOpcional.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(usuarioOpcional.get().getViajes2(),HttpStatus.OK);
	}
	// Publish a viaje with a specific driver
	@PostMapping("/publicar/{dni}")
	public ResponseEntity<Viaje> publicarViaje(@PathVariable Integer dni, @RequestBody Viaje viaje){
		Optional<User> usuarioOpcional = userRepositorio.findById(dni);
		
		if (!usuarioOpcional.isPresent()) {
			return ResponseEntity.unprocessableEntity().build();
		}
		User usuario = usuarioOpcional.get();
		viaje.setConductor(usuario);
		Viaje viajeGuardado = viajeRepositorio.save(viaje);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viaje.getIdViaje()).toUri();
		return ResponseEntity.created(location).body(viajeGuardado);
	}
	// add a reserva to a viaje
	@PostMapping("/reservar/{dni}/{idViaje}")
    public ResponseEntity<Object> realizarReserva (@PathVariable int dni, @PathVariable int idViaje) {               
           
            Optional<User> userOptional2 = userRepositorio.findById(dni);
            if(!userOptional2.isPresent()) {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            User userEntity = userOptional2.get();
           
            Optional<Viaje> viajeOptional2 = viajeRepositorio.findById(idViaje);
            if(!viajeOptional2.isPresent()) {
                    return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }
            Viaje viajeEntity = viajeOptional2.get();
           
            viajeEntity.getUsuarios2().add(userEntity);
           
            return ResponseEntity.ok(viajeRepositorio.save(viajeEntity));
    }
	// anular a reserva
	@DeleteMapping("/anularReserva/{dni}/{idViaje}")
	public ResponseEntity<Void> anularReserva(@PathVariable int dni, @PathVariable int idViaje){
		Optional<User> usuarioOpcional = userRepositorio.findById(dni);
		Optional<Viaje> viajeOpcional = viajeRepositorio.findById(idViaje);
		if (!usuarioOpcional.isPresent() || !(viajeOpcional.isPresent())) {
			return ResponseEntity.unprocessableEntity().build();
		}
		User usuario = usuarioOpcional.get();
		usuario.anularReserva(idViaje);
		Viaje viaje = viajeOpcional.get();
		viaje.anularReserva(dni);
		userRepositorio.save(usuario);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}