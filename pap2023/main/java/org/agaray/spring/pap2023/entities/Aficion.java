package org.agaray.spring.pap2023.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Aficion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nombre;
	
	@ManyToMany(mappedBy = "gustos")
	private Collection<Persona> aficionados;

	@ManyToMany(mappedBy = "odios")
	private Collection<Persona> haters;

	// ==============================================


	public Aficion() {
		this.aficionados = new ArrayList<Persona>();
		this.haters= new ArrayList<Persona>();
	}
	
	public Aficion(String nombre) {
		super();
		this.nombre = nombre;
		this.aficionados = new ArrayList<Persona>();
		this.haters= new ArrayList<Persona>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Persona> getAficionados() {
		return aficionados;
	}

	public void setAficionados(Collection<Persona> aficionados) {
		this.aficionados = aficionados;
	}

	public Collection<Persona> getHaters() {
		return haters;
	}

	public void setHaters(Collection<Persona> haters) {
		this.haters = haters;
	}
	
	
	
	
}
