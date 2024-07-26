package com.secret.platform.class_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-codes")
public class ClassCodeController {

    @Autowired
    private ClassCodeService classCodeService;

    @PostMapping
    public ResponseEntity<ClassCode> createClassCode(@RequestBody ClassCode classCode) {
        return ResponseEntity.ok(classCodeService.createClassCode(classCode));
    }

    @GetMapping
    public ResponseEntity<List<ClassCode>> getAllClassCodes() {
        return ResponseEntity.ok(classCodeService.getAllClassCodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassCode> getClassCodeById(@PathVariable Long id) {
        return classCodeService.getClassCodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassCode> updateClassCode(@PathVariable Long id, @RequestBody ClassCode classCodeDetails) {
        try {
            return ResponseEntity.ok(classCodeService.updateClassCode(id, classCodeDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassCode(@PathVariable Long id) {
        try {
            classCodeService.deleteClassCode(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
