package com.secret.platform.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeService;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeService;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @Mock
    private RateProductService rateProductService;

    @Mock
    private ClassCodeService classCodeService;

    @Mock
    private PricingCodeService pricingCodeService;

    @InjectMocks
    private LocationController locationController;

    private ObjectMapper objectMapper;
    private Location location;
    private RateProduct rateProduct;
    private ClassCode classCode1;
    private ClassCode classCode2;
    private PricingCode pricingCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
        objectMapper = new ObjectMapper();

        rateProduct = RateProduct.builder().id(1L).build();

        location = Location.builder()
                .locationNumber("LOC123")
                .locationName("Location 1")
                .walkupRate(rateProduct)
                .profitCenterNumber("001")
                .doFuelCalc("Y")
                .autoVehicleSelect("N")
                .checkOutFuel("8")
                .validRentalLoc("Y")
                .build();

        classCode1 = ClassCode.builder()
                .id(1L)
                .classCode("A1")
                .description("Compact")
                .build();

        classCode2 = ClassCode.builder()
                .id(2L)
                .classCode("B1")
                .description("SUV")
                .build();

        pricingCode = PricingCode.builder()
                .id(1L)
                .code("P1")
                .description("Standard Pricing")
                .build();
    }

    @Test
    public void testCreateRateProduct() throws Exception {
        when(rateProductService.createRateProduct(any(RateProduct.class), any())).thenReturn(rateProduct);

        mockMvc.perform(post("/api/locations/rate-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rateProduct)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(rateProductService, times(1)).createRateProduct(any(RateProduct.class), any());
    }

    @Test
    public void testCreateLocation() throws Exception {
        when(locationService.saveLocation(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(location)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location_name").value("Location 1"));

        verify(locationService, times(1)).saveLocation(any(Location.class));
    }

    @Test
    public void testCreateClassCode() throws Exception {
        when(locationService.getLocationById(anyLong())).thenReturn(Optional.of(location));
        when(pricingCodeService.getPricingCodeById(anyLong())).thenReturn(Optional.of(pricingCode));
        when(classCodeService.createClassCode(any(ClassCode.class))).thenReturn(classCode1);

        mockMvc.perform(post("/api/locations/class-code")
                        .param("locationId", "1")
                        .param("pricingCodeId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classCode1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.classCode").value("A1"));

        verify(locationService, times(1)).getLocationById(1L);
        verify(pricingCodeService, times(1)).getPricingCodeById(1L);
        verify(classCodeService, times(1)).createClassCode(any(ClassCode.class));
    }

    @Test
    public void testGetLocationById() throws Exception {
        when(locationService.getLocationById(anyLong())).thenReturn(Optional.of(location));

        mockMvc.perform(get("/api/locations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location_name").value("Location 1"));

        verify(locationService, times(1)).getLocationById(1L);
    }

    @Test
    public void testGetAllLocations() throws Exception {
        when(locationService.getAllLocations()).thenReturn(Arrays.asList(location));

        mockMvc.perform(get("/api/locations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].location_name").value("Location 1"));

        verify(locationService, times(1)).getAllLocations();
    }

    @Test
    public void testUpdateLocation() throws Exception {
        when(locationService.updateLocation(anyLong(), any(Location.class))).thenReturn(location);

        mockMvc.perform(put("/api/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(location)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location_name").value("Location 1"));

        verify(locationService, times(1)).updateLocation(anyLong(), any(Location.class));
    }

    @Test
    public void testDeleteLocation() throws Exception {
        doNothing().when(locationService).deleteLocation(anyLong());

        mockMvc.perform(delete("/api/locations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(locationService, times(1)).deleteLocation(1L);
    }
}
