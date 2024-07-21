package com.secret.platform.res_rates;

import com.secret.platform.res_rates.ResRatesController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResRatesController.class)
public class ResRatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResRatesService resRatesService;

    @InjectMocks
    private ResRatesController resRatesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRates() throws Exception {
        // Prepare the request XML
        ResRatesDTO resRatesDTO = new ResRatesDTO();
        resRatesDTO.setCorpRateID("MYWEB1");

        ResRatesDTO.PickupDTO pickup = new ResRatesDTO.PickupDTO();
        pickup.setLocationCode("GDLMY1");
        pickup.setDateTime(LocalDateTime.of(2024, 7, 18, 10, 0));
        resRatesDTO.setPickup(pickup);

        ResRatesDTO.ReturnDTO returnInfo = new ResRatesDTO.ReturnDTO();
        returnInfo.setLocationCode("GDLMY1");
        returnInfo.setDateTime(LocalDateTime.of(2024, 7, 20, 10, 0));
        resRatesDTO.setReturnInfo(returnInfo);

        resRatesDTO.setCountryCode("US");
        resRatesDTO.setEstimateType(3);

        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(resRatesDTO, sw);
        String requestXml = sw.toString();

        // Prepare the response
        ResRatesResponseDTO responseDTO = new ResRatesResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setCount(1);
        ResRatesResponseDTO.Rate rate = new ResRatesResponseDTO.Rate();
        rate.setRateID("23101118372129XXAR");
        rate.setClassCode("XXAR");
        rate.setAvailability("Available");
        rate.setCurrencyCode("USD");
        rate.setEstimate(72.88);
        rate.setRateOnlyEstimate(72.88);
        ResRatesResponseDTO.Rate.DropCharge dropCharge = new ResRatesResponseDTO.Rate.DropCharge();
        dropCharge.setResponsibility("renter");
        dropCharge.setAmount(0.00);
        rate.setDropCharge(dropCharge);
        ResRatesResponseDTO.Rate.Distance distance = new ResRatesResponseDTO.Rate.Distance();
        distance.setIncluded("unlimited");
        rate.setDistance(distance);
        rate.setLiability(0);
        rate.setPrePaid(false);
        responseDTO.addRate(rate);

        when(resRatesService.getRates(any(ResRatesDTO.class))).thenReturn(responseDTO);

        // Perform the test
        mockMvc.perform(post("/api/res-rates")
                        .contentType(MediaType.APPLICATION_XML)
                        .content(requestXml)
                        .accept(MediaType.APPLICATION_XML))
                .andExpect(status().isOk())
                .andExpect(content().xml(marshalResponse(responseDTO)));
    }

    private String marshalResponse(ResRatesResponseDTO responseDTO) throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesResponseDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(responseDTO, sw);
        return sw.toString();
    }
}
