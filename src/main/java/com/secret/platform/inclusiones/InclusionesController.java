package com.secret.platform.inclusiones;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inclusiones")
public class InclusionesController {

    @Autowired
    private com.secret.platform.inclusiones.InclusionesService inclusionesService;

    @GetMapping
    public List<Inclusiones> getAllInclusiones() {
        return inclusionesService.getAllInclusiones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inclusiones> getInclusionesById(@PathVariable Long id) {
        Inclusiones inclusiones = inclusionesService.getInclusionesById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inclusiones not found for this id :: " + id));
        return ResponseEntity.ok().body(inclusiones);
    }

    @PostMapping
    public Inclusiones createInclusiones(@RequestBody Inclusiones inclusiones) {
        return inclusionesService.createInclusiones(inclusiones);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inclusiones> updateInclusiones(@PathVariable Long id, @RequestBody Inclusiones inclusionesDetails) {
        Inclusiones updatedInclusiones = inclusionesService.updateInclusiones(id, inclusionesDetails);
        return ResponseEntity.ok(updatedInclusiones);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInclusiones(@PathVariable Long id) {
        inclusionesService.deleteInclusiones(id);
        return ResponseEntity.noContent().build();
    }
}
