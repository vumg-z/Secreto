package com.secret.platform.resOptions;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
public class ResOptionsResponseDTO {

    @XmlAttribute(name = "regardingReferenceNumber")
    private String regardingReferenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "webxg_id")
    private String webxgId;

    @XmlElement(name = "ResOptions")
    private ResOptions resOptions;

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ResOptions {
        @XmlAttribute(name = "success")
        private boolean success;

        @XmlElement(name = "Option")
        private List<Option> options;
    }

    @Data
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Option {
        @XmlAttribute(name = "code")
        private String code;

        @XmlAttribute(name = "desc")
        private String desc;

        @XmlAttribute(name = "rate")
        private double rate;

        @XmlAttribute(name = "week_rate")
        private double weekRate;

        @XmlAttribute(name = "month_rate")
        private double monthRate;

        @XmlAttribute(name = "xday_rate")
        private double xdayRate;

        @XmlAttribute(name = "rate_type")
        private String rateType;

        @XmlAttribute(name = "available")
        private boolean available;

        @XmlAttribute(name = "liability")
        private double liability;
    }
}
