package com.secret.platform.res_rates;

import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.corporate_contract.CorporateContractRepository;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductRepository;
import com.secret.platform.rate_product.RateProductServiceImpl;
import com.secret.platform.class_code.ClassCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
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
    private RateProductServiceImpl rateProductService;

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
        corporateContract.setRateProduct("TestProduct"); // Use the product name as a string

        CorporateAccount corporateAccount = new CorporateAccount();
        corporateAccount.setId(1L);
        corporateAccount.setCdpId("MYWEB1");
        corporateAccount.setCorporateContract(corporateContract);

        when(corporateAccountRepository.findByCdpId("MYWEB1")).thenReturn(Optional.of(corporateAccount));

        // Mock the RateProduct retrieval
        RateProduct rateProduct = createRateProduct();
        when(rateProductService.getSpecificRateProduct(eq("GDLMY1"), eq("US"), eq("TestProduct")))
                .thenReturn(Optional.of(rateProduct));
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

        // Set up pickup and return details in resRatesDTO
        ResRatesDTO.PickupDTO pickup = new ResRatesDTO.PickupDTO();
        pickup.setLocationCode("GDLMY1");
        pickup.setDateTime(LocalDateTime.of(2024, 8, 18, 10, 0));
        resRatesDTO.setPickup(pickup);

        ResRatesDTO.ReturnDTO returnInfo = new ResRatesDTO.ReturnDTO();
        returnInfo.setLocationCode("GDLMY1");
        returnInfo.setDateTime(LocalDateTime.of(2024, 8, 20, 10, 0));
        resRatesDTO.setReturnInfo(returnInfo);

        resRatesDTO.setCountryCode("US");
        resRatesDTO.setEstimateType(3);

        ResRatesResponseDTO response = resRatesService.getRates(resRatesDTO);

        assertNotNull(response);
        assertTrue(response.getResRates().isSuccess());
        assertEquals(2, response.getResRates().getCount());
        assertNotNull(response.getResRates().getRates());
        assertEquals(2, response.getResRates().getRates().size());

        ResRatesResponseDTO.Rate rate1 = response.getResRates().getRates().get(0);
        assertEquals("1", rate1.getRateID());
        assertEquals("Available", rate1.getAvailability());
        assertEquals("XXAR", rate1.getClassCode());
        assertEquals("USD", rate1.getCurrencyCode());
        // TODO: 7/28/2024  // assertEquals(72.88, rate1.getEstimate());
        // TODO: 7/28/2024  //assertEquals(72.88, rate1.getRateOnlyEstimate());
        assertNotNull(rate1.getDropCharge());
        assertEquals("renter", rate1.getDropCharge().getResponsibility());
        assertEquals(0.0, rate1.getDropCharge().getAmount());
        assertNotNull(rate1.getDistance());
        assertEquals("unlimited", rate1.getDistance().getIncluded());
        assertEquals(0, rate1.getLiability());
        assertFalse(rate1.isPrePaid());
        assertTrue(rate1.getAlternateRateProducts().isEmpty());

        ResRatesResponseDTO.Rate rate2 = response.getResRates().getRates().get(1);
        assertEquals("1", rate2.getRateID());
        assertEquals("Available", rate2.getAvailability());
        assertEquals("MCAR", rate2.getClassCode());
        assertEquals("USD", rate2.getCurrencyCode());
        //assertEquals(72.88, rate2.getEstimate());
        //todo update//assertEquals(72.88, rate2.getRateOnlyEstimate());
        assertNotNull(rate2.getDropCharge());
        assertEquals("renter", rate2.getDropCharge().getResponsibility());
        assertEquals(0.0, rate2.getDropCharge().getAmount());
        assertNotNull(rate2.getDistance());
        assertEquals("unlimited", rate2.getDistance().getIncluded());
        assertEquals(0, rate2.getLiability());
        assertFalse(rate2.isPrePaid());
        assertTrue(rate2.getAlternateRateProducts().isEmpty());

        verify(corporateAccountRepository, times(1)).findByCdpId("MYWEB1");
        verify(rateProductService, times(1)).getSpecificRateProduct(eq("GDLMY1"), eq("US"), eq("TestProduct"));
    }
}
