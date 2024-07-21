package com.secret.platform.tarifa;

import com.secret.platform.dto.ResRatesRequest;
import com.secret.platform.dto.ResRatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> getAllTarifas() {
        return tarifaService.getAllTarifas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> getTarifaById(@PathVariable Long id) {
        Optional<Tarifa> tarifa = tarifaService.getTarifaById(id);
        return tarifa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarifa createTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.createTarifa(tarifa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(@PathVariable Long id, @RequestBody Tarifa tarifaDetails) {
        Tarifa updatedTarifa = tarifaService.updateTarifa(id, tarifaDetails);
        return ResponseEntity.ok(updatedTarifa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable Long id) {
        tarifaService.deleteTarifa(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<ResRatesResponse> getRates(@RequestBody ResRatesRequest request) {
        ResRatesResponse response = tarifaService.getRates(request, request.getRateSet());
        return ResponseEntity.ok(response);
    }
}
