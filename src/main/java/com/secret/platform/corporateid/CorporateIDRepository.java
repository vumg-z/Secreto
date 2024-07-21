package com.secret.platform.corporateid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Deprecated
@Repository
public interface CorporateIDRepository extends JpaRepository<CorporateID, Long> {
    Optional<CorporateID> findByNombre(String nombre);
}
