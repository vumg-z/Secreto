package com.secret.platform.location;

import com.secret.platform.config.FeatureFlagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService implements LocationServiceInterface {

    @Autowired
    private FeatureFlagServiceInterface featureFlagService;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        validateProfitCenterNumber(location.getProfitCenterNumber());
        validateDoFuelCalc(location.getDoFuelCalc());

        if (!isLocationNumberUnique(location.getLocationNumber())) {
            throw new IllegalArgumentException("Location number must be unique");
        }

        if (featureFlagService.isHoldingDrawerEnabled()) {
            validateHoldingDrawer(location.getHoldingDrawer());

            if (!isHoldingDrawerUnique(location.getHoldingDrawer())) {
                throw new IllegalArgumentException("Holding drawer must be unique");
            }
        }

        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> findLocationByNumber(String locationNumber) {
        return locationRepository.findByLocationNumber(locationNumber);
    }

    @Override
    public boolean isLocationNumberUnique(String locationNumber) {
        return locationRepository.findByLocationNumber(locationNumber).isEmpty();
    }

    private void validateProfitCenterNumber(String profitCenterNumber) {
        if (profitCenterNumber == null || !profitCenterNumber.matches("\\d{1,3}")) {
            throw new IllegalArgumentException("Profit Center Number must be a numeric value up to 3 digits");
        }
    }

    private void validateDoFuelCalc(String doFuelCalc) {
        if (doFuelCalc == null || !doFuelCalc.matches("[YN]")) {
            throw new IllegalArgumentException("DO FUEL CALC must be 'Y' or 'N'");
        }
    }

    private void validateHoldingDrawer(String holdingDrawer) {
        if (holdingDrawer == null || !holdingDrawer.matches("[A-Za-z0-9]{1,3}")) {
            throw new IllegalArgumentException("Holding Drawer must be an alphanumeric value up to 3 characters");
        }
    }

    public boolean isHoldingDrawerUnique(String holdingDrawer) {
        return locationRepository.findByHoldingDrawer(holdingDrawer).isEmpty();
    }
}
