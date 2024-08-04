package com.secret.platform.reservation;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationServiceImpl reservationService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ReservationResponseDTO createReservation(@RequestBody String requestXml) throws JAXBException {
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
        return reservationService.processReservation(reservationRequestDTO, currency);
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
}
