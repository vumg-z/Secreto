package com.secret.platform.rate_set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rateSets")
public class RateSetController {

    @Autowired
    private RateSetService rateSetService;

    @GetMapping
    public List<RateSetResponseDTO> getAllRateSets() {
        return rateSetService.getAllRateSets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateSetResponseDTO> getRateSetById(@PathVariable Long id) {
        RateSetResponseDTO rateSet = rateSetService.getRateSetById(id);
        return ResponseEntity.ok(rateSet);
    }

    @PostMapping
    public RateSet createRateSet(@RequestBody RateSet rateSet) {
        return rateSetService.createRateSet(rateSet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateSet> updateRateSet(@PathVariable Long id, @RequestBody RateSet rateSetDetails) {
        RateSet updatedRateSet = rateSetService.updateRateSet(id, rateSetDetails);
        return ResponseEntity.ok(updatedRateSet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRateSet(@PathVariable Long id) {
        rateSetService.deleteRateSet(id);
        return ResponseEntity.noContent().build();
    }
}
