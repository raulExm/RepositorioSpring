package org.agaray.spring.pap2023.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.agaray.spring.pap2023.entities.Aficion;
import org.agaray.spring.pap2023.entities.Pais;
import org.agaray.spring.pap2023.entities.Persona;
import org.agaray.spring.pap2023.entities.Rol;
import org.agaray.spring.pap2023.repositories.AficionRepository;
import org.agaray.spring.pap2023.repositories.PaisRepository;
import org.agaray.spring.pap2023.repositories.PersonaRepository;
import org.agaray.spring.pap2023.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

	@Autowired
	private RolRepository rolRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private AficionRepository aficionRepository;

	public List<Persona> getPersonas() {
		return personaRepository.findAll();
	}
	
	public Persona saveAdmin(
			String nombre, 
			String loginname, 
			String password, 
			LocalDate fnac,
			String extension,
			Long idPaisNace, 
			Long idPaisResidencia,
			Long[] idGustos, 
			Long[] idOdios 
			) throws Exception {
		
		// Gestión de atributos "regulares"
		Persona persona = new Persona(nombre,loginname,(new BCryptPasswordEncoder()).encode(password), fnac , extension);

		// Gestión de roles
		Rol rol = rolRepository.findByNombre("admin");
		persona.setRol(rol);
		
		try {
			personaRepository.saveAndFlush(persona);
			return persona;
		} catch (Exception e) {
			throw new Exception("El/la persona " + nombre + " ya existe");
		}
	}


	public Persona savePersona(
			String nombre, 
			String loginname, 
			String password, 
			LocalDate fnac,
			String extension,
			Long idPaisNace, 
			Long idPaisResidencia,
			Long[] idGustos, 
			Long[] idOdios 
			) throws Exception {
		
		// Gestión de atributos "regulares"
		Persona persona = new Persona(nombre,loginname,(new BCryptPasswordEncoder()).encode(password), fnac , extension);

		// Gestión de roles
		Rol rol = rolRepository.findByNombre("user");
		persona.setRol(rol);
		
		// Gestión de países
		Pais paisNacimiento = paisRepository.getById(idPaisNace);
		Pais paisResidencia = paisRepository.getById(idPaisResidencia);
		
		persona.setNace(paisNacimiento);
		persona.setVive(paisResidencia);

		paisNacimiento.getNacidos().add(persona);
		paisResidencia.getResidentes().add(persona);
		
		// Gestión de aficiones
		for ( Long idGusto : idGustos) {
			Aficion aficionGustada = aficionRepository.getById(idGusto);
			persona.getGustos().add(aficionGustada);
			aficionGustada.getAficionados().add(persona);
		}
		
		for ( Long idOdio : idOdios) {
			Aficion aficionOdiada = aficionRepository.getById(idOdio);
			persona.getOdios().add(aficionOdiada);
			aficionOdiada.getHaters().add(persona);
		}
		

		
		try {
			personaRepository.saveAndFlush(persona);
			return persona;
		} catch (Exception e) {
			throw new Exception("El/la persona " + nombre + " ya existe");
		}
	}

	
	
	public Persona getPersonaById(Long id) {
		return personaRepository.getById(id);
	}

	public void updatePersona(Long idPersona, String nombre, Long idPaisNace, Long idPaisVive, Long[] idGustos, Long[] idOdios) throws Exception {
		Persona persona = personaRepository.getById(idPersona);
		
		// ATRIBUTOS REGULARES
		persona.setNombre(nombre);
		
		//PAIS NACIMIENTO
		if ( persona.getNace()== null || idPaisNace != persona.getNace().getId() )  {
			Pais nuevoPaisDeNacimiento = paisRepository.getById(idPaisNace);
			persona.setNace(nuevoPaisDeNacimiento);
		}
		
		// PAIS RESIDENCIA
		if ( persona.getVive() == null || idPaisVive != persona.getVive().getId() )  {
			Pais nuevoPaisDeResidencia = paisRepository.getById(idPaisVive);
			persona.setVive(nuevoPaisDeResidencia);
		}
		
		// GUSTOS
		Collection<Aficion> nuevosGustos = new ArrayList<Aficion>();
		for ( Long idGusto : idGustos) {
			nuevosGustos.add(aficionRepository.getById(idGusto));
		}
		persona.setGustos(nuevosGustos);
		
		//ODIOS
		Collection<Aficion> nuevosOdios = new ArrayList<Aficion>();
		for ( Long idOdio: idOdios) {
			nuevosOdios.add(aficionRepository.getById(idOdio));
		}
		persona.setOdios(nuevosOdios);
		
		// GUARDADO
		try {
			personaRepository.saveAndFlush(persona);
		} catch (Exception e) {
			throw new Exception("El/la persona " + nombre + " ya existe");
		}
	}

	public void deletePersona(Long id) {
		Persona persona = personaRepository.getById(id);
		personaRepository.delete(persona);
	}
	
	//METODO PARA AUTENTICAR EN EL LOGIN UN USUARIO
	public Persona autenticarUsuario(String loginname, String password) throws Exception {
		Persona usuario = null;
		
		// Verificar que el usuario existe
		try {
			usuario = personaRepository.findByLoginname(loginname);
			if (usuario == null ) {throw new Exception();}
		}
		catch (Exception e) {
			throw new Exception("El usuario "+loginname+" no existe");
		}
		
		// Verificar que la contraseña coincide
		if (!  (new BCryptPasswordEncoder()).matches(password,usuario.getPassword()) ) {
			throw new Exception("La contraseña es incorrecta");
		}
		
		return usuario;
	}
	
	//COMPRUEBA SI LA PERSONA EXISTE
	public boolean existePersona(String loginname) {
		return personaRepository.findByLoginname(loginname) != null;
	}
}

