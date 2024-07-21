package com.secret.platform.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationNumber(String locationNumber);
    Optional<Location> findByHoldingDrawer(String holdingDrawer);

    List<Location> findByMetroplexLocation_GroupCode(String groupCode);


}
