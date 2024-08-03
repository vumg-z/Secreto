package com.secret.platform.resOptions;

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
@RequestMapping("/api/res-options")
public class ResOptionsController {

    private final ResOptionsService resOptionsService;
    private static final Logger logger = LoggerFactory.getLogger(ResOptionsController.class);

    @Autowired
    public ResOptionsController(ResOptionsService resOptionsService) {
        this.resOptionsService = resOptionsService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResOptionsResponseDTO getOptions(@RequestBody String requestXml) throws JAXBException {
        // Log the received XML data
        logger.info("Received request XML: {}", requestXml);

        JAXBContext jaxbContext = JAXBContext.newInstance(ResOptionsDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResOptionsDTO resOptionsDTO = (ResOptionsDTO) unmarshaller.unmarshal(new StringReader(requestXml));

        String currency;
        if (resOptionsDTO.getSource() != null) {
            switch (resOptionsDTO.getSource()) {
                case "US":
                    currency = "USD";
                    break;
                case "MX":
                    currency = "MXN";
                    break;
                default:
                    currency = "USD"; // Default to USD if the source is not recognized
                    break;
            }
            logger.info("Set currency based on source {}: {}", resOptionsDTO.getSource(), currency);
        } else {
            currency = "USD"; // Default to USD if source is not provided
            logger.info("No source provided, defaulting currency to USD");
        }

        return resOptionsService.getOptions(resOptionsDTO, currency);
    }
}
