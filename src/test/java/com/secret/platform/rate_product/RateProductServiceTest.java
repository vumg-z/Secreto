package com.secret.platform.rate_product;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.type_code.ValidTypeCode;
import com.secret.platform.type_code.ValidTypeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RateProductServiceTest {

    @InjectMocks
    private RateProductServiceImpl rateProductService;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private OptionsServiceImpl optionsService;

    @Mock
    private ValidTypeCodeRepository validTypeCodeRepository;

    private RateProduct rateProduct;
    private Options cvg1Option;
    private Options cvg2Option;
    private Options cvg3Option;
    private Options cvg4Option;
    private ValidTypeCode validTypeCode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cvg1Option = Options.builder()
                .optionCode("CVG1")
                .shortDesc("LDW")
                .longDesc("Loss Damage Waiver")
                .build();

        cvg2Option = Options.builder()
                .optionCode("CVG2")
                .shortDesc("PAI")
                .longDesc("Personal Accident Insurance")
                .build();

        cvg3Option = Options.builder()
                .optionCode("CVG3")
                .shortDesc("SLI")
                .longDesc("Supplemental Liability Insurance")
                .build();

        cvg4Option = Options.builder()
                .optionCode("CVG4")
                .shortDesc("Other")
                .longDesc("Other Coverage")
                .build();

        validTypeCode = ValidTypeCode.builder()
                .typeCode("FC")
                .description("FULLY COMP INSURED")
                .build();

        rateProduct = RateProduct.builder()
                .includedOptions(new ArrayList<>())
                .defltRaType(validTypeCode)
                .build();
    }

    @Test
    void testCreateRateProduct_WithCoverages() {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", true);
        coverages.put("CVG3", false);
        coverages.put("CVG4", false);

        when(optionsService.findByOptionCode("CVG1")).thenReturn(cvg1Option);
        when(optionsService.findByOptionCode("CVG2")).thenReturn(cvg2Option);
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        RateProduct result = rateProductService.createRateProduct(rateProduct, coverages);

        assertEquals(2, result.getIncludedOptions().size());
        assertTrue(result.getIncludedOptions().contains(cvg1Option));
        assertTrue(result.getIncludedOptions().contains(cvg2Option));
    }

    @Test
    void testUpdateRateProduct_WithCoverages() throws IllegalAccessException {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", true);
        coverages.put("CVG3", false);
        coverages.put("CVG4", false);

        rateProduct.setEditable(true);

        when(optionsService.findByOptionCode("CVG1")).thenReturn(cvg1Option);
        when(optionsService.findByOptionCode("CVG2")).thenReturn(cvg2Option);
        when(rateProductRepository.findById(any(Long.class))).thenReturn(Optional.of(rateProduct));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        RateProduct rateProductDetails = RateProduct.builder().build();

        RateProduct result = rateProductService.updateRateProduct(1L, rateProductDetails, coverages);

        assertEquals(2, result.getIncludedOptions().size());
        assertTrue(result.getIncludedOptions().contains(cvg1Option));
        assertTrue(result.getIncludedOptions().contains(cvg2Option));
    }
    /*

    @Test
    void testFindByDefltRaType() {
        when(validTypeCodeRepository.findByTypeCode("FC")).thenReturn(Optional.of(validTypeCode));
        when(rateProductRepository.findByDefltRaType(validTypeCode)).thenReturn(Optional.of(rateProduct));

        Optional<RateProduct> result = rateProductService.findByDefltRaType("FC");
        assertTrue(result.isPresent());
        assertEquals(rateProduct, result.get());
    }

    @Test
    void testFindByDefltRaType_NotFound() {
        when(validTypeCodeRepository.findByTypeCode("XYZ")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            rateProductService.findByDefltRaType("XYZ");
        });
    }
     */

}
