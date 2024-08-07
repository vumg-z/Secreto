/*
package com.secret.platform.corporate_account;

import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.option_set.OptionSet;
import com.secret.platform.options.Options;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.res_rates.ResRatesControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CorporateAccountTest {

    private static final Logger logger = LoggerFactory.getLogger(ResRatesControllerTest.class);

    @Mock
    private CorporateAccountRepository corporateAccountRepository;

    @Mock
    private CorporateContractRepository corporateContractRepository;

    @InjectMocks
    private CorporateAccountServiceImpl corporateAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCorporateAccountWithContractAndPrivilegeCode() {
        // Create mock data for Options
        Options option1 = Options.builder().id(1L).optionCode("OPT1").build();
        Options option2 = Options.builder().id(2L).optionCode("OPT2").build();
        Options option3 = Options.builder().id(3L).optionCode("OPT3").build();
        List<Options> optionsList = Arrays.asList(option1, option2, option3);

        // Create mock data for OptionSet
        OptionSet optionSet = OptionSet.builder().id(1L).code("SET1").options(optionsList).build();

        // Create mock data for PrivilegeCode
        PrivilegeCode privilegeCode = PrivilegeCode.builder()
                .id(1L)
                .code("PRIV1")
                .optionSet(optionSet)
                .build();

        // Create mock data for RateProduct
        RateProduct rateProduct = RateProduct.builder()
                .id(1L)
                .product("RP1")
                .includedOptions(optionsList)
                .xDayRate(100.0f) // Set the xDayRate
                .build();

        // Create mock data for CorporateContract
        CorporateContract corporateContract = CorporateContract.builder()
                .id(1L)
                .contractNumber("CONTRACT1")
                .privilegeCodes(Arrays.asList(privilegeCode))
                .rateProduct(rateProduct)
                .build();

        // Create CorporateAccount
        CorporateAccount corporateAccount = CorporateAccount.builder()
                .id(1L)
                .cdpId("CDP1")
                .startBookDate(LocalDate.now())
                .endBookDate(LocalDate.now().plusDays(30))
                .companyName("Company A")
                .corporateContract(corporateContract)
                .build();

        when(corporateAccountRepository.save(corporateAccount)).thenReturn(corporateAccount);
        when(corporateContractRepository.findByContractNumber("CONTRACT1")).thenReturn(Optional.of(corporateContract));

        // Log the start of the test
        logger.info("Starting test for creating CorporateAccount with integrated CorporateContract and PrivilegeCode");

        // Act
        CorporateAccount createdCorporateAccount = corporateAccountService.createCorporateAccount(corporateAccount);

        // Log the results
        logger.info("CorporateAccount created: {}", createdCorporateAccount);
        logger.info("CorporateContract: {}", createdCorporateAccount.getCorporateContract());
        createdCorporateAccount.getCorporateContract().getPrivilegeCodes().forEach(pc -> {
            logger.info("PrivilegeCode: {}", pc);
            logger.info("OptionSet: {}", pc.getOptionSet());
            pc.getOptionSet().getOptions().forEach(option -> logger.info("Option: {}", option));
        });
        logger.info("RateProduct: {}", createdCorporateAccount.getCorporateContract().getRateProduct());

        // Assert
        assertNotNull(createdCorporateAccount);
        assertEquals("CDP1", createdCorporateAccount.getCdpId());
        assertEquals("Company A", createdCorporateAccount.getCompanyName());
        assertEquals(corporateContract, createdCorporateAccount.getCorporateContract());
    }
}
*/
