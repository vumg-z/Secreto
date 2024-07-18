package com.secret.platform.rate_product;

import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rate-products")
public class RateProductController {

    @Autowired
    private RateProductService rateProductService;

    @GetMapping
    public List<RateProduct> getAllRateProducts() {
        return rateProductService.getAllRateProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateProduct> getRateProductById(@PathVariable Long id) {
        Optional<RateProduct> rateProduct = rateProductService.getRateProductById(id);
        return rateProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RateProduct createRateProduct(@RequestBody RateProduct rateProduct) {
        return rateProductService.createRateProduct(rateProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateProduct> updateRateProduct(@PathVariable Long id, @RequestBody RateProduct rateProductDetails) {
        RateProduct updatedRateProduct = rateProductService.updateRateProduct(id, rateProductDetails);
        if (updatedRateProduct != null) {
            return ResponseEntity.ok(updatedRateProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRateProduct(@PathVariable Long id) {
        rateProductService.deleteRateProduct(id);
        return ResponseEntity.noContent().build();
    }
}
