package com.secret.platform.inclusiones;

import com.secret.platform.inclusiones.Inclusiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InclusionesRepository extends JpaRepository<Inclusiones, Long> {
}
