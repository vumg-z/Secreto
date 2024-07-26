package com.secret.platform.rate_product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.class_code.ClassCode;
import com.secret.platform.options.Options;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RateProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RateProductController rateProductController;

    @Mock
    private RateProductService rateProductService;

    private RateProduct rateProduct;
    private Options cvg1Option;
    private ClassCode classCode1;
    private ClassCode classCode2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rateProductController).build();

        cvg1Option = Options.builder()
                .optionCode("CVG1")
                .shortDesc("LDW")
                .longDesc("Loss Damage Waiver")
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

        rateProduct = RateProduct.builder()
                .product("Standard Product")
                .includedOptions(new ArrayList<>(Collections.singletonList(cvg1Option)))
                .xDayRate(50.0f)
                .build();
    }

    @Test
    void testCreateRateProductWithoutClassCodes() throws Exception {
        when(rateProductService.createRateProduct(any(RateProduct.class), any())).thenReturn(rateProduct);

        mockMvc.perform(post("/api/rate-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rateProduct))
                        .param("CVG1", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Standard Product"))
                .andExpect(jsonPath("$.includedOptions[0].optionCode").value("CVG1"))
                .andExpect(jsonPath("$.classCodes").doesNotExist());

        verify(rateProductService, times(1)).createRateProduct(any(RateProduct.class), any());
    }

    @Test
    void testAddClassCodesToRateProduct() throws Exception {
        RateProduct rateProductWithClassCodes = RateProduct.builder()
                .product("Standard Product")
                .includedOptions(new ArrayList<>(Collections.singletonList(cvg1Option)))
                .classCodes(new ArrayList<>(Arrays.asList(classCode1, classCode2)))
                .xDayRate(50.0f)
                .build();

        when(rateProductService.updateRateProduct(anyLong(), any(RateProduct.class), any())).thenReturn(rateProductWithClassCodes);

        mockMvc.perform(put("/api/rate-products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rateProductWithClassCodes))
                        .param("CVG1", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Standard Product"))
                .andExpect(jsonPath("$.classCodes[0].classCode").value("A1"))
                .andExpect(jsonPath("$.classCodes[1].classCode").value("B1"));

        verify(rateProductService, times(1)).updateRateProduct(anyLong(), any(RateProduct.class), any());
    }

    @Test
    void testGetRateProductById() throws Exception {
        when(rateProductService.getRateProductById(anyLong())).thenReturn(Optional.of(rateProduct));

        mockMvc.perform(get("/api/rate-products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Standard Product"))
                .andExpect(jsonPath("$.classCodes").doesNotExist());

        verify(rateProductService, times(1)).getRateProductById(1L);
    }

    @Test
    void testGetAllRateProducts() throws Exception {
        when(rateProductService.getAllRateProducts()).thenReturn(Collections.singletonList(rateProduct));

        mockMvc.perform(get("/api/rate-products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product").value("Standard Product"))
                .andExpect(jsonPath("$[0].classCodes").doesNotExist());

        verify(rateProductService, times(1)).getAllRateProducts();
    }

    @Test
    void testUpdateRateProduct() throws Exception {
        when(rateProductService.updateRateProduct(anyLong(), any(RateProduct.class), any())).thenReturn(rateProduct);

        mockMvc.perform(put("/api/rate-products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rateProduct))
                        .param("CVG1", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Standard Product"))
                .andExpect(jsonPath("$.classCodes").doesNotExist());

        verify(rateProductService, times(1)).updateRateProduct(anyLong(), any(RateProduct.class), any());
    }

    @Test
    void testDeleteRateProduct() throws Exception {
        doNothing().when(rateProductService).deleteRateProduct(anyLong());

        mockMvc.perform(delete("/api/rate-products/1"))
                .andExpect(status().isNoContent());

        verify(rateProductService, times(1)).deleteRateProduct(1L);
    }
}
