package com.secret.platform.dto;

import java.time.LocalDateTime;

public class ResRatesRequest {

    private String pickupLocationCode;
    private LocalDateTime pickupDateTime;
    private String returnLocationCode;
    private LocalDateTime returnDateTime;
    private String countryCode;
    private String corpRateID;

    // Getters and Setters

    public String getPickupLocationCode() {
        return pickupLocationCode;
    }

    public void setPickupLocationCode(String pickupLocationCode) {
        this.pickupLocationCode = pickupLocationCode;
    }

    public LocalDateTime getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(LocalDateTime pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public String getReturnLocationCode() {
        return returnLocationCode;
    }

    public void setReturnLocationCode(String returnLocationCode) {
        this.returnLocationCode = returnLocationCode;
    }

    public LocalDateTime getReturnDateTime() {
        return returnDateTime;
    }

    public void setReturnDateTime(LocalDateTime returnDateTime) {
        this.returnDateTime = returnDateTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCorpRateID() {
        return corpRateID;
    }

    public void setCorpRateID(String corpRateID) {
        this.corpRateID = corpRateID;
    }
}
