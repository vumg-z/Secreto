package com.secret.platform.location;

import java.util.Optional;

public interface LocationServiceInterface {
    Location saveLocation(Location location);
    Optional<Location> findLocationByNumber(String locationNumber);
    boolean isLocationNumberUnique(String locationNumber);
}
