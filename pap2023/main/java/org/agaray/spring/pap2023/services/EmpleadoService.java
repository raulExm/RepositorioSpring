package org.agaray.spring.pap2023.services;

import java.util.ArrayList;
import java.util.List;

import org.agaray.spring.pap2023.dto.EmpleadoDTO;
import org.agaray.spring.pap2023.entities.Empleado;
import org.agaray.spring.pap2023.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	public List<EmpleadoDTO> getEmpleados() {
		List<EmpleadoDTO> empleadosDTO = new ArrayList<>();
		for ( Empleado empleado : empleadoRepository.findAll() ) {
			empleadosDTO.add(new EmpleadoDTO(empleado));
		}
		return empleadosDTO;
	}

	public EmpleadoDTO saveEmpleado(EmpleadoDTO empleadoDTO) throws Exception {
		Empleado empleado = new Empleado(empleadoDTO);
		try {
			empleado = empleadoRepository.saveAndFlush(empleado);
		} catch (Exception e) {
			throw new Exception("El empleado " + empleado.getNombre() + " ya existe: "+e.getLocalizedMessage());
		}
		return new EmpleadoDTO(empleado);
	}

	public Empleado getEmpleadoById(Long id) {
		return empleadoRepository.findById(id).get();
	}

	public void updateEmpleado(Long id, String nombre) throws Exception {
		Empleado empleado = empleadoRepository.findById(id).get();
		empleado.setNombre(nombre);
		try {
			empleadoRepository.saveAndFlush(empleado);
		} catch (Exception e) {
			throw new Exception("El/la empleado " + nombre + " ya existe");
		}
	}

	public void deleteEmpleado(Long id) {
		Empleado empleado = empleadoRepository.findById(id).get();
		empleadoRepository.delete(empleado);
	}
}

