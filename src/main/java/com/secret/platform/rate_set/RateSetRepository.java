package com.secret.platform.rate_set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateSetRepository extends JpaRepository<RateSet, Long> {
}
