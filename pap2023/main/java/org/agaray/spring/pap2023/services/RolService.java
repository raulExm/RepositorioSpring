package org.agaray.spring.pap2023.services;

import org.agaray.spring.pap2023.entities.Rol;
import org.agaray.spring.pap2023.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
	
	@Autowired
	private RolRepository rolRepository;
	
	//GUARDA UN ROL
	public void saveRol(String nombre) throws Exception {
		Rol rol = new Rol(nombre);
		try {
			rolRepository.save(rol);
		}
		catch (Exception e) {
			throw new Exception("El rol "+nombre+" ya existe");
		}
	}
	
	
	//COMPRUEBA SI YA EXISTE EL ROL
	public boolean existeRol(String nombre) {
		return rolRepository.findByNombre(nombre) != null;
	}
}
