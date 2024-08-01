package com.secret.platform.resEstimates;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ResEstimatesResponseDTO {

    @XmlAttribute(name = "regardingReferenceNumber")
    private String regardingReferenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "webxg_id")
    private String webxgId;

    @XmlElement(name = "ResEstimate")
    private ResEstimate resEstimate;

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ResEstimate {
        @XmlAttribute(name = "success")
        private boolean success;

        @XmlElement(name = "RenterEstimate")
        private RenterEstimate renterEstimate;
    }

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RenterEstimate {
        @XmlAttribute(name = "total")
        private String total;

        @XmlAttribute(name = "includedDistance")
        private String includedDistance;

        @XmlAttribute(name = "currencyCode")
        private String currencyCode;

        @XmlElement(name = "Charge")
        private List<Charge> charges;
    }

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Charge {
        @XmlAttribute(name = "code")
        private String code;

        @XmlAttribute(name = "desc")
        private String description;

        @XmlAttribute(name = "quantity")
        private String quantity;

        @XmlAttribute(name = "total")
        private String total;
    }
}
