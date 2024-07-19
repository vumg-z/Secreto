package com.secret.platform.pricing_code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PricingCodeServiceTests {

    @Mock
    private PricingCodeRepository pricingCodeRepository;

    @InjectMocks
    private PricingCodeServiceImpl pricingCodeService;

    private PricingCode pricingCode;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
    public void testCreatePricingCode() {
        when(pricingCodeRepository.save(pricingCode)).thenReturn(pricingCode);

        PricingCode created = pricingCodeService.createPricingCode(pricingCode);

        assertNotNull(created);
        assertEquals(pricingCode.getCode(), created.getCode());
        verify(pricingCodeRepository, times(1)).save(pricingCode);
    }

    @Test
    public void testGetPricingCodeById() {
        when(pricingCodeRepository.findById(1L)).thenReturn(Optional.of(pricingCode));

        Optional<PricingCode> found = pricingCodeService.getPricingCodeById(1L);

        assertTrue(found.isPresent());
        assertEquals(pricingCode.getCode(), found.get().getCode());
        verify(pricingCodeRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllPricingCodes() {
        List<PricingCode> pricingCodes = Arrays.asList(pricingCode);
        when(pricingCodeRepository.findAll()).thenReturn(pricingCodes);

        List<PricingCode> found = pricingCodeService.getAllPricingCodes();

        assertEquals(1, found.size());
        verify(pricingCodeRepository, times(1)).findAll();
    }

    @Test
    public void testUpdatePricingCode() {
        when(pricingCodeRepository.existsById(pricingCode.getId())).thenReturn(true);
        when(pricingCodeRepository.save(pricingCode)).thenReturn(pricingCode);

        PricingCode updated = pricingCodeService.updatePricingCode(pricingCode.getId(), pricingCode);

        assertNotNull(updated);
        assertEquals(pricingCode.getCode(), updated.getCode());
        verify(pricingCodeRepository, times(1)).save(pricingCode);
    }

    @Test
    public void testDeletePricingCode() {
        doNothing().when(pricingCodeRepository).deleteById(pricingCode.getId());

        pricingCodeService.deletePricingCode(pricingCode.getId());

        verify(pricingCodeRepository, times(1)).deleteById(pricingCode.getId());
    }
}
