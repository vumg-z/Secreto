package com.secret.platform.res_estimates;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.corporate_account.CorporateAccount;
import com.secret.platform.corporate_account.CorporateAccountRepository;
import com.secret.platform.corporate_contract.CorporateContract;
import com.secret.platform.exception.CorporateRateNotFoundException;
import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.rate_product.RateProductService;
import com.secret.platform.resEstimates.ResEstimatesDTO;
import com.secret.platform.resEstimates.ResEstimatesResponseDTO;
import com.secret.platform.resEstimates.ResEstimatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResEstimatesServiceTestComplete {

    @InjectMocks
    private ResEstimatesService resEstimatesService;

    @Mock
    private RateProductService rateProductService;

    @Mock
    private CorporateAccountRepository corporateAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEstimates() {
        // Setup
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 5, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 8, 10, 10, 0)));
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        CorporateAccount corporateAccount = new CorporateAccount();
        CorporateContract corporateContract = new CorporateContract();
        corporateContract.setRateProduct("Standard Product");
        corporateAccount.setCorporateContract(corporateContract);

        ClassCode classCode1 = ClassCode.builder()
                .classCode("XXAR")
                .dayRate(36.44)
                .weekRate(255.06)
                .monthRate(1093.13)
                .xDayRate(36.44)
                .hourRate(12.15)
                .mileRate(0.0)
                .build();

        ClassCode classCode2 = ClassCode.builder()
                .classCode("YYBR")
                .dayRate(40.00)
                .weekRate(280.00)
                .monthRate(1200.00)
                .xDayRate(40.00)
                .hourRate(15.00)
                .mileRate(0.0)
                .build();

        ClassCode classCode3 = ClassCode.builder()
                .classCode("ZZCR")
                .dayRate(50.00)
                .weekRate(300.00)
                .monthRate(1300.00)
                .xDayRate(50.00)
                .hourRate(20.00)
                .mileRate(0.0)
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .product("Standard Product")
                .classCodes(new ArrayList<>())
                .build();
        rateProduct.getClassCodes().add(classCode1);
        rateProduct.getClassCodes().add(classCode2);
        rateProduct.getClassCodes().add(classCode3);

        when(corporateAccountRepository.findByCdpId(anyString()))
                .thenReturn(Optional.of(corporateAccount));

        when(rateProductService.getSpecificRateProduct(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(rateProduct));

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Additional assertions based on response
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getResEstimate(), "ResEstimate should not be null");
        assertNotNull(response.getResEstimate().getRenterEstimate(), "RenterEstimate should not be null");

        // Add assertions to check the estimate
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = response.getResEstimate().getRenterEstimate();
        assertEquals("182.20", renterEstimate.getTotal(), "The total estimate should match");
        assertEquals(1, renterEstimate.getCharges().size(), "There should be one charge");
        assertEquals("XDAYS", renterEstimate.getCharges().get(0).getDescription(), "Charge description should be XDAYS");
        assertEquals("5", renterEstimate.getCharges().get(0).getQuantity(), "Quantity should be 5");
    }

    @Test
    void testGetEstimates_CorporateRateNotFoundException() {
        // Setup
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 5, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 8, 10, 10, 0)));
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", null));

        when(corporateAccountRepository.findByCdpId(anyString()))
                .thenReturn(Optional.empty());

        // Execute & Verify
        assertThrows(CorporateRateNotFoundException.class, () -> {
            resEstimatesService.getEstimates(resEstimatesDTO);
        });

        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(0)).getSpecificRateProduct(anyString(), anyString(), anyString());
    }

    @Test
    void testGetEstimates_WeeklyRate() {
        // Setup
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 1, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 8, 8, 10, 0))); // 7 days
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        CorporateAccount corporateAccount = new CorporateAccount();
        CorporateContract corporateContract = new CorporateContract();
        corporateContract.setRateProduct("Standard Product");
        corporateAccount.setCorporateContract(corporateContract);

        ClassCode classCode1 = ClassCode.builder()
                .classCode("XXAR")
                .dayRate(36.44)
                .weekRate(255.06)
                .monthRate(1093.13)
                .xDayRate(36.44)
                .hourRate(12.15)
                .mileRate(0.0)
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .product("Standard Product")
                .classCodes(new ArrayList<>())
                .build();
        rateProduct.getClassCodes().add(classCode1);

        when(corporateAccountRepository.findByCdpId(anyString()))
                .thenReturn(Optional.of(corporateAccount));

        when(rateProductService.getSpecificRateProduct(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(rateProduct));

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Additional assertions based on response
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = response.getResEstimate().getRenterEstimate();
        assertEquals("255.06", renterEstimate.getTotal(), "The total estimate should match for 1 week");
        assertEquals(1, renterEstimate.getCharges().size(), "There should be one charge");
        assertEquals("WEEKS", renterEstimate.getCharges().get(0).getDescription(), "Charge description should be WEEKS");
        assertEquals("1", renterEstimate.getCharges().get(0).getQuantity(), "Quantity should be 1");
    }

    @Test
    void testGetEstimates_MonthlyRate() {
        // Setup
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 1, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 9, 1, 10, 0))); // 31 days
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        CorporateAccount corporateAccount = new CorporateAccount();
        CorporateContract corporateContract = new CorporateContract();
        corporateContract.setRateProduct("Standard Product");
        corporateAccount.setCorporateContract(corporateContract);

        ClassCode classCode1 = ClassCode.builder()
                .classCode("XXAR")
                .dayRate(36.44)
                .weekRate(255.06)
                .monthRate(1093.13)
                .xDayRate(36.44)
                .hourRate(12.15)
                .mileRate(0.0)
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .product("Standard Product")
                .classCodes(new ArrayList<>())
                .build();
        rateProduct.getClassCodes().add(classCode1);

        when(corporateAccountRepository.findByCdpId(anyString()))
                .thenReturn(Optional.of(corporateAccount));

        when(rateProductService.getSpecificRateProduct(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(rateProduct));

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Additional assertions based on response
        ResEstimatesResponseDTO.RenterEstimate renterEstimate = response.getResEstimate().getRenterEstimate();
        assertEquals("1129.57", renterEstimate.getTotal(), "The total estimate should match for 1 month and 1 day");
        assertEquals(2, renterEstimate.getCharges().size(), "There should be two charges");
        assertEquals("MONTHS", renterEstimate.getCharges().get(0).getDescription(), "Charge description should be MONTHS");
        assertEquals("1", renterEstimate.getCharges().get(0).getQuantity(), "Quantity should be 1");
        assertEquals("XDAYS", renterEstimate.getCharges().get(1).getDescription(), "Charge description should be XDAYS");
        assertEquals("1", renterEstimate.getCharges().get(1).getQuantity(), "Quantity should be 1");
    }
}
