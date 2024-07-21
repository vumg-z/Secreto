package com.secret.platform.dto;

import java.util.List;

public class ResRatesResponse {

    private String message;

    private boolean success;
    private int count;
    private List<Rate> rates;

    public ResRatesResponse() {
    }

    public ResRatesResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResRatesResponse(boolean success, List<Rate> rates) {
        this.success = success;
        this.rates = rates;
        this.count = (rates != null) ? rates.size() : 0;
    }

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public String getMessage() {
        return this.message;
    }

    public static class Rate {
        private String rateID;
        private String vehicleClass;
        private String availability;
        private String currencyCode;
        private double estimate;
        private double rateOnlyEstimate;
        private double dropCharge;
        private String distanceIncluded;
        private double liability;
        private boolean prePaid;

        // Getters and Setters

        public String getRateID() {
            return rateID;
        }

        public void setRateID(String rateID) {
            this.rateID = rateID;
        }

        public String getVehicleClass() {
            return vehicleClass;
        }

        public void setVehicleClass(String vehicleClass) {
            this.vehicleClass = vehicleClass;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public double getEstimate() {
            return estimate;
        }

        public void setEstimate(double estimate) {
            this.estimate = estimate;
        }

        public double getRateOnlyEstimate() {
            return rateOnlyEstimate;
        }

        public void setRateOnlyEstimate(double rateOnlyEstimate) {
            this.rateOnlyEstimate = rateOnlyEstimate;
        }

        public double getDropCharge() {
            return dropCharge;
        }

        public void setDropCharge(double dropCharge) {
            this.dropCharge = dropCharge;
        }

        public String getDistanceIncluded() {
            return distanceIncluded;
        }

        public void setDistanceIncluded(String distanceIncluded) {
            this.distanceIncluded = distanceIncluded;
        }

        public double getLiability() {
            return liability;
        }

        public void setLiability(double liability) {
            this.liability = liability;
        }

        public boolean isPrePaid() {
            return prePaid;
        }

        public void setPrePaid(boolean prePaid) {
            this.prePaid = prePaid;
        }
    }
}
