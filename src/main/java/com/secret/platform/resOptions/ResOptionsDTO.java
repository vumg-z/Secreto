package com.secret.platform.resOptions;

import com.secret.platform.resEstimates.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@XmlRootElement(name = "ResOptions")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ResOptionsDTO {

    @XmlElement(name = "Pickup")
    private Pickup pickup;

    @XmlElement(name = "Return")
    private Return returnInfo;

    @XmlElement(name = "QuotedRate")
    private QuotedRate quotedRate;

    @XmlElement(name = "Vehicle")
    private Vehicle vehicle;

    @Data
    @NoArgsConstructor
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
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class QuotedRate {
        @XmlAttribute(name = "corporateRateID")
        private String corporateRateID;

        @XmlAttribute(name = "classCode")
        private String classCode;
    }

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Vehicle {
        @XmlAttribute(name = "classCode")
        private String classCode;
    }
}
