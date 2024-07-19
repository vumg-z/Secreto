package com.secret.platform.class_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassCodeRepository extends JpaRepository<ClassCode, Long> {
    // Custom query methods can be defined here if needed
}
