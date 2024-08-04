package com.secret.platform.customer;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @XmlElement(name = "RenterName")
    private RenterName renterName;

    @XmlElement(name = "Address")
    private Address address;

    private Long id;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RenterName {
        @XmlAttribute(name = "firstName")
        private String firstName;

        @XmlAttribute(name = "lastName")
        private String lastName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Address {
        @XmlElement(name = "Email")
        private String email;

        @XmlElement(name = "WorkTelephoneNumber")
        private String workTelephoneNumber;

        @XmlElement(name = "CellTelephoneNumber")
        private String cellTelephoneNumber;
    }
}
