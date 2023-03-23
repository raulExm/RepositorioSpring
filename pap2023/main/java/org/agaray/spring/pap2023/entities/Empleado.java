package org.agaray.spring.pap2023.entities;

import org.agaray.spring.pap2023.dto.EmpleadoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Empleado {

	public Empleado(EmpleadoDTO empleadoDTO) {
		this.dni = empleadoDTO.getDni();
		this.nombre = empleadoDTO.getName();
		this.apellido = empleadoDTO.getSurname();
		this.color = empleadoDTO.getColor();
		this.chorrada = empleadoDTO.getNonsense();
		this.tlf = "+" + empleadoDTO.getPrefix() + " "+ empleadoDTO.getTel();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String dni;

	private String nombre;

	private String apellido;
	
	private String tlf;

	private String color;

	private String chorrada;
	
	

}
