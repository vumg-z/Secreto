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

        return resOptionsService.getOptions(resOptionsDTO);
    }
}
