package org.agaray.spring.pap2023.services;

import java.util.List;

import org.agaray.spring.pap2023.entities.Aficion;
import org.agaray.spring.pap2023.repositories.AficionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AficionService {
	
	@Autowired
	private AficionRepository aficionRepository;
	
	public List<Aficion> getAficiones() {
		return aficionRepository.findAll();
	}
	
	public void saveAficion(String nombre) throws Exception {
		Aficion aficion = new Aficion(nombre);
		if (nombre.equals("")) {
			throw new Exception("El nombre de afición no puede ser vacío");
		}
		try {
			
			aficionRepository.saveAndFlush(aficion);
		}
		catch (Exception e) {
			throw new Exception("La afición "+nombre+" ya existe");
		}
	}

	public Aficion getAficionById(Long id) {
		return aficionRepository.getById(id);
	}

	public void updateAficion(Long idAficion, String nombre) throws Exception {
		Aficion aficion = aficionRepository.getById(idAficion);
		aficion.setNombre(nombre);
		try {
			aficionRepository.saveAndFlush(aficion);
		}
		catch (Exception e) {
			throw new Exception("La afición "+nombre+" ya existe");
		}
	}

	public void deleteAficion(Long id) {
		Aficion aficion = aficionRepository.getById(id);
		aficionRepository.delete(aficion);
	}

}
