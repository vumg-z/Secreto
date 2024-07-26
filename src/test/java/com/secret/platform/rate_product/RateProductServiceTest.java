package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.location.Location;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
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

    @Mock
    private ClassCodeRepository classCodeRepository;

    @Mock
    private RateSetRepository rateSetRepository;

    @Mock
    private OptionSetRepository optionSetRepository;

    private RateProduct rateProduct;
    private Options cvg1Option;
    private Options cvg2Option;
    private Options taxOption;
    private Options lrfOption;
    private Options locnOption;
    private RateSet rateSet;
    private OptionSet optionSet;
    private List<ClassCode> classCodes; // Declare classCodes here

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cvg1Option = Options.builder()
                .optionCode("LDW")
                .shortDesc("Loss Damage Waiver")
                .longDesc("Loss Damage Waiver")
                .build();

        cvg2Option = Options.builder()
                .optionCode("PAI")
                .shortDesc("Personal Accident Insurance")
                .longDesc("Personal Accident Insurance")
                .build();

        taxOption = Options.builder()
                .optionCode("TAX")
                .shortDesc("Tax")
                .longDesc("Tax Description")
                .build();

        lrfOption = Options.builder()
                .optionCode("LRF")
                .shortDesc("Local Registration Fee")
                .longDesc("Local Registration Fee Description")
                .build();

        locnOption = Options.builder()
                .optionCode("LOCN")
                .shortDesc("Location")
                .longDesc("Location Description")
                .build();

        optionSet = OptionSet.builder()
                .id(1L)
                .code("A")
                .effDate(new Date())
                .termDate(new Date())
                .options(Arrays.asList(taxOption, lrfOption, locnOption))
                .build();

        rateSet = RateSet.builder()
                .id(1L)
                .rateSetCode("Standard Rate Set")
                .description("Standard Rate Set Description")
                .build();

        rateProduct = RateProduct.builder()
                .rateSet(rateSet)
                .includedOptions(new ArrayList<>())
                .inclOptSet(optionSet)
                .build();

        Location defaultLocation = Location.builder()
                .id(1L)
                .locationNumber("DEFAULT")
                .build();

        ClassCode classCode1 = ClassCode.builder()
                .id(1L)
                .location(defaultLocation)
                .classCode("SUV")
                .description("Sport Utility Vehicle")
                .build();

        ClassCode classCode2 = ClassCode.builder()
                .id(2L)
                .location(defaultLocation)
                .classCode("SEDAN")
                .description("Sedan")
                .build();

        classCodes = Arrays.asList(classCode1, classCode2); // Initialize classCodes here
    }

    @Test
    void testCreateRateProduct_WithCoverages() {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", true);
        coverages.put("CVG3", false);
        coverages.put("CVG4", false);

        when(optionsService.findByOptionCode("LDW")).thenReturn(cvg1Option);
        when(optionsService.findByOptionCode("PAI")).thenReturn(cvg2Option);
        when(rateSetRepository.findByRateSetCode(anyString())).thenReturn(Optional.of(rateSet));
        when(optionSetRepository.findByCode(anyString())).thenReturn(Optional.of(optionSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        RateProduct result = rateProductService.createRateProduct(rateProduct, coverages);

        // Assert the size of included options
        assertEquals(5, result.getIncludedOptions().size());

        // Assert that the expected options are included
        assertTrue(result.getIncludedOptions().contains(cvg1Option));
        assertTrue(result.getIncludedOptions().contains(cvg2Option));
        assertTrue(result.getIncludedOptions().contains(taxOption));
        assertTrue(result.getIncludedOptions().contains(lrfOption));
        assertTrue(result.getIncludedOptions().contains(locnOption));
    }

    @Test
    void testUpdateRateProduct_WithCoverages() throws IllegalAccessException {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", true);
        coverages.put("CVG3", false);
        coverages.put("CVG4", false);

        rateProduct.setEditable(true);

        when(optionsService.findByOptionCode("LDW")).thenReturn(cvg1Option);
        when(optionsService.findByOptionCode("PAI")).thenReturn(cvg2Option);
        when(rateProductRepository.findById(any(Long.class))).thenReturn(Optional.of(rateProduct));
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));
        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.of(optionSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);
        when(classCodeRepository.findAllByLocation(argThat(location -> "DEFAULT".equals(location.getLocationNumber())))).thenReturn(classCodes);

        RateProduct rateProductDetails = RateProduct.builder().build();

        RateProduct result = rateProductService.updateRateProduct(1L, rateProductDetails, coverages);

        assertEquals(2, result.getIncludedOptions().size());
        assertTrue(result.getIncludedOptions().contains(cvg1Option));
        assertTrue(result.getIncludedOptions().contains(cvg2Option));
        assertNotNull(result.getClassCodes());
        assertEquals(2, result.getClassCodes().size());
        assertEquals("SUV", result.getClassCodes().get(0).getClassCode());
        assertEquals("SEDAN", result.getClassCodes().get(1).getClassCode());

        verify(rateProductRepository, times(1)).findById(1L);
        verify(rateProductRepository, times(1)).save(any(RateProduct.class));
        verify(classCodeRepository, times(1)).findAllByLocation(argThat(location -> "DEFAULT".equals(location.getLocationNumber())));
    }
}
