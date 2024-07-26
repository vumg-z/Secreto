package com.secret.platform.privilege_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privilege-codes")
public class PrivilegeCodeController {

    @Autowired
    private PrivilegeCodeService privilegeCodeService;

    @GetMapping
    public List<PrivilegeCode> getAllPrivilegeCodes() {
        return privilegeCodeService.getAllPrivilegeCodes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrivilegeCode> getPrivilegeCodeById(@PathVariable Long id) {
        PrivilegeCode privilegeCode = privilegeCodeService.getPrivilegeCodeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PrivilegeCode not found for this id :: " + id));
        return ResponseEntity.ok(privilegeCode);
    }

    @PostMapping
    public ResponseEntity<PrivilegeCode> createPrivilegeCode(@RequestBody PrivilegeCode privilegeCode) {
        PrivilegeCode createdPrivilegeCode = privilegeCodeService.createPrivilegeCode(privilegeCode);
        return ResponseEntity.ok(createdPrivilegeCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrivilegeCode> updatePrivilegeCode(@PathVariable Long id, @RequestBody PrivilegeCode privilegeCodeDetails) {
        PrivilegeCode updatedPrivilegeCode = privilegeCodeService.updatePrivilegeCode(id, privilegeCodeDetails);
        return ResponseEntity.ok(updatedPrivilegeCode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilegeCode(@PathVariable Long id) {
        privilegeCodeService.deletePrivilegeCode(id);
        return ResponseEntity.noContent().build();
    }
}
