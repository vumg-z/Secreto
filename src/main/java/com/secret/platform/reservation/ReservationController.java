package com.secret.platform.reservation;

import com.secret.platform.exception.CustomerNotFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationServiceImpl reservationService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ReservationResponseDTO createReservation(@RequestBody String requestXml) {
        try {
            // Log the received XML data
            logger.info("Received reservation request XML: {}", requestXml);

            // Unmarshal the XML to a CreateReservationRequestDTO object
            JAXBContext jaxbContext = JAXBContext.newInstance(CreateReservationRequestDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            CreateReservationRequestDTO reservationRequestDTO = (CreateReservationRequestDTO) unmarshaller.unmarshal(new StringReader(requestXml));

            // Extract relevant information for logging or further processing
            String referenceNumber = reservationRequestDTO.getReferenceNumber();
            logger.info("Processing reservation with reference number: {}", referenceNumber);

            // Determine currency based on the Source field
            String currency = determineCurrency(reservationRequestDTO.getNewReservationRequest().getSource().getCountryCode());
            logger.info("Determined currency: {}", currency);

            // Pass the DTO and currency to the service for processing
            return reservationService.saveReservation(reservationRequestDTO, currency);

        } catch (JAXBException e) {
            logger.error("Failed to parse XML: {}", e.getMessage());
            throw new RuntimeException("Invalid XML format.", e);
        } catch (Exception e) {
            logger.error("Error creating reservation: {}", e.getMessage());
            throw new RuntimeException("Failed to create reservation.", e);
        }
    }

    private String determineCurrency(String countryCode) {
        if (countryCode != null) {
            switch (countryCode) {
                case "US":
                    return "USD";
                case "MX":
                    return "MXN";
                default:
                    return "USD"; // Default to USD if source is not recognized
            }
        }
        logger.info("No country code provided, defaulting currency to USD");
        return "USD";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> getReservations(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber) {
        logger.info("Searching for reservations with provided criteria");

        try {
            if (firstName != null && lastName != null) {
                // Search by customer name
                return reservationService.getReservationsByCustomerName(firstName, lastName);
            } else if (email != null) {
                // Search by customer email
                return reservationService.getReservationsByCustomerEmail(email);
            } else if (phoneNumber != null) {
                // Search by customer phone number
                return reservationService.getReservationsByCustomerPhoneNumber(phoneNumber);
            } else {
                // Return all reservations if no criteria are provided
                return reservationService.getAllReservations();
            }
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found: {}", e.getMessage());
            throw new RuntimeException("Customer not found.", e);
        } catch (Exception e) {
            logger.error("Error retrieving reservations: {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve reservations.", e);
        }
    }


    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ReservationResponseDTO cancelReservation(@RequestBody String requestXml) {
        try {
            // Log the received XML data
            logger.info("Received cancellation request XML: {}", requestXml);

            // Unmarshal the XML to a CancelReservationRequestDTO object
            JAXBContext jaxbContext = JAXBContext.newInstance(CancelReservationRequestDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            CancelReservationRequestDTO cancelRequestDTO = (CancelReservationRequestDTO) unmarshaller.unmarshal(new StringReader(requestXml));

            // Extract relevant information for logging or further processing
            String reservationNumber = cancelRequestDTO.getCancelReservationRequest().getReservationNumber();
            logger.info("Processing cancellation for reservation number: {}", reservationNumber);

            // Call the reservation service to cancel the reservation
            reservationService.cancelReservation(cancelRequestDTO);

            // Return a successful response
            ReservationResponseDTO responseDTO = new ReservationResponseDTO();
            responseDTO.setRegardingReferenceNumber(cancelRequestDTO.getReferenceNumber());
            responseDTO.setVersion(cancelRequestDTO.getVersion());

            ReservationResponseDTO.NewReservationResponse newReservationResponse = new ReservationResponseDTO.NewReservationResponse();
            newReservationResponse.setSuccess(true);
            newReservationResponse.setReservationStatus("Canceled");
            newReservationResponse.setReservationConfirmed(false);

            responseDTO.setNewReservationResponse(newReservationResponse);
            return responseDTO;

        } catch (JAXBException e) {
            logger.error("Failed to parse XML: {}", e.getMessage());
            throw new RuntimeException("Invalid XML format.", e);
        } catch (Exception e) {
            logger.error("Error canceling reservation: {}", e.getMessage());
            throw new RuntimeException("Failed to cancel reservation.", e);
        }
    }
}
