package com.secret.platform;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.corporateid.CorporateIDRepository;
import com.secret.platform.dto.ResRatesRequest;
import com.secret.platform.dto.ResRatesResponse;
import com.secret.platform.productos.Productos;
import com.secret.platform.productos.PaqueteProductosExtras;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TarifaServiceDifferentLocationsTest {

    @Mock
    private TarifaRepository tarifaRepository;

    @Mock
    private CorporateIDRepository corporateIDRepository;

    @Mock
    private VehicleClassRepository vehicleClassRepository;

    @InjectMocks
    private TarifaService tarifaService;

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
        corporateID.setIva(16.0);
        corporateID.setDiscount(5.0);

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

        // Setup Productos
        Productos producto1 = new Productos();
        producto1.setId(1L);
        producto1.setNombre("Product1");
        producto1.setCosto(5.0);
        producto1.setObligatorio(true);

        Set<Productos> productosObligatorios = new HashSet<>();
        productosObligatorios.add(producto1);

        // Setup PaqueteProductosExtras
        PaqueteProductosExtras paquete = new PaqueteProductosExtras();
        paquete.setId(1L);
        paquete.setNombre("Package1");
        paquete.setDiscount(10.0);

        Set<PaqueteProductosExtras> paqueteProductosExtras = new HashSet<>();
        paqueteProductosExtras.add(paquete);
        corporateID.setPaqueteProductosExtras(paqueteProductosExtras);

        when(corporateIDRepository.findByName("MYWEB1")).thenReturn(Optional.of(corporateID));
        when(vehicleClassRepository.findByClassName("ECAR")).thenReturn(Optional.of(vehicleClassEcar));
        when(vehicleClassRepository.findByClassName("FCAR")).thenReturn(Optional.of(vehicleClassFcar));
        when(vehicleClassRepository.findByClassName("MCAR")).thenReturn(Optional.of(vehicleClassMcar));
    }

    @Test
    public void testGetRatesByDifferentLocations() {
        // Setup location-specific Tarifas for San Jose Del Cabo
        Tarifa tarifaSJD1 = new Tarifa();
        tarifaSJD1.setId(4L);
        tarifaSJD1.setName("TarifaSJD1");
        tarifaSJD1.setDescription("Tarifa for San Jose Del Cabo");
        tarifaSJD1.setCorporateID(corporateID);
        tarifaSJD1.setVehicleClass(vehicleClassEcar);
        tarifaSJD1.setCodigoMoneda("USD");
        tarifaSJD1.setRateOnlyEstimate(50.0);
        tarifaSJD1.setDropCharge(11.0);
        tarifaSJD1.setDistance(110.0);
        tarifaSJD1.setPrepaid(true);
        tarifaSJD1.setAvailability(true);
        tarifaSJD1.setRateSet("SJD_RATE_SET");
        tarifaSJD1.setLocationCode("SJDMY1");
        tarifaSJD1.setProductosObligatorios(new HashSet<>()); // Assuming no obligatory products for simplicity
        tarifaSJD1.setEstimate(tarifaService.calculateEstimate(tarifaSJD1));

        Tarifa tarifaSJD2 = new Tarifa();
        tarifaSJD2.setId(5L);
        tarifaSJD2.setName("TarifaSJD2");
        tarifaSJD2.setDescription("Tarifa for San Jose Del Cabo");
        tarifaSJD2.setCorporateID(corporateID);
        tarifaSJD2.setVehicleClass(vehicleClassFcar);
        tarifaSJD2.setCodigoMoneda("USD");
        tarifaSJD2.setRateOnlyEstimate(75.0);
        tarifaSJD2.setDropCharge(12.0);
        tarifaSJD2.setDistance(120.0);
        tarifaSJD2.setPrepaid(true);
        tarifaSJD2.setAvailability(true);
        tarifaSJD2.setRateSet("SJD_RATE_SET");
        tarifaSJD2.setLocationCode("SJDMY1");
        tarifaSJD2.setProductosObligatorios(new HashSet<>()); // Assuming no obligatory products for simplicity
        tarifaSJD2.setEstimate(tarifaService.calculateEstimate(tarifaSJD2));

        Tarifa tarifaSJD3 = new Tarifa();
        tarifaSJD3.setId(6L);
        tarifaSJD3.setName("TarifaSJD3");
        tarifaSJD3.setDescription("Tarifa for San Jose Del Cabo");
        tarifaSJD3.setCorporateID(corporateID);
        tarifaSJD3.setVehicleClass(vehicleClassMcar);
        tarifaSJD3.setCodigoMoneda("USD");
        tarifaSJD3.setRateOnlyEstimate(60.0);
        tarifaSJD3.setDropCharge(13.0);
        tarifaSJD3.setDistance(130.0);
        tarifaSJD3.setPrepaid(true);
        tarifaSJD3.setAvailability(true);
        tarifaSJD3.setRateSet("SJD_RATE_SET");
        tarifaSJD3.setLocationCode("SJDMY1");
        tarifaSJD3.setProductosObligatorios(new HashSet<>()); // Assuming no obligatory products for simplicity
        tarifaSJD3.setEstimate(tarifaService.calculateEstimate(tarifaSJD3));

        // Setup location-specific Tarifas for another location (e.g., Los Cabos)
        Tarifa tarifaCabo1 = new Tarifa();
        tarifaCabo1.setId(7L);
        tarifaCabo1.setName("TarifaCabo1");
        tarifaCabo1.setDescription("Tarifa for Los Cabos");
        tarifaCabo1.setCorporateID(corporateID);
        tarifaCabo1.setVehicleClass(vehicleClassEcar);
        tarifaCabo1.setCodigoMoneda("USD");
        tarifaCabo1.setRateOnlyEstimate(55.0);
        tarifaCabo1.setDropCharge(10.0);
        tarifaCabo1.setDistance(100.0);
        tarifaCabo1.setPrepaid(true);
        tarifaCabo1.setAvailability(true);
        tarifaCabo1.setRateSet("CABO_RATE_SET");
        tarifaCabo1.setLocationCode("CABOMY1");
        tarifaCabo1.setProductosObligatorios(new HashSet<>()); // Assuming no obligatory products for simplicity
        tarifaCabo1.setEstimate(tarifaService.calculateEstimate(tarifaCabo1));

        Tarifa tarifaCabo2 = new Tarifa();
        tarifaCabo2.setId(8L);
        tarifaCabo2.setName("TarifaCabo2");
        tarifaCabo2.setDescription("Tarifa for Los Cabos");
        tarifaCabo2.setCorporateID(corporateID);
        tarifaCabo2.setVehicleClass(vehicleClassFcar);
        tarifaCabo2.setCodigoMoneda("USD");
        tarifaCabo2.setRateOnlyEstimate(80.0);
        tarifaCabo2.setDropCharge(11.0);
        tarifaCabo2.setDistance(120.0);
        tarifaCabo2.setPrepaid(true);
        tarifaCabo2.setAvailability(true);
        tarifaCabo2.setRateSet("CABO_RATE_SET");
        tarifaCabo2.setLocationCode("CABOMY1");
        tarifaCabo2.setProductosObligatorios(new HashSet<>()); // Assuming no obligatory products for simplicity
        tarifaCabo2.setEstimate(tarifaService.calculateEstimate(tarifaCabo2));

        // Mock repository response for the new locations
        when(tarifaRepository.findByRateSetAndLocationCode("SJD_RATE_SET", "SJDMY1"))
                .thenReturn(Arrays.asList(tarifaSJD1, tarifaSJD2, tarifaSJD3));
        when(tarifaRepository.findByRateSetAndLocationCode("CABO_RATE_SET", "CABOMY1"))
                .thenReturn(Arrays.asList(tarifaCabo1, tarifaCabo2));

        // Request for rates at the San Jose Del Cabo location
        ResRatesRequest requestSJD = new ResRatesRequest();
        requestSJD.setCorpRateID("MYWEB1");
        requestSJD.setPickupLocationCode("SJDMY1");
        requestSJD.setPickupDateTime(LocalDateTime.of(2024, 7, 19, 10, 0));
        requestSJD.setReturnLocationCode("SJDMY1");
        requestSJD.setReturnDateTime(LocalDateTime.of(2024, 7, 21, 10, 0));
        requestSJD.setCountryCode("US");
        requestSJD.setRateSet("SJD_RATE_SET");  // Set the rateSet

        // Execute the test for San Jose Del Cabo location
        ResRatesResponse responseSJD = tarifaService.getRates(requestSJD, "SJD_RATE_SET");

        // Validate the response for San Jose Del Cabo location
        assertNotNull(responseSJD);
        assertTrue(responseSJD.isSuccess());
        assertNotNull(responseSJD.getRates());
        assertFalse(responseSJD.getRates().isEmpty());
        assertEquals(3, responseSJD.getRates().size());

        // Print results to verify correct behavior for San Jose Del Cabo location
        responseSJD.getRates().forEach(rate -> {
            System.out.println("Rate for location SJDMY1:");
            System.out.println("  RateID=" + rate.getRateID());
            System.out.println("  Class=" + rate.getVehicleClass());
            System.out.println("  Availability=" + rate.getAvailability());
            System.out.println("  CurrencyCode=" + rate.getCurrencyCode());
            System.out.println("  Estimate (per day)=" + rate.getEstimate() / java.time.Duration.between(requestSJD.getPickupDateTime(), requestSJD.getReturnDateTime()).toDays());
            System.out.println("  Total Estimate=" + rate.getEstimate());
        });

        // Assert specific expected results for San Jose Del Cabo location
        ResRatesResponse.Rate rate1SJD = responseSJD.getRates().get(0);
        assertEquals("4", rate1SJD.getRateID());
        assertEquals("ECAR", rate1SJD.getVehicleClass());
        assertEquals("Available", rate1SJD.getAvailability());
        assertEquals("USD", rate1SJD.getCurrencyCode());
        assertEquals(tarifaSJD1.getEstimate() * java.time.Duration.between(requestSJD.getPickupDateTime(), requestSJD.getReturnDateTime()).toDays(), rate1SJD.getEstimate());
        assertTrue(rate1SJD.isPrePaid());

        // Repeat assertions for the other two rates...
        ResRatesResponse.Rate rate2SJD = responseSJD.getRates().get(1);
        assertEquals("5", rate2SJD.getRateID());
        assertEquals("FCAR", rate2SJD.getVehicleClass());
        assertEquals("Available", rate2SJD.getAvailability());
        assertEquals("USD", rate2SJD.getCurrencyCode());
        assertEquals(tarifaSJD2.getEstimate() * java.time.Duration.between(requestSJD.getPickupDateTime(), requestSJD.getReturnDateTime()).toDays(), rate2SJD.getEstimate());
        assertTrue(rate2SJD.isPrePaid());

        ResRatesResponse.Rate rate3SJD = responseSJD.getRates().get(2);
        assertEquals("6", rate3SJD.getRateID());
        assertEquals("MCAR", rate3SJD.getVehicleClass());
        assertEquals("Available", rate3SJD.getAvailability());
        assertEquals("USD", rate3SJD.getCurrencyCode());
        assertEquals(tarifaSJD3.getEstimate() * java.time.Duration.between(requestSJD.getPickupDateTime(), requestSJD.getReturnDateTime()).toDays(), rate3SJD.getEstimate());
        assertTrue(rate3SJD.isPrePaid());

        // Request for rates at the Los Cabos location
        ResRatesRequest requestCabo = new ResRatesRequest();
        requestCabo.setCorpRateID("MYWEB1");
        requestCabo.setPickupLocationCode("CABOMY1");
        requestCabo.setPickupDateTime(LocalDateTime.of(2024, 7, 19, 10, 0));
        requestCabo.setReturnLocationCode("CABOMY1");
        requestCabo.setReturnDateTime(LocalDateTime.of(2024, 7, 21, 10, 0));
        requestCabo.setCountryCode("US");
        requestCabo.setRateSet("CABO_RATE_SET");  // Set the rateSet

        // Execute the test for Los Cabos location
        System.out.println("Executing test for Los Cabos location");
        ResRatesResponse responseCabo = tarifaService.getRates(requestCabo, "CABO_RATE_SET");
        System.out.println("Response generated for Los Cabos: " + responseCabo);

        // Validate the response for Los Cabos location
        assertNotNull(responseCabo);
        assertTrue(responseCabo.isSuccess());
        assertNotNull(responseCabo.getRates());
        assertFalse(responseCabo.getRates().isEmpty());
        assertEquals(2, responseCabo.getRates().size());

        // Print results to verify correct behavior for Los Cabos location
        responseCabo.getRates().forEach(rate -> {
            System.out.println("Rate for location CABOMY1:");
            System.out.println("  RateID=" + rate.getRateID());
            System.out.println("  Class=" + rate.getVehicleClass());
            System.out.println("  Availability=" + rate.getAvailability());
            System.out.println("  CurrencyCode=" + rate.getCurrencyCode());
            System.out.println("  Estimate (per day)=" + rate.getEstimate() / java.time.Duration.between(requestCabo.getPickupDateTime(), requestCabo.getReturnDateTime()).toDays());
            System.out.println("  Total Estimate=" + rate.getEstimate());
        });

        // Assert specific expected results for Los Cabos location
        ResRatesResponse.Rate rate1Cabo = responseCabo.getRates().get(0);
        assertEquals("7", rate1Cabo.getRateID());
        assertEquals("ECAR", rate1Cabo.getVehicleClass());
        assertEquals("Available", rate1Cabo.getAvailability());
        assertEquals("USD", rate1Cabo.getCurrencyCode());
        assertEquals(tarifaCabo1.getEstimate() * java.time.Duration.between(requestCabo.getPickupDateTime(), requestCabo.getReturnDateTime()).toDays(), rate1Cabo.getEstimate());
        assertTrue(rate1Cabo.isPrePaid());

        ResRatesResponse.Rate rate2Cabo = responseCabo.getRates().get(1);
        assertEquals("8", rate2Cabo.getRateID());
        assertEquals("FCAR", rate2Cabo.getVehicleClass());
        assertEquals("Available", rate2Cabo.getAvailability());
        assertEquals("USD", rate2Cabo.getCurrencyCode());
        assertEquals(tarifaCabo2.getEstimate() * java.time.Duration.between(requestCabo.getPickupDateTime(), requestCabo.getReturnDateTime()).toDays(), rate2Cabo.getEstimate());
        assertTrue(rate2Cabo.isPrePaid());
    }
}
