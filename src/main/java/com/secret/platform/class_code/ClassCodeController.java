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

    @GetMapping
    public List<ClassCode> getAllClassCodes() {
        return classCodeService.getAllClassCodes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassCode> getClassCodeById(@PathVariable Long id) {
        return classCodeService.getClassCodeById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ClassCode not found with id " + id));
    }

    @PostMapping
    public ClassCode createClassCode(@RequestBody ClassCode classCode) {
        return classCodeService.createClassCode(classCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassCode> updateClassCode(@PathVariable Long id, @RequestBody ClassCode classCode) {
        return ResponseEntity.ok(classCodeService.updateClassCode(id, classCode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassCode(@PathVariable Long id) {
        classCodeService.deleteClassCode(id);
        return ResponseEntity.noContent().build();
    }
}
