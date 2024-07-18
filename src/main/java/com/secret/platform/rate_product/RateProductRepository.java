package com.secret.platform.rate_product;

import com.secret.platform.rate_product.RateProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateProductRepository extends JpaRepository<RateProduct, Long> {
}
