package com.secret.platform.location;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeService;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeService;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private RateProductService rateProductService;

    @Autowired
    private ClassCodeService classCodeService;

    @Autowired
    private PricingCodeService pricingCodeService;

    @PostMapping("/rate-product")
    public ResponseEntity<RateProduct> createRateProduct(@RequestBody RateProduct rateProduct) {
        RateProduct createdRateProduct = rateProductService.createRateProduct(rateProduct, null);
        return ResponseEntity.ok(createdRateProduct);
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location createdLocation = locationService.saveLocation(location);
        return ResponseEntity.ok(createdLocation);
    }

    @PostMapping("/class-code")
    public ResponseEntity<ClassCode> createClassCode(@RequestParam Long locationId, @RequestParam Long pricingCodeId, @RequestBody ClassCode classCode) {
        Location location = locationService.getLocationById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with ID " + locationId));
        PricingCode pricingCode = pricingCodeService.getPricingCodeById(pricingCodeId)
                .orElseThrow(() -> new ResourceNotFoundException("PricingCode not found with ID " + pricingCodeId));
        classCode.setLocation(location);
        classCode.setPricingCode(pricingCode);
        ClassCode createdClassCode = classCodeService.createClassCode(classCode);
        return ResponseEntity.ok(createdClassCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        Optional<Location> location = locationService.getLocationById(id);
        if (location.isPresent()) {
            return ResponseEntity.ok(location.get());
        } else {
            throw new ResourceNotFoundException("Location not found with ID " + id);
        }
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) {
        Location updatedLocation = locationService.updateLocation(id, locationDetails);
        return ResponseEntity.ok(updatedLocation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
