package com.secret.platform.privilege_code;

import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.Options;
import com.secret.platform.rate_product.RateProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PrivilegeCodeProductTest {
    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);

    @Mock
    private PrivilegeCodeRepository privilegeCodeRepository;

    @Mock
    private OptionSetRepository optionSetRepository;

    @InjectMocks
    private PrivilegeCodeServiceImpl privilegeCodeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllProductsAssociatedWithThisPrivilegeCode() {
        // Create mock data
        Options option1 = Options.builder().id(1L).optionCode("OPT1").build();
        Options option2 = Options.builder().id(2L).optionCode("OPT2").build();
        List<Options> optionsList = Arrays.asList(option1, option2);

        OptionSet optionSet = OptionSet.builder().id(1L).code("SET1").options(optionsList).build();

        PrivilegeCode privilegeCode = PrivilegeCode.builder()
                .id(1L)
                .code("PRIV1")
                .optionSet(optionSet)
                .build();

        when(privilegeCodeRepository.findById(1L)).thenReturn(Optional.of(privilegeCode));

        // Log the start of the test
        logger.info("Starting test for retrieving products associated with PrivilegeCode ID: 1");

        // Act
        Optional<PrivilegeCode> retrievedPrivilegeCode = privilegeCodeService.getPrivilegeCodeById(1L);

        // Log the results
        if (retrievedPrivilegeCode.isPresent()) {
            logger.info("PrivilegeCode found: {}", retrievedPrivilegeCode.get());
            logger.info("OptionSet found: {}", retrievedPrivilegeCode.get().getOptionSet());
            logger.info("Options in OptionSet: {}", retrievedPrivilegeCode.get().getOptionSet().getOptions());
        } else {
            logger.warn("PrivilegeCode with ID 1 not found");
        }

        // Assert
        assertTrue(retrievedPrivilegeCode.isPresent(), "PrivilegeCode should be present");
        assertNotNull(retrievedPrivilegeCode.get().getOptionSet(), "OptionSet should not be null");
        List<Options> retrievedOptions = retrievedPrivilegeCode.get().getOptionSet().getOptions();
        assertEquals(2, retrievedOptions.size(), "There should be 2 options");
        assertTrue(retrievedOptions.contains(option1), "Option list should contain option1");
        assertTrue(retrievedOptions.contains(option2), "Option list should contain option2");

        // Log the end of the test
        logger.info("Test completed successfully");
    }
}
