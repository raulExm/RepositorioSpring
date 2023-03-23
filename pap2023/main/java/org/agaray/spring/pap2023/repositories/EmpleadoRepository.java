package org.agaray.spring.pap2023.repositories;

import org.agaray.spring.pap2023.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
