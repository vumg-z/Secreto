package com.secret.platform.res_rates;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;

@XmlRootElement(name = "ResRates")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResRatesDTO {

    @XmlElement(name = "Pickup")
    private PickupDTO pickup;

    @XmlElement(name = "Return")
    private ReturnDTO returnInfo;

    @XmlElement(name = "Source")
    private String countryCode;

    @XmlElement(name = "CorpRateID")
    private String corpRateID;

    @XmlElement(name = "EstimateType")
    private int estimateType;

    // Getters and setters

    public PickupDTO getPickup() {
        return pickup;
    }

    public void setPickup(PickupDTO pickup) {
        this.pickup = pickup;
    }

    public ReturnDTO getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(ReturnDTO returnInfo) {
        this.returnInfo = returnInfo;
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

    public int getEstimateType() {
        return estimateType;
    }

    public void setEstimateType(int estimateType) {
        this.estimateType = estimateType;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PickupDTO {
        @XmlAttribute(name = "locationCode")
        private String locationCode;

        @XmlAttribute(name = "dateTime")
        @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
        private LocalDateTime dateTime;

        // Getters and setters

        public String getLocationCode() {
            return locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ReturnDTO {
        @XmlAttribute(name = "locationCode")
        private String locationCode;

        @XmlAttribute(name = "dateTime")
        @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
        private LocalDateTime dateTime;

        // Getters and setters

        public String getLocationCode() {
            return locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }
    }
}
