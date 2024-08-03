package com.secret.platform.options_rates;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secret.platform.options.OptionsRateRequest;
import com.secret.platform.options.OptionsRates;
import com.secret.platform.options.OptionsRatesController;
import com.secret.platform.options.OptionsRatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OptionsRatesController.class)
public class OptionsRatesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OptionsRatesService optionsRatesService;

    private OptionsRates optionsRates;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        optionsRates = OptionsRates.builder()
                .optionCode("FC01")
                .locationCode("LOC01")
                .currency("USD")
                .primaryRate(100.0)
                .weeklyRate(700.0)
                .monthlyRate(3000.0)
                .xdayRate(10.0)
                .build();
    }

    @Test
    public void testAddRateToOption_Success() throws Exception {
        when(optionsRatesService.addRateToOption(
                anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyDouble(), anyDouble(), anyString(), anyString()))
                .thenReturn(optionsRates);

        OptionsRateRequest rateRequest = OptionsRateRequest.builder()
                .optionCode("FC01")
                .locationCode("LOC01")
                .currency("USD")
                .primaryRate(100.0)
                .weeklyRate(700.0)
                .monthlyRate(3000.0)
                .xdayRate(10.0)
                .pricingCode("20")
                .privilegeCode("L0")
                .build();

        String payload = new ObjectMapper().writeValueAsString(rateRequest);

        mockMvc.perform(post("/api/options-rates/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.optionCode").value("FC01"))
                .andExpect(jsonPath("$.locationCode").value("LOC01"))
                .andExpect(jsonPath("$.currency").value("USD"))
                .andExpect(jsonPath("$.primaryRate").value(100.0))
                .andExpect(jsonPath("$.weeklyRate").value(700.0))
                .andExpect(jsonPath("$.monthlyRate").value(3000.0))
                .andExpect(jsonPath("$.xdayRate").value(10.0));
    }

  /*  @Test
    public void testAddRateToOption_MissingParameter() throws Exception {
        OptionsRateRequest rateRequest = new OptionsRateRequest(); // Empty request body

        String payload = new ObjectMapper().writeValueAsString(rateRequest);

        mockMvc.perform(post("/api/options-rates/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest());
    }*/

    @Test
    public void testFindRatesByCriteria_Success() throws Exception {
        when(optionsRatesService.findRatesByCriteria(anyString(), anyString(), anyString()))
                .thenReturn(List.of(optionsRates));

        mockMvc.perform(get("/api/options-rates/find")
                        .param("optionCode", "FC01")
                        .param("privilegeCodeId", "L0")
                        .param("pricingCodeId", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].optionCode").value("FC01"))
                .andExpect(jsonPath("$[0].locationCode").value("LOC01"))
                .andExpect(jsonPath("$[0].currency").value("USD"))
                .andExpect(jsonPath("$[0].primaryRate").value(100.0))
                .andExpect(jsonPath("$[0].weeklyRate").value(700.0))
                .andExpect(jsonPath("$[0].monthlyRate").value(3000.0))
                .andExpect(jsonPath("$[0].xdayRate").value(10.0));
    }
}
