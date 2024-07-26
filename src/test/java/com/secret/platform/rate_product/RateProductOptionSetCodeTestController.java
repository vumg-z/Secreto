package com.secret.platform.rate_product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.options.Options;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import com.secret.platform.type_code.ValidTypeCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateProductController.class)
@Import(RateSetDeserializer.class) // Ensure the deserializer is loaded
public class RateProductOptionSetCodeTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateProductService rateProductService;

    @MockBean
    private RateSetRepository rateSetRepository; // Mock the repository

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateRateProduct() throws Exception {
        RateSet rateSet = RateSet.builder()
                .id(1L)
                .rateSetCode("BJXA01/*")
                .description("Default description")
                .build();

        OptionSet optionSet = OptionSet.builder()
                .id(1L)
                .code("1")
                .options(Arrays.asList(
                        Options.builder().id(1L).optionCode("LOCN").build(),
                        Options.builder().id(2L).optionCode("TAX").build(),
                        Options.builder().id(3L).optionCode("LRF").build()
                ))
                .build();

        ValidTypeCode validTypeCode = ValidTypeCode.builder()
                .id(1L)
                .typeCode("FC")
                .description("FULLY COMP INSURED")
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .id(1L)
                .rateSet(rateSet)
                .product("MI2")
                .effPkupDate(new Date())
                .effPkupTime("12:00")
                .mustPkupBefore("18:00")
                .comment("Walkup L1")
                .inclCvg1(false)
                .inclCvg2(false)
                .inclCvg3(false)
                .inclCvg4(false)
                .graceMinutes(59)
                .chargeForGrace(true)
                .discountable(true)
                .editable(true)
                .defltRaType(validTypeCode)
                .daysPerMonth(30)
                .commCat("A")
                .inclOptSet(optionSet)
                .currency("MXN")
                .paidFreeDay("*/*")
                .modDate(new Date())
                .modEmpl("EMP123")
                .empl("EMP456")
                .build();

        when(rateProductService.createRateProduct(any(RateProduct.class), any(Map.class)))
                .thenReturn(rateProduct);

        when(rateSetRepository.findById(1L)).thenReturn(Optional.of(rateSet));

        String requestJson = objectMapper.writeValueAsString(rateProduct);

        mockMvc.perform(post("/api/rate-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rateSet.id").value(1))
                .andExpect(jsonPath("$.product").value("MI2"))
                .andExpect(jsonPath("$.inclOptSet.id").value(1))
                .andExpect(jsonPath("$.inclOptSet.options.length()").value(3))
                .andExpect(jsonPath("$.inclOptSet.options[?(@.id == 1)].optionCode").value("LOCN"))
                .andExpect(jsonPath("$.inclOptSet.options[?(@.id == 2)].optionCode").value("TAX"))
                .andExpect(jsonPath("$.inclOptSet.options[?(@.id == 3)].optionCode").value("LRF"));
    }
}
