package com.secret.platform.class_code;

import com.secret.platform.location.Location;
import com.secret.platform.rate_product.RateProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassCodeRepository extends JpaRepository<ClassCode, Long> {
    Object findByLocationId(long l);

    List<ClassCode> findAllByRateProduct(RateProduct rateProduct);
    List<ClassCode> findAllByLocation(Location location);
    Optional<ClassCode> findByClassCode(String classCode);
    List<ClassCode> findByRateProduct(RateProduct rateProduct);


}
