package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.location.Location;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RateProductService {
    List<RateProduct> getAllRateProducts();
    Optional<RateProduct> getRateProductById(Long id);
    RateProduct createRateProduct(RateProduct rateProduct, Map<String, Boolean> coverages);
    RateProduct updateRateProduct(Long id, RateProduct rateProductDetails, Map<String, Boolean> coverages) throws IllegalAccessException;
    void deleteRateProduct(Long id);

    List<ClassCode> getAllClassesWithRatesByLocation(Location defaultLocation);
}
