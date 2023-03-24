package com.ander.VitoCar.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="Users")
public class User {
	// Declarate the class atributes
	@Id
	private Integer DNI;
	private int telefono;
	private String Contraseña;
	private String mail;
	private String nombre;
	private String apellido;
	private String foto;
	private String Coche;
	
	@OneToMany(mappedBy="conductor")
	@JsonIgnore
	private List<Viaje> viajes;

	@ManyToMany()
	@JsonBackReference
	@JoinTable(name="reserva", joinColumns = @JoinColumn(name="userId", referencedColumnName="DNI"),
			inverseJoinColumns= @JoinColumn(name="viajeId", referencedColumnName="idViaje"))
	
	private List<Viaje> viajes2;
	
	
	public Integer getDNI() {
		return DNI;
	}


	public void setDNI(Integer dNI) {
		DNI = dNI;
	}


	public int getTelefono() {
		return telefono;
	}


	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}


	public String getContraseña() {
		return Contraseña;
	}


	public void setContraseña(String contraseña) {
		Contraseña = contraseña;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}


	public String getCoche() {
		return Coche;
	}


	public void setCoche(String coche) {
		Coche = coche;
	}


	public List<Viaje> getViajes() {
		return viajes;
	}


	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}


	public List<Viaje> getViajes2() {
		return viajes2;
	}


	public void setViajes2(List<Viaje> viajes2) {
		this.viajes2 = viajes2;
	}

	public void anularReserva(Integer idViaje) {
		for (int i=0; i<= viajes2.size();i++) {
			if (viajes2.get(i).getIdViaje()==idViaje) {
				viajes2.remove(i);
			}
		}
	}	
	public User() {
		super();
	}
}
