package org.agaray.spring.pap2023.repositories;

import java.util.List;

import org.agaray.spring.pap2023.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long>{
	public List<Pais> findByOrderByNombreDesc();
}
