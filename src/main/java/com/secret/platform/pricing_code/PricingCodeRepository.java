package com.secret.platform.pricing_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingCodeRepository extends JpaRepository<PricingCode, Long> {
    Optional<PricingCode> findByCode(String code);
}
