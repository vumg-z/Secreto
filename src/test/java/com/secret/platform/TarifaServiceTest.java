package com.secret.platform;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.corporateid.CorporateIDRepository;
import com.secret.platform.dto.ResRatesRequest;
import com.secret.platform.dto.ResRatesResponse;
import com.secret.platform.tarifa.Tarifa;
import com.secret.platform.tarifa.TarifaRepository;
import com.secret.platform.tarifa.TarifaService;
import com.secret.platform.vehicle_class.VehicleClass;
import com.secret.platform.vehicle_class.VehicleClassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarifaServiceTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @Mock
    private CorporateIDRepository corporateIDRepository;

    @Mock
    private VehicleClassRepository vehicleClassRepository;

    @InjectMocks
    private TarifaService tarifaService;

    private Tarifa tarifa1;
    private Tarifa tarifa2;
    private Tarifa tarifa3;
    private CorporateID corporateID;
    private VehicleClass vehicleClassEcar;
    private VehicleClass vehicleClassFcar;
    private VehicleClass vehicleClassMcar;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        corporateID = new CorporateID();
        corporateID.setId(1L);
        corporateID.setNombre("MYWEB1");

        vehicleClassEcar = new VehicleClass();
        vehicleClassEcar.setId(1L);
        vehicleClassEcar.setClassName("ECAR");
        vehicleClassEcar.setDescription("Economy Car");

        vehicleClassFcar = new VehicleClass();
        vehicleClassFcar.setId(2L);
        vehicleClassFcar.setClassName("FCAR");
        vehicleClassFcar.setDescription("Full-size Car");

        vehicleClassMcar = new VehicleClass();
        vehicleClassMcar.setId(3L);
        vehicleClassMcar.setClassName("MCAR");
        vehicleClassMcar.setDescription("Mid-size Car");

        tarifa1 = new Tarifa();
        tarifa1.setId(1L);
        tarifa1.setName("Tarifa1");
        tarifa1.setDescription("DescripcionTarifa1");
        tarifa1.setCorporateID(corporateID);
        tarifa1.setVehicleClass(vehicleClassEcar);
        tarifa1.setCodigoMoneda("USD");
        tarifa1.setEstimate(50.0);
        tarifa1.setRateOnlyEstimate(45.0);
        tarifa1.setDropCharge(10.0);
        tarifa1.setDistance(100.0);
        tarifa1.setPrepaid(false);
        tarifa1.setAvailability(true);

        tarifa2 = new Tarifa();
        tarifa2.setId(2L);
        tarifa2.setName("Tarifa2");
        tarifa2.setDescription("DescripcionTarifa2");
        tarifa2.setCorporateID(corporateID);
        tarifa2.setVehicleClass(vehicleClassFcar);
        tarifa2.setCodigoMoneda("USD");
        tarifa2.setEstimate(75.0);
        tarifa2.setRateOnlyEstimate(70.0);
        tarifa2.setDropCharge(15.0);
        tarifa2.setDistance(150.0);
        tarifa2.setPrepaid(false);
        tarifa2.setAvailability(true);

        tarifa3 = new Tarifa();
        tarifa3.setId(3L);
        tarifa3.setName("Tarifa3");
        tarifa3.setDescription("DescripcionTarifa3");
        tarifa3.setCorporateID(corporateID);
        tarifa3.setVehicleClass(vehicleClassMcar);
        tarifa3.setCodigoMoneda("USD");
        tarifa3.setEstimate(60.0);
        tarifa3.setRateOnlyEstimate(55.0);
        tarifa3.setDropCharge(12.0);
        tarifa3.setDistance(120.0);
        tarifa3.setPrepaid(false);
        tarifa3.setAvailability(true);

        when(corporateIDRepository.findByName("MYWEB1")).thenReturn(Optional.of(corporateID));
        when(vehicleClassRepository.findByClassName("ECAR")).thenReturn(Optional.of(vehicleClassEcar));
        when(vehicleClassRepository.findByClassName("FCAR")).thenReturn(Optional.of(vehicleClassFcar));
        when(vehicleClassRepository.findByClassName("MCAR")).thenReturn(Optional.of(vehicleClassMcar));
        when(tarifaRepository.findByCorporateID(String.valueOf(corporateID))).thenReturn(Arrays.asList(tarifa1, tarifa2, tarifa3));
    }

    @Test
    public void testGetRates() {
        ResRatesRequest request = new ResRatesRequest();
        request.setCorpRateID("MYWEB1");
        request.setPickupLocationCode("GDLMY1");
        request.setPickupDateTime(LocalDateTime.of(2024, 7, 1, 10, 0));
        request.setReturnLocationCode("GDLMY1");
        request.setReturnDateTime(LocalDateTime.of(2024, 7, 10, 10, 0));
        request.setCountryCode("US");

        System.out.println("Request:");
        System.out.println("  Pickup locationCode=" + request.getPickupLocationCode() + " dateTime=" + request.getPickupDateTime());
        System.out.println("  Return locationCode=" + request.getReturnLocationCode() + " dateTime=" + request.getReturnDateTime());
        System.out.println("  Source countryCode=" + request.getCountryCode());
        System.out.println("  CorpRateID=" + request.getCorpRateID());

        ResRatesResponse response = tarifaService.getRates(request);

        System.out.println("Response:");
        System.out.println("  success=" + response.isSuccess());
        System.out.println("  count=" + response.getCount());

        long days = ChronoUnit.DAYS.between(request.getPickupDateTime(), request.getReturnDateTime());
        System.out.println("  Rental days=" + days);

        for (ResRatesResponse.Rate rate : response.getRates()) {
            System.out.println("  Rate:");
            System.out.println("    RateID=" + rate.getRateID());
            System.out.println("    Class=" + rate.getVehicleClass());
            System.out.println("    Availability=" + rate.getAvailability());
            System.out.println("    CurrencyCode=" + rate.getCurrencyCode());
            System.out.println("    Estimate (per day)=" + rate.getEstimate() / days);
            System.out.println("    Total Estimate=" + rate.getEstimate());
            System.out.println("    RateOnlyEstimate (per day)=" + rate.getRateOnlyEstimate() / days);
            System.out.println("    Total RateOnlyEstimate=" + rate.getRateOnlyEstimate());
            System.out.println("    DropCharge=" + rate.getDropCharge());
            System.out.println("    DistanceIncluded=" + rate.getDistanceIncluded());
            System.out.println("    Liability=" + rate.getLiability());
            System.out.println("    PrePaid=" + rate.isPrePaid());
        }

        ResRatesResponse.Rate rate1 = response.getRates().get(0);
        assertEquals("1", rate1.getRateID());
        assertEquals("ECAR", rate1.getVehicleClass());
        assertEquals("Available", rate1.getAvailability());
        assertEquals("USD", rate1.getCurrencyCode());
        assertEquals(50.0 * days, rate1.getEstimate());
        assertEquals(45.0 * days, rate1.getRateOnlyEstimate());
        assertEquals(10.0, rate1.getDropCharge());
        assertEquals("unlimited", rate1.getDistanceIncluded());
        assertEquals(0, rate1.getLiability());
        assertFalse(rate1.isPrePaid());

        ResRatesResponse.Rate rate2 = response.getRates().get(1);
        assertEquals("2", rate2.getRateID());
        assertEquals("FCAR", rate2.getVehicleClass());
        assertEquals("Available", rate2.getAvailability());
        assertEquals("USD", rate2.getCurrencyCode());
        assertEquals(75.0 * days, rate2.getEstimate());
        assertEquals(70.0 * days, rate2.getRateOnlyEstimate());
        assertEquals(15.0, rate2.getDropCharge());
        assertEquals("unlimited", rate2.getDistanceIncluded());
        assertEquals(0, rate2.getLiability());
        assertFalse(rate2.isPrePaid());

        ResRatesResponse.Rate rate3 = response.getRates().get(2);
        assertEquals("3", rate3.getRateID());
        assertEquals("MCAR", rate3.getVehicleClass());
        assertEquals("Available", rate3.getAvailability());
        assertEquals("USD", rate3.getCurrencyCode());
        assertEquals(60.0 * days, rate3.getEstimate());
        assertEquals(55.0 * days, rate3.getRateOnlyEstimate());
        assertEquals(12.0, rate3.getDropCharge());
        assertEquals("unlimited", rate3.getDistanceIncluded());
        assertEquals(0, rate3.getLiability());
        assertFalse(rate3.isPrePaid());
    }
}
