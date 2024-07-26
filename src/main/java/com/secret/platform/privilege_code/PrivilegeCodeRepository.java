package com.secret.platform.privilege_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeCodeRepository extends JpaRepository<PrivilegeCode, Long> {
    Optional<PrivilegeCode> findByCode(String code);

}
