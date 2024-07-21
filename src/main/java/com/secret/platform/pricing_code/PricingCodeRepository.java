package com.secret.platform.pricing_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingCodeRepository extends JpaRepository<PricingCode, Long> {
    // Additional query methods can be defined here if needed
}
