/*
package com.secret.platform.res_rates;

import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.rate_product.RateProduct;

import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.res_rates.ResRatesDTO;
import com.secret.platform.res_rates.ResRatesResponseDTO;
import com.secret.platform.class_code.ClassCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResRatesServiceTest {

    @Mock
    private CorporateAccountRepository corporateAccountRepository;

    @Mock
    private CorporateContractRepository corporateContractRepository;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private ClassCodeRepository classCodeRepository;

    @InjectMocks
    private ResRatesService resRatesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpDefaultMocks();
    }

    private void setUpDefaultMocks() {
        // Mock default CorporateAccount
        CorporateContract corporateContract = new CorporateContract();
        corporateContract.setId(1L);
        corporateContract.setRateProduct(createRateProduct());

        CorporateAccount corporateAccount = new CorporateAccount();
        corporateAccount.setId(1L);
        corporateAccount.setCdpId("MYWEB1");
        corporateAccount.setCorporateContract(corporateContract);

        when(corporateAccountRepository.findByCdpId("MYWEB1")).thenReturn(Optional.of(corporateAccount));
    }

    private RateProduct createRateProduct() {
        RateProduct rateProduct = new RateProduct();
        rateProduct.setId(1L);
        rateProduct.setProduct("TestProduct");
        rateProduct.setCurrency("USD");
        rateProduct.setClassCodes(Arrays.asList(createClassCode("XXAR"), createClassCode("MCAR")));
        return rateProduct;
    }

    private ClassCode createClassCode(String code) {
        ClassCode classCode = new ClassCode();
        classCode.setClassCode(code);
        return classCode;
    }

    @Test
    void testGetRates() {
        ResRatesDTO resRatesDTO = new ResRatesDTO();
        resRatesDTO.setCorpRateID("MYWEB1");

        ResRatesResponseDTO response = resRatesService.getRates(resRatesDTO);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(1, response.getCount());
        assertNotNull(response.getRates());
        assertEquals(1, response.getRates().size());

        ResRatesResponseDTO.Rate rate = response.getRates().get(0);
        assertEquals("1", rate.getRateID());
        assertEquals("Available", rate.getAvailability());
        assertEquals("XXAR, MCAR", rate.getClassCode());
        assertEquals("USD", rate.getCurrencyCode());
        assertEquals(0.0, rate.getEstimate());
        assertEquals(0.0, rate.getRateOnlyEstimate());
        assertNotNull(rate.getDropCharge());
        assertEquals("renter", rate.getDropCharge().getResponsibility());
        assertEquals(0.0, rate.getDropCharge().getAmount());
        assertNotNull(rate.getDistance());
        assertEquals("unlimited", rate.getDistance().getIncluded());
        assertEquals(0, rate.getLiability());
        assertFalse(rate.isPrePaid());
        assertTrue(rate.getAlternateRateProducts().isEmpty());

        verify(corporateAccountRepository, times(1)).findByCdpId("MYWEB1");
        verify(rateProductRepository, times(0)).findById(anyLong());
    }
}
*/
