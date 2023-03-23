package org.agaray.spring.pap2023.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;
	
	@Column(unique = true)
	private String loginname;
	
	private String password;

	private LocalDate fnac;

	private String extension;
	// ....................................
	
	@ManyToOne
	private Rol rol;
	
	@ManyToOne
	private Pais nace;
	
	@ManyToOne
	private Pais vive;
	
	@ManyToMany
	private Collection<Aficion> gustos;

	@ManyToMany
	private Collection<Aficion> odios;
	
	
	// ==============================================
	
	public Persona() {
		this.gustos = new ArrayList<Aficion>();
		this.odios = new ArrayList<Aficion>();
	}

	public Persona(String nombre, String loginname, String password, LocalDate fnac, String extension) {
		super();
		this.gustos= new ArrayList<Aficion>();
		this.odios= new ArrayList<Aficion>();
		this.nombre = nombre;
		this.loginname = loginname;
		this.password = password;
		this.fnac = fnac;
		this.extension = extension;
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

	public Pais getNace() {
		return nace;
	}

	public void setNace(Pais nace) {
		this.nace = nace;
	}

	public Pais getVive() {
		return vive;
	}

	public void setVive(Pais vive) {
		this.vive = vive;
	}

	public Collection<Aficion> getGustos() {
		return gustos;
	}

	public void setGustos(Collection<Aficion> gustos) {
		this.gustos = gustos;
	}

	public Collection<Aficion> getOdios() {
		return odios;
	}

	public void setOdios(Collection<Aficion> odios) {
		this.odios = odios;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFnac() {
		return fnac;
	}

	public void setFnac(LocalDate fnac) {
		this.fnac = fnac;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	//ROLES EN PERSONA
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
