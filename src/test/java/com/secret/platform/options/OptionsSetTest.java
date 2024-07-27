package com.secret.platform.options;

import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.rate_product.RateProductServiceImpl;
import com.secret.platform.rate_set.RateSet;
import com.secret.platform.rate_set.RateSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
public class OptionsSetTest {

    @Mock
    private OptionSetRepository optionSetRepository;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private RateSetRepository rateSetRepository;

    @Mock
    private OptionsServiceImpl optionsService;

    @InjectMocks
    private RateProductServiceImpl rateProductService;

    private OptionSet optionSet;
    private RateProduct rateProduct;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Initialize OptionSet with three options using the builder pattern
        Options option1 = Options.builder()
                .id(35L)
                .optionCode("LOCN")
                .optSetCode("1")
                .build();

        Options option2 = Options.builder()
                .id(36L)
                .optionCode("TAX")
                .optSetCode("1")
                .build();

        Options option3 = Options.builder()
                .id(37L)
                .optionCode("LRF")
                .optSetCode("1")
                .build();

        List<Options> optionsList = Arrays.asList(option1, option2, option3);

        optionSet = OptionSet.builder()
                .code("1")
                .effDate(new Date())
                .termDate(new Date())
                .options(optionsList)
                .build();

        // Initialize RateSet
        RateSet rateSet = new RateSet();
        rateSet.setRateSetCode("DEFAULT_RATE_SET");

        // Initialize RateProduct
        rateProduct = new RateProduct();
        rateProduct.setInclOptSet(optionSet);
        rateProduct.setRateSet(rateSet);
    }

    @Test
    public void testCreateRateProductWithOptions() {
        // Mock repository behavior
        when(optionSetRepository.findByCode("1")).thenReturn(Optional.of(optionSet));
        when(rateSetRepository.findByRateSetCode("DEFAULT_RATE_SET")).thenReturn(Optional.of(rateProduct.getRateSet()));
        when(rateProductRepository.save(any(RateProduct.class))).thenAnswer(i -> i.getArguments()[0]);

        // Call createRateProduct
        RateProduct createdRateProduct = rateProductService.createRateProduct(rateProduct, new HashMap<>());

        // Verify interactions and state
        verify(optionSetRepository, times(1)).findByCode("1");
        verify(rateSetRepository, times(1)).findByRateSetCode("DEFAULT_RATE_SET");
        verify(rateProductRepository, times(1)).save(any(RateProduct.class));

        // Check that all options are included in the RateProduct
        assertEquals(3, createdRateProduct.getIncludedOptions().size());
        assertEquals(new HashSet<>(optionSet.getOptions()), new HashSet<>(createdRateProduct.getIncludedOptions()));
    }
}
