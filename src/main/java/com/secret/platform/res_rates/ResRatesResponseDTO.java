package com.secret.platform.res_rates;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResRatesResponseDTO {

    @XmlElement(name = "success")
    private boolean success;

    @XmlElement(name = "Count")
    private int count;

    @XmlElementWrapper(name = "Rate")
    @XmlElement(name = "Rate")
    private List<Rate> rates = new ArrayList<>();

    // Getters and setters

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void addRate(Rate rate) {
        this.rates.add(rate);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Rate {
        @XmlElement(name = "RateID")
        private String rateID;

        @XmlElement(name = "Class")
        private String classCode;

        @XmlElement(name = "Availability")
        private String availability;

        @XmlElement(name = "CurrencyCode")
        private String currencyCode;

        @XmlElement(name = "Estimate")
        private double estimate;

        @XmlElement(name = "RateOnlyEstimate")
        private double rateOnlyEstimate;

        @XmlElement(name = "DropCharge")
        private DropCharge dropCharge;

        @XmlElement(name = "Distance")
        private Distance distance;

        @XmlElement(name = "Liability")
        private double liability;

        @XmlElement(name = "PrePaid")
        private boolean prePaid;

        @XmlElement(name = "AlternateRateProduct")
        private List<AlternateRateProduct> alternateRateProducts = new ArrayList<>();

        // Getters and setters

        public String getRateID() {
            return rateID;
        }

        public void setRateID(String rateID) {
            this.rateID = rateID;
        }

        public String getClassCode() {
            return classCode;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
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

        public DropCharge getDropCharge() {
            return dropCharge;
        }

        public void setDropCharge(DropCharge dropCharge) {
            this.dropCharge = dropCharge;
        }

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
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

        public List<AlternateRateProduct> getAlternateRateProducts() {
            return alternateRateProducts;
        }

        public void setAlternateRateProducts(List<AlternateRateProduct> alternateRateProducts) {
            this.alternateRateProducts = alternateRateProducts;
        }

        // Inner classes for DropCharge and Distance

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class DropCharge {
            @XmlAttribute(name = "responsibility")
            private String responsibility;

            @XmlValue
            private double amount;

            // Getters and setters
            public String getResponsibility() {
                return responsibility;
            }

            public void setResponsibility(String responsibility) {
                this.responsibility = responsibility;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Distance {
            @XmlElement(name = "Included")
            private String included;

            // Getters and setters
            public String getIncluded() {
                return included;
            }

            public void setIncluded(String included) {
                this.included = included;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class AlternateRateProduct {
            @XmlValue
            private String value;

            // Getters and setters
            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
