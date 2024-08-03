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

    private final ResEstimatesService resEstimatesService;
    private static final Logger logger = LoggerFactory.getLogger(ResEstimatesController.class);

    @Autowired
    public ResEstimatesController(ResEstimatesService resEstimatesService) {
        this.resEstimatesService = resEstimatesService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResEstimatesResponseDTO getEstimates(@RequestBody String requestXml) throws JAXBException {
        // Log the received XML data
        logger.info("Received request XML: {}", requestXml);

        JAXBContext jaxbContext = JAXBContext.newInstance(ResEstimatesDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResEstimatesDTO resEstimatesDTO = (ResEstimatesDTO) unmarshaller.unmarshal(new StringReader(requestXml));

        return resEstimatesService.getEstimates(resEstimatesDTO);
    }
}
