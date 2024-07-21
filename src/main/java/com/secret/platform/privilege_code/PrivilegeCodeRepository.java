package com.secret.platform.privilege_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeCodeRepository extends JpaRepository<PrivilegeCode, Long> {
}
