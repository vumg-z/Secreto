package com.secret.platform.location;

import com.secret.platform.config.FeatureFlagServiceInterface;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.general_ledger.GeneralLedgerRepository;
import com.secret.platform.group_code.GroupCodes;
import com.secret.platform.group_code.GroupCodesRepository;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.status_code.StatusCode;
import com.secret.platform.status_code.StatusCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements LocationServiceInterface {

    @Autowired
    private FeatureFlagServiceInterface featureFlagService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private StatusCodeRepository statusCodeRepository;

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    private GroupCodesRepository groupCodesRepository;

    @Autowired
    private RateProductRepository rateProductRepository;

    public Location saveLocation(Location location) {
        //validateProfitCenterNumber(location.getProfitCenterNumber());
       // validateDoFuelCalc(location.getDoFuelCalc());
        //validateAutoVehicleSelect(location.getAutoVehicleSelect());
        //validateCheckOutFuel(location.getCheckOutFuel());
        //validateValidRentalLoc(location.getValidRentalLoc());
        //validateAllowMultiLanguageRa(location.getAllowMultiLanguageRa());
        //validateAllowWaitRas(location.getAllowWaitRas());

        if (!isLocationNumberUnique(location.getLocationNumber())) {
            throw new IllegalArgumentException("Location number must be unique");
        }

        /*
       if (location.getCheckInStatus() != null) {
            StatusCode checkInStatus = statusCodeRepository.findById(location.getCheckInStatus().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid check-in status"));
            location.setCheckInStatus(checkInStatus);
        } else {
            StatusCode defaultStatus = statusCodeRepository.findByCode("A")
                    .orElseThrow(() -> new IllegalArgumentException("Default check-in status 'A' does not exist"));
            location.setCheckInStatus(defaultStatus);
        }

         */

        /*

        if (featureFlagService.isHoldingDrawerEnabled()) {
            validateHoldingDrawer(location.getHoldingDrawer());

            if (!isHoldingDrawerUnique(location.getHoldingDrawer())) {
                throw new IllegalArgumentException("Holding drawer must be unique");
            }
        }
         */

        /*

        if (location.getRegion() != null) {
            validateRegion(location.getRegion());
        }
         */

        if (location.getDispatchControl() == null) {
            location.setDispatchControl("N");
        }

        // Validate metroplex location
       // validateMetroplexLocation(location.getMetroplexLocation());

        // Validate and set the RateProduct
        if (location.getWalkupRate() != null) {
            RateProduct rateProduct = rateProductRepository.findByProduct(location.getWalkupRate().getProduct())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid rate product"));
            location.setWalkupRate(rateProduct);
        }

        return locationRepository.save(location);
    }

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

    private void validateAutoVehicleSelect(String autoVehicleSelect) {
        if (autoVehicleSelect == null || !autoVehicleSelect.matches("[YN]")) {
            throw new IllegalArgumentException("AUTO VEHICLE SELECT must be 'Y' or 'N'");
        }
    }

    private void validateCheckInStatus(StatusCode checkInStatus) {
        if (checkInStatus == null || !statusCodeRepository.existsByCode(checkInStatus.getCode())) {
            throw new IllegalArgumentException("Invalid check-in status code");
        }
    }

    private void validateCheckOutFuel(String checkOutFuel) {
        if (checkOutFuel == null || !checkOutFuel.matches("[789]")) {
            throw new IllegalArgumentException("CHECK-OUT FUEL must be '7', '8', or '9'");
        }
    }

    private void validateRegion(Location region) {
        if (region == null || !"N".equals(region.getValidRentalLoc())) {
            throw new IllegalArgumentException("Region must be a non-renting location");
        }
    }

    private void validateValidRentalLoc(String validRentalLoc) {
        if (validRentalLoc == null || !validRentalLoc.matches("[YNyPwWCoOnFR]")) {
            throw new IllegalArgumentException("VALID RENTAL LOC must be one of the valid codes: Y, N, y, P, W, C, O, X, n, F, R");
        }
    }

    private void validateMetroplexLocation(GroupCodes metroplexLocation) {
        if (metroplexLocation == null || !groupCodesRepository.existsById(metroplexLocation.getId())) {
            throw new IllegalArgumentException("Invalid metroplex group code");
        }
    }

    private void validateAllowMultiLanguageRa(String allowMultiLanguageRa) {
        if (allowMultiLanguageRa == null || !allowMultiLanguageRa.matches("[YNX]")) {
            throw new IllegalArgumentException("ALLOW MULTI-LANGUAGE RA must be 'Y', 'N', or 'X'");
        }
    }

    private void validateAllowWaitRas(String allowWaitRas) {
        if (allowWaitRas == null || !allowWaitRas.matches("[YN]")) {
            throw new IllegalArgumentException("ALLOW WAIT RAs must be 'Y' or 'N'");
        }
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
        //existingLocation.setInterOfcArAcct(locationDetails.getInterOfcArAcct());
        existingLocation.setAllowMultiLanguageRa(locationDetails.getAllowMultiLanguageRa());
        existingLocation.setAllowWaitRas(locationDetails.getAllowWaitRas());
        existingLocation.setRegion(locationDetails.getRegion());
        existingLocation.setDispatchControl(locationDetails.getDispatchControl());
        existingLocation.setMetroplexLocation(locationDetails.getMetroplexLocation());

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
}
