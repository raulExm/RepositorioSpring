package org.agaray.spring.pap2023.services;

import java.util.List;
import org.agaray.spring.pap2023.entities.Pais;
import org.agaray.spring.pap2023.entities.Persona;
import org.agaray.spring.pap2023.repositories.PaisRepository;
import org.agaray.spring.pap2023.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {
	
	@Autowired
	private PaisRepository paisRepository;
	
	@Autowired
	private PersonaRepository personaRepository;
	
	
	public List<Pais> getPaises() {
		return paisRepository.findAll();
	}
	
	public void savePais(String nombre) throws Exception {
		Pais pais = Pais
				.builder()
				.nombre(nombre)
				.build();
		try {
			paisRepository.saveAndFlush(pais);
		}
		catch (Exception e) {
			throw new Exception("El país "+nombre+" ya existe");
		}	}

	public Pais getPaisById(Long id) {
		return paisRepository.getById(id);
	}

	public void updatePais(Long idPais, String nombre) throws Exception {
		Pais pais = paisRepository.getById(idPais);
		pais.setNombre(nombre);
		try {
			paisRepository.saveAndFlush(pais);
		}
		catch (Exception e) {
			throw new Exception("El país "+nombre+" ya existe");
		}
	}

	public void deletePais(Long id) throws Exception {
		Pais pais = paisRepository.getById(id);
		//if (!pais.getResidentes().isEmpty() || !pais.getNacidos().isEmpty()) {
		if (3==4) {
			throw new Exception(
					pais.getNombre()+
					" tiene algún residente/nacido aún. Borra todos sus elementos antes de intentar borrar el país"
					);
		}
		for (Persona nacido : pais.getNacidos()) {
			nacido.setNace(null);
			personaRepository.saveAndFlush(nacido);
		}
		for (Persona residente : pais.getResidentes()) {
			residente.setVive(null);
			personaRepository.saveAndFlush(residente);
		}
		paisRepository.delete(pais);

	}
}
