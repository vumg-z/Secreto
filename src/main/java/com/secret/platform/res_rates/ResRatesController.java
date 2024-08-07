package com.secret.platform.res_rates;

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
@RequestMapping("/api/res-rates")
public class ResRatesController {

    private final ResRatesService resRatesService;
    private static final Logger logger = LoggerFactory.getLogger(ResRatesController.class);

    @Autowired
    public ResRatesController(ResRatesService resRatesService) {
        this.resRatesService = resRatesService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResRatesResponseDTO getRates(@RequestBody String requestXml) throws JAXBException {
        // Log the received XML data
        logger.info("Received request XML: {}", requestXml);

        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResRatesDTO resRatesDTO = (ResRatesDTO) unmarshaller.unmarshal(new StringReader(requestXml));

        return resRatesService.getRates(resRatesDTO);
    }
}
