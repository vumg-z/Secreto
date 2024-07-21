package com.secret.platform.privilege_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivilegeCodeServiceImpl implements PrivilegeCodeService {

    @Autowired
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Override
    public List<PrivilegeCode> getAllPrivilegeCodes() {
        return privilegeCodeRepository.findAll();
    }

    @Override
    public Optional<PrivilegeCode> getPrivilegeCodeById(Long id) {
        return privilegeCodeRepository.findById(id);
    }

    @Override
    public PrivilegeCode createPrivilegeCode(PrivilegeCode privilegeCode) {
        return privilegeCodeRepository.save(privilegeCode);
    }

    @Override
    public PrivilegeCode updatePrivilegeCode(Long id, PrivilegeCode privilegeCode) {
        return privilegeCodeRepository.findById(id)
                .map(existingPrivilegeCode -> {
                    privilegeCode.setId(id);
                    return privilegeCodeRepository.save(privilegeCode);
                })
                .orElseThrow(() -> new ResourceNotFoundException("PrivilegeCode not found with id " + id));
    }

    @Override
    public void deletePrivilegeCode(Long id) {
        if (privilegeCodeRepository.existsById(id)) {
            privilegeCodeRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("PrivilegeCode not found with id " + id);
        }
    }
}
