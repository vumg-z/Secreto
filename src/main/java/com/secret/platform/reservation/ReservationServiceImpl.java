package com.secret.platform.reservation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Override
    public ReservationResponseDTO processReservation(CreateReservationRequestDTO reservationRequestDTO, String currency) {
        logger.info("Processing reservation for reference number: {}", reservationRequestDTO.getReferenceNumber());

        // Extract data from the DTO for processing
        String pickupLocation = reservationRequestDTO.getNewReservationRequest().getPickup().getLocationCode();
        String returnLocation = reservationRequestDTO.getNewReservationRequest().getReturnInfo().getLocationCode();
        String vehicleClass = reservationRequestDTO.getNewReservationRequest().getVehicle().getClassCode();

        logger.info("Pickup location: {}, Return location: {}, Vehicle class: {}", pickupLocation, returnLocation, vehicleClass);

        // Here, implement the business logic for processing the reservation
        // For example, checking availability, calculating costs, etc.

        // Use the currency parameter as needed
        double totalCost = calculateTotalCost(reservationRequestDTO, currency);
        logger.info("Total cost calculated: {} {}", totalCost, currency);

        // Create a response DTO based on the processing result
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setRegardingReferenceNumber(reservationRequestDTO.getReferenceNumber());
        responseDTO.setVersion("2.3101"); // Example version number
        responseDTO.setWebxgId(generateWebxgId()); // Generate or obtain a unique webxg_id

        // Populate NewReservationResponse
        ReservationResponseDTO.NewReservationResponse newReservationResponse = new ReservationResponseDTO.NewReservationResponse();
        newReservationResponse.setSuccess(true); // Set based on business logic
        newReservationResponse.setReservationStatus("Available"); // Set based on business logic
        newReservationResponse.setReservationNumber(generateReservationNumber()); // Generate or obtain reservation number
        newReservationResponse.setReservationConfirmed(true); // Set based on business logic

        responseDTO.setNewReservationResponse(newReservationResponse);

        return responseDTO;
    }

    private double calculateTotalCost(CreateReservationRequestDTO reservationRequestDTO, String currency) {
        // Implement cost calculation logic based on reservation details and currency
        // This is a placeholder for the actual calculation logic
        double baseRate = reservationRequestDTO.getNewReservationRequest().getQuotedRate().getTotalCost().getAmount();

        // Example: Adjust cost based on currency (this logic can be more complex)
        if ("MXN".equals(currency)) {
            baseRate *= 18.0; // Hypothetical conversion rate for example
        }

        // Additional logic to calculate cost can be added here

        return baseRate;
    }

    private String generateWebxgId() {
        // Implement logic to generate or retrieve a unique webxg_id
        return "a4ed33fc-529f-11ef-902d-16d0608c1743"; // Placeholder
    }

    private String generateReservationNumber() {
        // Implement logic to generate or retrieve a reservation number
        return "033423"; // Placeholder
    }
}
