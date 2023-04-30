package com.ander.VitoCar.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="Viajes")
public class Viaje {

	// declare the class atributes
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idViaje;
	private int precio;
	private String origen;
	private String destino;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	
	private LocalDateTime fechaSalida;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="conductorId", referencedColumnName="DNI")
	@JsonProperty(access=Access.WRITE_ONLY)
	//@JsonIgnore
	private User conductor;
	
	@ManyToMany()
	@JsonBackReference
	@JoinTable(name="reserva", joinColumns= @JoinColumn(name="viajeId", referencedColumnName="idViaje"),
	inverseJoinColumns= @JoinColumn(name="userId", referencedColumnName="DNI"))
	
	private List<User> usuarios2;

	public void realizarReserva(User pasajero) {
		usuarios2.add(pasajero);
	}
	
	public void deletePasajero(int dni) {
		usuarios2.removeIf(usuario -> usuario.getDNI()==dni);
	}

	public int getIdViaje() {
		return idViaje;
	}




	public void setIdViaje(int idViaje) {
		this.idViaje = idViaje;
	}




	public int getPrecio() {
		return precio;
	}




	public void setPrecio(int precio) {
		this.precio = precio;
	}




	public String getOrigen() {
		return origen;
	}




	public void setOrigen(String origen) {
		this.origen = origen;
	}




	public String getDestino() {
		return destino;
	}




	public void setDestino(String destino) {
		this.destino = destino;
	}




	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}




	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}




	public User getConductor() {
		return conductor;
	}




	public void setConductor(User conductor) {
		this.conductor = conductor;
	}




	public List<User> getUsuarios2() {
		return usuarios2;
	}




	public void setUsuarios2(List<User> usuarios2) {
		this.usuarios2 = usuarios2;
	}


	public void anularReserva(Integer dni) {
		for (int i=0; i < usuarios2.size();i++) {
			if (usuarios2.get(i).getDNI()==dni) {
				usuarios2.remove(i);
			}
		}
	}

	public Viaje() {
		super();
	}
}
