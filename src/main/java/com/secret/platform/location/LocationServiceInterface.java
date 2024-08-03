package com.secret.platform.location;

import com.secret.platform.rate_set.RateSet;

import java.util.List;
import java.util.Optional;

public interface LocationServiceInterface {
    Location saveLocation(Location location);

    List<Location> findLocationsByGroupCode(String groupCode);

    Optional<Location> findLocationByNumber(String locationNumber);
    boolean isLocationNumberUnique(String locationNumber);

    List<Location> getAllLocations();

    Location updateLocation(Long id, Location locationDetails);

    Optional<Location> getLocationById(Long id);

    void deleteLocation(Long id);

    String getRateSet(String locationNumber);

}
