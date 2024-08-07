package com.secret.platform.rate_product;

import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.options.OptionsServiceImpl;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RateProductServiceAllAttributesTest {
    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);

    @InjectMocks
    private RateProductServiceImpl rateProductService;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private OptionsServiceImpl optionsService;

    @Mock
    private OptionSetRepository optionSetRepository;

    @Mock
    private RateSetRepository rateSetRepository;

    private RateProduct rateProduct;
    private Options cvg1Option;
    private Options taxOption;
    private Options lrfOption;
    private Options locnOption;

    private OptionSet optionSet;
    private RateSet rateSet;

    // Define coverage codes in the test class
    private static final String CVG1_CODE = "LDW";
    private static final String CVG2_CODE = "PAI";
    private static final String CVG3_CODE = "XYZ";
    private static final String CVG4_CODE = "ABC";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cvg1Option = Options.builder()
                .optionCode(CVG1_CODE)
                .shortDesc("Loss Damage Waiver")
                .longDesc("Loss Damage Waiver")
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
                .product("Standard Product")
                .effPkupDate(new Date())
                .effPkupTime("12:00")
                .mustPkupBefore("18:00")
                .comment("Standard Comment")
                .rateType("Daily")
                .includedOptions(new ArrayList<>())
                .inclCvg1(true)
                .inclCvg2(true)
                .inclCvg3(false)
                .inclCvg4(false)
                .unused(10.0f)
                .milesMeth(15.0f)
                .week(200.0f)
                .extraWeek(180.0f)
                .freeMilesHour(50.0f)
                .graceMinutes(30)
                .chargeForGrace(true)
                .discountable(true)
                .editable(true)
                .minDaysForWeek(7)
                .periodMaxDays(30)
                .daysPerMonth(28)
                .commYn("Y")
                .commCat("A")
                .inclOptSet(optionSet)
                .currency("MXN")
                .paidFreeDay("*/*")
                .modDate(new Date())
                .modTime(12.0f)
                .modEmpl("EMP123")
                .empl("EMP456")
                .build();
    }

    @Test
    void testCreateRateProduct_WithInclOptSet() {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", false);

        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.of(optionSet));
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        RateProduct result = rateProductService.createRateProduct(rateProduct, coverages);

        assertNotNull(result);
        assertEquals(optionSet, result.getInclOptSet());
        assertTrue(result.getIncludedOptions().containsAll(optionSet.getOptions()), "Included options do not contain all options from the option set.");
    }

    @Test
    void testCreateRateProduct_WithNewInclOptSet() {
        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", false);

        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.empty());
        when(optionSetRepository.save(any(OptionSet.class))).thenReturn(optionSet);
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        RateProduct result = rateProductService.createRateProduct(rateProduct, coverages);

        assertNotNull(result);
        assertEquals(optionSet, result.getInclOptSet());
        assertTrue(result.getIncludedOptions().containsAll(optionSet.getOptions()), "Included options do not contain all options from the option set.");
    }

    @Test
    void testFetchProductsWhenSearchingForARateProduct() {
        logger.info("Setting up test data for RateProduct creation.");

        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("TAX", true);
        coverages.put("LRF", true);

        when(optionsService.findByOptionCode("TAX")).thenReturn(taxOption);
        when(optionsService.findByOptionCode("LRF")).thenReturn(lrfOption);
        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.of(optionSet));
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        logger.info("Creating RateProduct with coverages: {}", coverages);
        RateProduct createdRateProduct = rateProductService.createRateProduct(rateProduct, coverages);

        logger.info("Created RateProduct: {}", createdRateProduct);

        Set<Options> expectedIncludedOptions = new HashSet<>(Arrays.asList(taxOption, lrfOption, locnOption));
        logger.info("Asserting that the included options match the expected options.");
        assertEquals(expectedIncludedOptions.size(), createdRateProduct.getIncludedOptions().size());
        assertTrue(createdRateProduct.getIncludedOptions().containsAll(expectedIncludedOptions), "Included options do not contain all expected options.");

        logger.info("Test testFetchProductsWhenSearchingForARateProduct passed.");
    }

    @Test
    void testFetchCoveragesForRateProduct() throws Exception {
        logger.info("Setting up test data for RateProduct creation with coverages.");

        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("CVG2", false);

        logger.info("Mocking responses for optionsService.");
        when(optionsService.findByOptionCode(CVG1_CODE)).thenReturn(cvg1Option); // LDW corresponds to CVG1
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));

        logger.info("Creating RateProduct with coverages: {}", coverages);

        Method method = RateProductServiceImpl.class.getDeclaredMethod("updateIncludedOptions", Map.class, Set.class);
        method.setAccessible(true);
        Set<Options> includedOptionsSet = new HashSet<>(rateProduct.getIncludedOptions());
        method.invoke(rateProductService, coverages, includedOptionsSet);

        rateProduct.setIncludedOptions(new ArrayList<>(includedOptionsSet));

        logger.info("Fetching coverages for RateProduct.");
        List<Options> fetchedCoverages = rateProduct.getIncludedOptions();

        logger.info("Asserting that the included options match the expected coverages.");
        assertNotNull(fetchedCoverages);
        assertEquals(1, fetchedCoverages.size());
        assertEquals(cvg1Option, fetchedCoverages.get(0));

        logger.info("Test testFetchCoveragesForRateProduct passed.");
    }

    @Test
    void testAddBothCoveragesAndIncludedOptions() {
        // Arrange
        logger.info("Setting up test data for RateProduct creation with both coverages and included options.");

        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("TAX", true);

        // Mocking responses
        when(optionsService.findByOptionCode(CVG1_CODE)).thenReturn(cvg1Option); // LDW corresponds to CVG1
        when(optionsService.findByOptionCode("TAX")).thenReturn(taxOption);
        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.of(optionSet));
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);

        // Act
        logger.info("Creating RateProduct with coverages: {}", coverages);
        RateProduct createdRateProduct = rateProductService.createRateProduct(rateProduct, coverages);

        // Assert
        logger.info("Created RateProduct: {}", createdRateProduct);

        logger.info("Final included options in RateProduct: {}", createdRateProduct.getIncludedOptions());
        assertNotNull(createdRateProduct);
        assertTrue(createdRateProduct.getIncludedOptions().contains(cvg1Option), "Included options do not contain CVG1 option.");
        assertTrue(createdRateProduct.getIncludedOptions().contains(taxOption), "Included options do not contain TAX option.");
        assertTrue(createdRateProduct.getIncludedOptions().containsAll(optionSet.getOptions()), "Included options do not contain all options from the option set.");
    }

    @Test
    void testCreateRateProductAuditFields() {
        when(rateProductRepository.save(any(RateProduct.class))).thenReturn(rateProduct);
        when(optionSetRepository.findByCode(any(String.class))).thenReturn(Optional.of(optionSet));
        when(rateSetRepository.findByRateSetCode(any(String.class))).thenReturn(Optional.of(rateSet));

        Map<String, Boolean> coverages = new HashMap<>();
        coverages.put("CVG1", true);
        coverages.put("TAX", true);

        RateProduct createdProduct = rateProductService.createRateProduct(rateProduct, coverages);

        assertNotNull(createdProduct.getModDate());
        assertNotNull(createdProduct.getModTime());
        assertEquals("EMP123", createdProduct.getModEmpl());
        assertEquals("EMP456", createdProduct.getEmpl());
    }
}