package com.secret.platform.location;

import com.secret.platform.config.FeatureFlagServiceInterface;
import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.general_ledger.GeneralLedgerRepository;
import com.secret.platform.status_code.StatusCode;
import com.secret.platform.status_code.StatusCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Location saveLocation(Location location) {
        validateProfitCenterNumber(location.getProfitCenterNumber());
        validateDoFuelCalc(location.getDoFuelCalc());
        validateAutoVehicleSelect(location.getAutoVehicleSelect());
        validateCheckOutFuel(location.getCheckOutFuel());
        validateValidRentalLoc(location.getValidRentalLoc());
        validateInterOfcArAcct(location.getInterOfcArAcct());


        if (!isLocationNumberUnique(location.getLocationNumber())) {
            throw new IllegalArgumentException("Location number must be unique");
        }

        if (location.getCheckInStatus() == null) {
            StatusCode defaultStatus = statusCodeRepository.findByCode("A")
                    .orElseThrow(() -> new IllegalArgumentException("Default check-in status 'A' does not exist"));
            location.setCheckInStatus(defaultStatus);
        } else {
            validateCheckInStatus(location.getCheckInStatus());
        }

        if (featureFlagService.isHoldingDrawerEnabled()) {
            validateHoldingDrawer(location.getHoldingDrawer());

            if (!isHoldingDrawerUnique(location.getHoldingDrawer())) {
                throw new IllegalArgumentException("Holding drawer must be unique");
            }
        }

        if (location.getRegion() != null) {
            validateRegion(location.getRegion());
        }

        if (location.getDispatchControl() == null) {
            location.setDispatchControl("N");
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

    public void validateInterOfcArAcct(GeneralLedger interOfcArAcct) {
        if (interOfcArAcct == null || !generalLedgerRepository.existsById(interOfcArAcct.getId())) {
            throw new IllegalArgumentException("INTER-OFC A/R ACCT must be a valid General Ledger account");
        }
    }

    private void validateValidRentalLoc(String validRentalLoc) {
        if (validRentalLoc == null || !validRentalLoc.matches("[YNyPwWCoOnFR]")) {
            throw new IllegalArgumentException("VALID RENTAL LOC must be one of the valid codes: Y, N, y, P, W, C, O, X, n, F, R");
        }
    }
}
