package com.secret.platform.pricing_code;

import com.secret.platform.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class PricingCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PricingCodeService pricingCodeService;

    @InjectMocks
    private PricingCodeController pricingCodeController;

    private PricingCode pricingCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = standaloneSetup(pricingCodeController).build();

        pricingCode = PricingCode.builder()
                .id(1L)
                .code("A")
                .description("Economy and Compact Cars")
                .ldwRate(12.95)
                .noLdwResp(3000.00)
                .noLdwAge1(25)
                .noLdwResp1(4000.00)
                .noLdwAge2(22)
                .noLdwResp2(5000.00)
                .inclLdwResp(0.00)
                .cvg2Value(2000.00)
                .cvg3Value(1000.00)
                .cvg4Value(500.00)
                .build();
    }

    @Test
    public void testCreatePricingCode() throws Exception {
        when(pricingCodeService.createPricingCode(any(PricingCode.class))).thenReturn(pricingCode);

        mockMvc.perform(post("/api/pricing-codes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"A\",\"description\":\"Economy and Compact Cars\",\"ldwRate\":12.95}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A"))
                .andExpect(jsonPath("$.description").value("Economy and Compact Cars"))
                .andExpect(jsonPath("$.ldwRate").value(12.95));

        verify(pricingCodeService, times(1)).createPricingCode(any(PricingCode.class));
    }

    @Test
    public void testGetAllPricingCodes() throws Exception {
        List<PricingCode> pricingCodes = Arrays.asList(pricingCode);

        when(pricingCodeService.getAllPricingCodes()).thenReturn(pricingCodes);

        mockMvc.perform(get("/api/pricing-codes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value("A"));

        verify(pricingCodeService, times(1)).getAllPricingCodes();
    }

    @Test
    public void testGetPricingCodeById() throws Exception {
        when(pricingCodeService.getPricingCodeById(anyLong())).thenReturn(Optional.of(pricingCode));

        mockMvc.perform(get("/api/pricing-codes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A"))
                .andExpect(jsonPath("$.description").value("Economy and Compact Cars"))
                .andExpect(jsonPath("$.ldwRate").value(12.95));

        verify(pricingCodeService, times(1)).getPricingCodeById(1L);
    }

    @Test
    public void testGetPricingCodeById_NotFound() throws Exception {
        when(pricingCodeService.getPricingCodeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pricing-codes/1"))
                .andExpect(status().isNotFound());

        verify(pricingCodeService, times(1)).getPricingCodeById(1L);
    }

    @Test
    public void testUpdatePricingCode() throws Exception {
        PricingCode updatedPricingCode = PricingCode.builder()
                .id(1L)
                .code("A")
                .description("Updated Description")
                .ldwRate(15.00)
                .build();

        when(pricingCodeService.updatePricingCode(anyLong(), any(PricingCode.class))).thenReturn(updatedPricingCode);

        mockMvc.perform(put("/api/pricing-codes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"A\",\"description\":\"Updated Description\",\"ldwRate\":15.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.ldwRate").value(15.00));

        verify(pricingCodeService, times(1)).updatePricingCode(anyLong(), any(PricingCode.class));
    }

    @Test
    public void testUpdatePricingCode_NotFound() throws Exception {
        when(pricingCodeService.updatePricingCode(anyLong(), any(PricingCode.class))).thenReturn(null);

        mockMvc.perform(put("/api/pricing-codes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"A\",\"description\":\"Updated Description\",\"ldwRate\":15.00}"))
                .andExpect(status().isNotFound());

        verify(pricingCodeService, times(1)).updatePricingCode(anyLong(), any(PricingCode.class));
    }

    @Test
    public void testDeletePricingCode() throws Exception {
        doNothing().when(pricingCodeService).deletePricingCode(anyLong());

        mockMvc.perform(delete("/api/pricing-codes/1"))
                .andExpect(status().isNoContent());

        verify(pricingCodeService, times(1)).deletePricingCode(1L);
    }
}
