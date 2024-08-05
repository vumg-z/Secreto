package com.secret.platform.reservation;

import ch.qos.logback.core.status.Status;
import com.secret.platform.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceNumber;
    private String version;
    private boolean confirmAvailability;
    private String pickupLocationCode;
    private LocalDateTime pickupDateTime;
    private String returnLocationCode;
    private LocalDateTime returnDateTime;
    private String sourceCountryCode;
    private String confirmationNumber;
    private String vehicleClassCode;
    private String currencyCode;
    private String corporateRateID;
    private double totalCostAmount;
    private String localPhone;

    private String reservationNotes;

    private String productCode;
    private double dayRate;
    private double weekRate;
    private double monthRate;
    private double xdayRate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ElementCollection
    @CollectionTable(name = "reservation_options", joinColumns = @JoinColumn(name = "reservation_id"))
    private List<ReservationOption> options;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class ReservationOption {
        private String code;
        private int quantity;
    }

    public enum Status {
        ACTIVE,
        CANCELED
    }
}
