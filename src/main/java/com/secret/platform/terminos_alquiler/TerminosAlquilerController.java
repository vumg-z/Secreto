package com.secret.platform.terminos_alquiler;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terminos_alquiler")
public class TerminosAlquilerController {

    @Autowired
    private TerminosAlquilerService terminosAlquilerService;

    @GetMapping
    public List<TerminosAlquiler> getAllTerminosAlquiler() {
        return terminosAlquilerService.getAllTerminosAlquiler();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TerminosAlquiler> getTerminosAlquilerById(@PathVariable Long id) {
        TerminosAlquiler terminosAlquiler = terminosAlquilerService.getTerminosAlquilerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TerminosAlquiler not found for this id :: " + id));
        return ResponseEntity.ok().body(terminosAlquiler);
    }

    @PostMapping
    public TerminosAlquiler createTerminosAlquiler(@RequestBody TerminosAlquiler terminosAlquiler) {
        return terminosAlquilerService.createTerminosAlquiler(terminosAlquiler);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TerminosAlquiler> updateTerminosAlquiler(@PathVariable Long id, @RequestBody TerminosAlquiler terminosAlquilerDetails) {
        TerminosAlquiler updatedTerminosAlquiler = terminosAlquilerService.updateTerminosAlquiler(id, terminosAlquilerDetails);
        return ResponseEntity.ok(updatedTerminosAlquiler);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTerminosAlquiler(@PathVariable Long id) {
        terminosAlquilerService.deleteTerminosAlquiler(id);
        return ResponseEntity.noContent().build();
    }
}
