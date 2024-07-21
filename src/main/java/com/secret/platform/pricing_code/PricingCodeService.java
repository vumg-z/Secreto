package com.secret.platform.pricing_code;

import java.util.List;
import java.util.Optional;

public interface PricingCodeService {
    PricingCode createPricingCode(PricingCode pricingCode);
    Optional<PricingCode> getPricingCodeById(Long id);
    List<PricingCode> getAllPricingCodes();
    PricingCode updatePricingCode(Long id, PricingCode pricingCode);
    void deletePricingCode(Long id);
}
