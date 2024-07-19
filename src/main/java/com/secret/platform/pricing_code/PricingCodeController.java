package com.secret.platform.pricing_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-codes")
public class PricingCodeController {

    private final PricingCodeService pricingCodeService;

    @Autowired
    public PricingCodeController(PricingCodeService pricingCodeService) {
        this.pricingCodeService = pricingCodeService;
    }

    @PostMapping
    public ResponseEntity<PricingCode> createPricingCode(@RequestBody PricingCode pricingCode) {
        return ResponseEntity.ok(pricingCodeService.createPricingCode(pricingCode));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PricingCode> getPricingCodeById(@PathVariable Long id) {
        return pricingCodeService.getPricingCodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PricingCode>> getAllPricingCodes() {
        return ResponseEntity.ok(pricingCodeService.getAllPricingCodes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PricingCode> updatePricingCode(@PathVariable Long id, @RequestBody PricingCode pricingCode) {
        PricingCode updatedPricingCode = pricingCodeService.updatePricingCode(id, pricingCode);
        if (updatedPricingCode != null) {
            return ResponseEntity.ok(updatedPricingCode);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePricingCode(@PathVariable Long id) {
        pricingCodeService.deletePricingCode(id);
        return ResponseEntity.noContent().build();
    }
}
