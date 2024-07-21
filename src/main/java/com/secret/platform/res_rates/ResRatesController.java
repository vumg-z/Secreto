package com.secret.platform.res_rates;

import com.secret.platform.res_rates.ResRatesDTO;
import com.secret.platform.res_rates.ResRatesResponseDTO;
import com.secret.platform.res_rates.ResRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

@RestController
@RequestMapping("/api/res-rates")
public class ResRatesController {

    private final ResRatesService resRatesService;

    @Autowired
    public ResRatesController(ResRatesService resRatesService) {
        this.resRatesService = resRatesService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResRatesResponseDTO getRates(@RequestBody String requestXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ResRatesDTO resRatesDTO = (ResRatesDTO) unmarshaller.unmarshal(new StringReader(requestXml));

        return resRatesService.getRates(resRatesDTO);
    }
}
