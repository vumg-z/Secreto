package com.secret.platform.type_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidTypeCodeRepository extends JpaRepository<ValidTypeCode, Long> {
    Optional<ValidTypeCode> findByTypeCode(String defltRaType);

}
