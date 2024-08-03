package com.secret.platform.res_estimates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.class_code.ClassCodeDTO;
import com.secret.platform.options.Options;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductController;
import com.secret.platform.rate_product.RateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResEstimatesServiceTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RateProductController rateProductController;

    @Mock
    private RateProductService rateProductService;

    private RateProduct rateProduct;
    private Options cvg1Option;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rateProductController).build();

        cvg1Option = Options.builder()
                .optionCode("CVG1")
                .shortDesc("LDW")
                .longDesc("Loss Damage Waiver")
                .build();

        rateProduct = RateProduct.builder()
                .product("Standard Product")
                .includedOptions(Collections.singletonList(cvg1Option))
                .xDayRate(50.0f)
                .weekRate(300.0f)
                .build();
    }

    @Test
    void testAddClassesToRateProduct() throws Exception {
        ClassCodeDTO classCodeDTO = new ClassCodeDTO();
        classCodeDTO.setClassCode("XXAR");
        classCodeDTO.setDayRate(36.44);
        classCodeDTO.setWeekRate(255.06);
        classCodeDTO.setMonthRate(1093.13);
        classCodeDTO.setXDayRate(36.44);
        classCodeDTO.setHourRate(12.15);
        classCodeDTO.setMileRate(0.0);
        classCodeDTO.setRateProductNumber("Standard Product");
        classCodeDTO.setRateSetCode("GDLA01/US");

        List<ClassCodeDTO> classCodeDTOs = Collections.singletonList(classCodeDTO);

        RateProduct updatedRateProduct = RateProduct.builder()
                .product("Standard Product")
                .includedOptions(Collections.singletonList(cvg1Option))
                .classCodes(Collections.emptyList())
                .xDayRate(50.0f)
                .weekRate(300.0f)
                .build();

        when(rateProductService.addClassesToRateProduct(anyList())).thenReturn(updatedRateProduct);

        mockMvc.perform(post("/api/rate-products/add-classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(classCodeDTOs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Standard Product"))
                .andExpect(jsonPath("$.includedOptions[0].optionCode").value("CVG1"))
                .andExpect(jsonPath("$.weekRate").value(300.0))
                .andExpect(jsonPath("$.xDayRate").value(50.0));


        verify(rateProductService, times(1)).addClassesToRateProduct(anyList());
    }
}
