package com.secret.platform.terminos_alquiler;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminosAlquilerRepository extends JpaRepository<TerminosAlquiler, Long> {
}
