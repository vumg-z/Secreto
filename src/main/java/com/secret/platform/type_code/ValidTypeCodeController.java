package com.secret.platform.type_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/valid-type-codes")
public class ValidTypeCodeController {

    @Autowired
    private ValidTypeCodeService validTypeCodeService;

    // Get all ValidTypeCodes
    @GetMapping
    public List<ValidTypeCode> getAllValidTypeCodes() {
        return validTypeCodeService.getAllValidTypeCodes();
    }

    // Get a single ValidTypeCode by code
    @GetMapping("/{code}")
    public ResponseEntity<ValidTypeCode> getValidTypeCodeByCode(@PathVariable(value = "code") String code) {
        ValidTypeCode validTypeCode = validTypeCodeService.getValidTypeCodeByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("ValidTypeCode not found for this code :: " + code));
        return ResponseEntity.ok().body(validTypeCode);
    }

    // Create a new ValidTypeCode
    @PostMapping
    public ValidTypeCode createValidTypeCode(@RequestBody ValidTypeCode validTypeCode) {
        return validTypeCodeService.createValidTypeCode(validTypeCode);
    }

    // Update an existing ValidTypeCode
    @PutMapping("/{code}")
    public ResponseEntity<ValidTypeCode> updateValidTypeCode(
            @PathVariable(value = "code") String code,
            @RequestBody ValidTypeCode validTypeCodeDetails) {

        ValidTypeCode updatedValidTypeCode = validTypeCodeService.updateValidTypeCodeByCode(code, validTypeCodeDetails);
        return ResponseEntity.ok(updatedValidTypeCode);
    }

    // Delete a ValidTypeCode
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteValidTypeCode(@PathVariable(value = "code") String code) {
        validTypeCodeService.deleteValidTypeCodeByCode(code);
        return ResponseEntity.noContent().build();
    }
}
