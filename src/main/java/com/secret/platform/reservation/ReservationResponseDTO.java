package com.secret.platform.reservation;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO {

    @XmlAttribute(name = "regardingReferenceNumber")
    private String regardingReferenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "webxg_id")
    private String webxgId;

    @XmlElement(name = "NewReservationResponse")
    private NewReservationResponse newReservationResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class NewReservationResponse {
        @XmlAttribute(name = "success")
        private boolean success;

        @XmlAttribute(name = "reservationStatus")
        private String reservationStatus;

        @XmlAttribute(name = "reservationNumber")
        private String reservationNumber;

        @XmlAttribute(name = "reservationConfirmed")
        private boolean reservationConfirmed;
    }
}
