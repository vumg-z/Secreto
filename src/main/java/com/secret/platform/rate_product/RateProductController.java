package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCodeDTO;
import com.secret.platform.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rate-products")
public class RateProductController {

    private static final Logger logger = LoggerFactory.getLogger(RateProductController.class);

    @Autowired
    private RateProductService rateProductService;

    @PostMapping
    public ResponseEntity<RateProduct> createRateProduct(@RequestBody RateProduct rateProduct,
                                                         @RequestParam Map<String, Boolean> coverages) {
        logger.info("Received RateProduct: {}", rateProduct);
        logger.info("Received coverages: {}", coverages);
        RateProduct createdRateProduct = rateProductService.createRateProduct(rateProduct, coverages);
        return ResponseEntity.ok(createdRateProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateProduct> getRateProductById(@PathVariable Long id) {
        RateProduct rateProduct = rateProductService.getRateProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateProduct not found for this id :: " + id));
        return ResponseEntity.ok(rateProduct);
    }

    @GetMapping
    public List<RateProduct> getAllRateProducts() {
        return rateProductService.getAllRateProducts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateProduct> updateRateProduct(@PathVariable Long id,
                                                         @RequestBody RateProduct rateProductDetails,
                                                         @RequestParam Map<String, Boolean> coverages) throws IllegalAccessException {
        logger.info("Updating RateProduct with ID: {}", id);
        logger.info("Received RateProduct details: {}", rateProductDetails);
        logger.info("Received coverages: {}", coverages);
        RateProduct updatedRateProduct = rateProductService.updateRateProduct(id, rateProductDetails, coverages);
        return ResponseEntity.ok(updatedRateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRateProduct(@PathVariable Long id) {
        rateProductService.deleteRateProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-classes")
    public ResponseEntity<RateProduct> addClassesToRateProduct(@RequestBody List<ClassCodeDTO> classCodeDTOs) {
        RateProduct updatedRateProduct = rateProductService.addClassesToRateProduct(classCodeDTOs);
        logger.info("Returning RateProduct: xDayRate = {}, weekRate = {}", updatedRateProduct.getXDayRate(), updatedRateProduct.getWeekRate());
        return ResponseEntity.ok(updatedRateProduct);
    }

}
