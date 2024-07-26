package com.secret.platform.rate_set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateSetRepository extends JpaRepository<RateSet, Long> {
    Optional<RateSet> findByRateSetCode(String rateSetCode);
}
