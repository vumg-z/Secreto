package com.secret.platform.reservation;

import com.secret.platform.resEstimates.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationRequestDTO {

    @XmlAttribute(name = "referenceNumber")
    private String referenceNumber;

    @XmlAttribute(name = "version")
    private String version;

    @XmlElement(name = "NewReservationRequest")
    private NewReservationRequest newReservationRequest;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class NewReservationRequest {

        @XmlAttribute(name = "confirmAvailability")
        private boolean confirmAvailability;

        @XmlElement(name = "Pickup")
        private Pickup pickup;

        @XmlElement(name = "Return")
        private Return returnInfo;

        @XmlElement(name = "Source")
        private Source source;

        @XmlElement(name = "Vehicle")
        private Vehicle vehicle;

        @XmlElement(name = "Renter")
        private Renter renter;

        @XmlElement(name = "QuotedRate")
        private QuotedRate quotedRate;

        @XmlElement(name = "LocalPhone")
        private String localPhone;

        @XmlElement(name = "ReservationMainNote")
        private String reservationMainNote;

        @XmlElement(name = "ReservationNotes")
        private ReservationNotes reservationNotes;

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
        public static class Source {
            @XmlAttribute(name = "confirmationNumber")
            private String confirmationNumber;

            @XmlAttribute(name = "countryCode")
            private String countryCode;

            @XmlAttribute(name = "code")
            private String code;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Vehicle {
            @XmlAttribute(name = "classCode")
            private String classCode;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class Renter {
            @XmlElement(name = "RenterName")
            private RenterName renterName;

            @XmlElement(name = "Address")
            private Address address;

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

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class QuotedRate {
            @XmlAttribute(name = "productCode")
            private String productCode;

            @XmlAttribute(name = "rateID")
            private String rateID;

            @XmlAttribute(name = "classCode")
            private String classCode;

            @XmlAttribute(name = "currencyCode")
            private String currencyCode;

            @XmlAttribute(name = "corporateRateID")
            private String corporateRateID;

            @XmlAttribute(name = "day_rate")
            private double dayRate;

            @XmlAttribute(name = "week_rate")
            private double weekRate;

            @XmlAttribute(name = "month_rate")
            private double monthRate;

            @XmlAttribute(name = "xday_rate")
            private double xDayRate;

            @XmlElement(name = "TotalCost")
            private TotalCost totalCost;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @XmlAccessorType(XmlAccessType.FIELD)
            public static class TotalCost {
                @XmlAttribute(name = "amount")
                private double amount;

                @XmlAttribute(name = "currencyCode")
                private String currencyCode;
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @XmlAccessorType(XmlAccessType.FIELD)
        public static class ReservationNotes {
            @XmlElement(name = "Note")
            private Note note;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            @XmlAccessorType(XmlAccessType.FIELD)
            public static class Note {
                @XmlAttribute(name = "Cnf")
                private String confirmation;
            }
        }
    }
}