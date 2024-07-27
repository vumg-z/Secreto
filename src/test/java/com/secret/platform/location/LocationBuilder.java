package com.secret.platform.location;

import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.group_code.GroupCodes;
import com.secret.platform.status_code.StatusCode;

public class LocationBuilder {
    private String locationNumber;
    private String locationName;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String phone;
    private String profitCenterNumber;
    private String doFuelCalc;
    private String holdingDrawer;
    private String autoVehicleSelect;
    private StatusCode checkInStatus;
    private String checkOutFuel;
    private Location region;
    private String dispatchControl;
    private GeneralLedger interOfcArAcct;
    private GroupCodes metroplexLocation;
    private String validRentalLoc;
    private String allowMultiLanguageRa;
    private String allowWaitRas;
    private String rateSet;

    public LocationBuilder setLocationNumber(String locationNumber) {
        this.locationNumber = locationNumber;
        return this;
    }

    public LocationBuilder setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public LocationBuilder setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public LocationBuilder setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public LocationBuilder setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
        return this;
    }

    public LocationBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LocationBuilder setProfitCenterNumber(String profitCenterNumber) {
        this.profitCenterNumber = profitCenterNumber;
        return this;
    }

    public LocationBuilder setDoFuelCalc(String doFuelCalc) {
        this.doFuelCalc = doFuelCalc;
        return this;
    }

    public LocationBuilder setHoldingDrawer(String holdingDrawer) {
        this.holdingDrawer = holdingDrawer;
        return this;
    }

    public LocationBuilder setAutoVehicleSelect(String autoVehicleSelect) {
        this.autoVehicleSelect = autoVehicleSelect;
        return this;
    }

    public LocationBuilder setCheckInStatus(StatusCode checkInStatus) {
        this.checkInStatus = checkInStatus;
        return this;
    }

    public LocationBuilder setCheckOutFuel(String checkOutFuel) {
        this.checkOutFuel = checkOutFuel;
        return this;
    }

    public LocationBuilder setRegion(Location region) {
        this.region = region;
        return this;
    }

    public LocationBuilder setDispatchControl(String dispatchControl) {
        this.dispatchControl = dispatchControl;
        return this;
    }

    public LocationBuilder setInterOfcArAcct(GeneralLedger interOfcArAcct) {
        this.interOfcArAcct = interOfcArAcct;
        return this;
    }

    public LocationBuilder setMetroplexLocation(GroupCodes metroplexLocation) {
        this.metroplexLocation = metroplexLocation;
        return this;
    }

    public LocationBuilder setValidRentalLoc(String validRentalLoc) {
        this.validRentalLoc = validRentalLoc;
        return this;
    }

    public LocationBuilder setAllowMultiLanguageRa(String allowMultiLanguageRa) {
        this.allowMultiLanguageRa = allowMultiLanguageRa;
        return this;
    }

    public LocationBuilder setAllowWaitRas(String allowWaitRas) {
        this.allowWaitRas = allowWaitRas;
        return this;
    }

    public LocationBuilder setRateSet(String rateSet) {
        this.rateSet = rateSet;
        return this;
    }

    public Location build() {
        Location location = new Location();
        location.setLocationNumber(locationNumber);
        location.setLocationName(locationName);
        location.setAddressLine1(addressLine1);
        location.setAddressLine2(addressLine2);
        location.setAddressLine3(addressLine3);
        location.setPhone(phone);
        location.setProfitCenterNumber(profitCenterNumber);
        location.setDoFuelCalc(doFuelCalc);
        location.setHoldingDrawer(holdingDrawer);
        location.setAutoVehicleSelect(autoVehicleSelect);
        location.setCheckInStatus(checkInStatus);
        location.setCheckOutFuel(checkOutFuel);
        location.setRegion(region);
        location.setDispatchControl(dispatchControl);
        //location.setInterOfcArAcct(interOfcArAcct);
        location.setMetroplexLocation(metroplexLocation);
        location.setValidRentalLoc(validRentalLoc);
        location.setAllowMultiLanguageRa(allowMultiLanguageRa);
        location.setAllowWaitRas(allowWaitRas);
        //location.setRateSet(rateSet);
        return location;
    }
}
