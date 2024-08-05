package com.secret.platform.reservation;

import com.secret.platform.customer.CustomerDTO;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelReservationRequestDTO {

    @XmlAttribute(name = "referenceNumber")
    private String referenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlElement(name = "CancelReservationRequest")
    private CancelReservationRequest cancelReservationRequest;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CancelReservationRequest {

        @XmlAttribute(name = "reservationNumber")
        private String reservationNumber;

        @XmlElement(name = "Customer")
        private CustomerDTO customer;
    }
}
