package org.agaray.spring.pap2023.repositories;

import org.agaray.spring.pap2023.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
	public Persona findByLoginname(String loginname);
}
