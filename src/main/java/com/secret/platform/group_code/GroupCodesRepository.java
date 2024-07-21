package com.secret.platform.group_code;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupCodesRepository extends JpaRepository<GroupCodes, Long> {
    boolean existsByGroupCode(String groupCode);
}
