package com.secret.platform.resEstimates;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@XmlRootElement(name = "ResEstimate")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ResEstimatesDTO {

    @XmlAttribute(name = "referenceNumber")
    private String referenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlElement(name = "Pickup")
    private Pickup pickup;

    @XmlElement(name = "Return")
    private Return returnInfo;

    @XmlElement(name = "QuotedRate")
    private QuotedRate quotedRate;

    @XmlElement(name = "Source")
    private String source;

    @XmlElement(name = "Option")
    private List<Option> options;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Pickup {
        @XmlAttribute(name = "locationCode")
        private String locationCode;

        @XmlAttribute(name = "dateTime")
        @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
        private LocalDateTime dateTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Return {
        @XmlAttribute(name = "locationCode")
        private String locationCode;

        @XmlAttribute(name = "dateTime")
        @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
        private LocalDateTime dateTime;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class QuotedRate {
        @XmlAttribute(name = "rateID")
        private String rateID;

        @XmlAttribute(name = "corporateRateID")
        private String corporateRateID;

        @XmlAttribute(name = "classCode")
        private String classCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Option {
        @XmlElement(name = "Code")
        private String code;

        @XmlElement(name = "Qty")
        private String quantity;
    }
}
