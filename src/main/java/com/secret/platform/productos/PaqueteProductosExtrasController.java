package com.secret.platform.productos;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteProductosExtrasController {

    @Autowired
    private PaqueteProductosExtrasService paqueteProductosExtrasService;

    @GetMapping
    public List<PaqueteProductosExtras> getAllPaquetes() {
        return paqueteProductosExtrasService.getAllPaquetes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaqueteProductosExtras> getPaqueteById(@PathVariable Long id) {
        PaqueteProductosExtras paquete = paqueteProductosExtrasService.getPaqueteById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaqueteProductosExtras not found for this id :: " + id));
        return ResponseEntity.ok().body(paquete);
    }

    @PostMapping
    public PaqueteProductosExtras createPaquete(@RequestBody PaqueteProductosExtras paquete) {
        return paqueteProductosExtrasService.createPaquete(paquete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaqueteProductosExtras> updatePaquete(@PathVariable Long id, @RequestBody PaqueteProductosExtras paqueteDetails) {
        PaqueteProductosExtras updatedPaquete = paqueteProductosExtrasService.updatePaquete(id, paqueteDetails);
        return ResponseEntity.ok(updatedPaquete);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaquete(@PathVariable Long id) {
        paqueteProductosExtrasService.deletePaquete(id);
        return ResponseEntity.noContent().build();
    }
}
