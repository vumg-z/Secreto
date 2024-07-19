package com.secret.platform.status_code;

import com.secret.platform.status_code.StatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusCodeRepository extends JpaRepository<StatusCode, Long> {
    Optional<StatusCode> findByCode(String code);
    boolean existsByCode(String code);
}
