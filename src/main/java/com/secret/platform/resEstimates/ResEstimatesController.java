package com.secret.platform.resEstimates;

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
@RequestMapping("/api/res-estimates")
public class ResEstimatesController {

    private static final Logger logger = LoggerFactory.getLogger(ResEstimatesController.class);

    @Autowired
    private ResEstimatesService resEstimatesService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResEstimatesResponseDTO getEstimates(@RequestBody String requestXml) throws JAXBException {
        // Log the received XML data
        logger.info("Received request XML: {}", requestXml);

        // Unmarshal the XML to a ResEstimatesDTO object
        JAXBContext jaxbContext = JAXBContext.newInstance(ResEstimatesDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResEstimatesDTO resEstimatesDTO = (ResEstimatesDTO) unmarshaller.unmarshal(new StringReader(requestXml));

        // Determine currency based on the Source field
        String currency = determineCurrency(resEstimatesDTO.getSource());
        logger.info("Determined currency: {}", currency);

        // Pass the DTO and currency to the service for processing
        return resEstimatesService.getEstimates(resEstimatesDTO, currency);
    }

    private String determineCurrency(String source) {
        if (source != null) {
            switch (source) {
                case "US":
                    return "USD";
                case "MX":
                    return "MXN";
                default:
                    return "USD"; // Default to USD if source is not recognized
            }
        }
        logger.info("No source provided, defaulting currency to USD");
        return "USD";
    }
}
