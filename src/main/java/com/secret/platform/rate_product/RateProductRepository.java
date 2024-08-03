package com.secret.platform.rate_product;

import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.type_code.ValidTypeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateProductRepository extends JpaRepository<RateProduct, Long> {

    List<RateProduct> findAllByRateSet(RateSet rateSet);


    Optional<RateProduct> findByProduct(String product);

    List<RateProduct> findAllByProduct(String rateProductName);

    Optional<RateProduct> findByProductAndRateSet_RateSetCode(String product, String rateSetCode);

}
