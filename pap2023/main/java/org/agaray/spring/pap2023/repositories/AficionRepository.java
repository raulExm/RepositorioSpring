package org.agaray.spring.pap2023.repositories;

import org.agaray.spring.pap2023.entities.Aficion;
import org.agaray.spring.pap2023.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AficionRepository extends JpaRepository<Aficion, Long>{
}
