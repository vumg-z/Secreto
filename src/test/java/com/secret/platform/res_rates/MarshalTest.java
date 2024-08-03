package com.secret.platform.res_rates;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

public class MarshalTest {

    public static void main(String[] args) {
        try {
            // Create and populate the response DTO
            ResRatesResponseDTO responseDTO = createResponseDTO();

            // Marshall the object to XML
            String xml = marshal(responseDTO);
            System.out.println("Marshalled XML:\n" + xml);

            // Unmarshal the XML back to an object
            ResRatesResponseDTO unmarshalledResponse = unmarshal(xml);
            System.out.println("Unmarshalled Object:\n" + unmarshalledResponse);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static ResRatesResponseDTO createResponseDTO() {
        ResRatesResponseDTO responseDTO = new ResRatesResponseDTO();
        responseDTO.setSuccess(true);
        responseDTO.setCount(1);

        ResRatesResponseDTO.Rate rate = new ResRatesResponseDTO.Rate();
        rate.setRateID("23101118372129XXAR");
        rate.setClassCode("XXAR, MCAR");
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

        return responseDTO;
    }

    private static String marshal(ResRatesResponseDTO responseDTO) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesResponseDTO.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // Optional: for pretty-print

        StringWriter sw = new StringWriter();
        marshaller.marshal(responseDTO, sw);
        return sw.toString();
    }

    private static ResRatesResponseDTO unmarshal(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ResRatesResponseDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (ResRatesResponseDTO) unmarshaller.unmarshal(new StringReader(xml));
    }
}
