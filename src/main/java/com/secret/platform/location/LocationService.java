package com.secret.platform.location;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements LocationServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private RateProductRepository rateProductRepository;

    @Autowired
    private RateSetRepository rateSetRepository;

    @Override
    public Location saveLocation(Location location) {
        logger.debug("Attempting to save location with number: {}", location.getLocationNumber());

        if (!isLocationNumberUnique(location.getLocationNumber())) {
            logger.error("Location number must be unique. Provided location number: {}", location.getLocationNumber());
            throw new IllegalArgumentException("Location number must be unique");
        }

        if (location.getDispatchControl() == null) {
            logger.debug("Setting default dispatch control to 'N'");
            location.setDispatchControl("N");
        }

        // Ensure the RateSet is saved before associating it with the Location
        if (location.getRateSet() != null) {
            logger.debug("RateSet provided: {}", location.getRateSet().getRateSetCode());
            RateSet rateSet = rateSetRepository.findByRateSetCode(location.getRateSet().getRateSetCode())
                    .orElseGet(() -> {
                        logger.debug("RateSet not found. Saving new RateSet with code: {}", location.getRateSet().getRateSetCode());
                        return rateSetRepository.save(location.getRateSet());
                    });
            location.setRateSet(rateSet);
            logger.debug("RateSet set to location: {}", rateSet.getRateSetCode());
        }

        // Validate and set the RateProduct
        if (location.getWalkupRate() != null) {
            logger.debug("WalkupRate provided: {}", location.getWalkupRate().getProduct());
            RateProduct rateProduct = rateProductRepository.findByProduct(location.getWalkupRate().getProduct())
                    .orElseThrow(() -> {
                        logger.error("Invalid rate product: {}", location.getWalkupRate().getProduct());
                        return new IllegalArgumentException("Invalid rate product");
                    });
            location.setWalkupRate(rateProduct);
            logger.debug("WalkupRate set to location: {}", rateProduct.getProduct());
        }

        Location savedLocation = locationRepository.save(location);
        logger.info("Location saved successfully with number: {}", savedLocation.getLocationNumber());

        return savedLocation;
    }

    @Override
    public List<Location> findLocationsByGroupCode(String groupCode) {
        return locationRepository.findByMetroplexLocation_GroupCode(groupCode);
    }

    @Override
    public Optional<Location> findLocationByNumber(String locationNumber) {
        return locationRepository.findByLocationNumber(locationNumber);
    }

    @Override
    public boolean isLocationNumberUnique(String locationNumber) {
        return locationRepository.findByLocationNumber(locationNumber).isEmpty();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location updateLocation(Long id, Location locationDetails) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id " + id));

        existingLocation.setLocationName(locationDetails.getLocationName());
        existingLocation.setAddressLine1(locationDetails.getAddressLine1());
        existingLocation.setAddressLine2(locationDetails.getAddressLine2());
        existingLocation.setAddressLine3(locationDetails.getAddressLine3());
        existingLocation.setPhone(locationDetails.getPhone());
        existingLocation.setProfitCenterNumber(locationDetails.getProfitCenterNumber());
        existingLocation.setDoFuelCalc(locationDetails.getDoFuelCalc());
        existingLocation.setHoldingDrawer(locationDetails.getHoldingDrawer());
        existingLocation.setAutoVehicleSelect(locationDetails.getAutoVehicleSelect());
        existingLocation.setCheckInStatus(locationDetails.getCheckInStatus());
        existingLocation.setCheckOutFuel(locationDetails.getCheckOutFuel());
        existingLocation.setValidRentalLoc(locationDetails.getValidRentalLoc());
        existingLocation.setAllowMultiLanguageRa(locationDetails.getAllowMultiLanguageRa());
        existingLocation.setAllowWaitRas(locationDetails.getAllowWaitRas());
        existingLocation.setRegion(locationDetails.getRegion());
        existingLocation.setDispatchControl(locationDetails.getDispatchControl());
        existingLocation.setMetroplexLocation(locationDetails.getMetroplexLocation());

        // Ensure the RateSet is saved before associating it with the Location
        if (locationDetails.getRateSet() != null) {
            RateSet rateSet = rateSetRepository.findByRateSetCode(locationDetails.getRateSet().getRateSetCode())
                    .orElseGet(() -> rateSetRepository.save(locationDetails.getRateSet()));
            existingLocation.setRateSet(rateSet);
        }

        // Validate and set the RateProduct
        if (locationDetails.getWalkupRate() != null) {
            RateProduct rateProduct = rateProductRepository.findByProduct(locationDetails.getWalkupRate().getProduct())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid rate product"));
            existingLocation.setWalkupRate(rateProduct);
        } else {
            existingLocation.setWalkupRate(null);
        }

        return locationRepository.save(existingLocation);
    }

    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location not found with id " + id);
        }
        locationRepository.deleteById(id);
    }

    @Override
    public String getRateSet(String locationNumber) {
        Location location = locationRepository.findByLocationNumber(locationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with number " + locationNumber));

        if (location.getRateSet() == null) {
            throw new IllegalArgumentException("No RateSet associated with this location");
        }

        return location.getRateSet().getRateSetCode();
    }

}
