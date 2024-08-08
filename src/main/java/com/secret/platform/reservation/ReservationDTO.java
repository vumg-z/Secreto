package com.secret.platform.reservation;

import com.secret.platform.customer.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
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

    private CustomerDTO customer;
    private List<ReservationOptionDTO> options;
    private String status;
}
