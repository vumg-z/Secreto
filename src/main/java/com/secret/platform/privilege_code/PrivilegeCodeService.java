package com.secret.platform.privilege_code;

import java.util.List;
import java.util.Optional;

public interface PrivilegeCodeService {
    List<PrivilegeCode> getAllPrivilegeCodes();
    Optional<PrivilegeCode> getPrivilegeCodeById(Long id);
    PrivilegeCode createPrivilegeCode(PrivilegeCode privilegeCode);
    PrivilegeCode updatePrivilegeCode(Long id, PrivilegeCode privilegeCode);
    void deletePrivilegeCode(Long id);
}
