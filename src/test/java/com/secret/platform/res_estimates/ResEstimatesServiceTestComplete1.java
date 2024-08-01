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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResEstimatesServiceTestComplete1 {

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
    void testGetEstimatesForDaysOnly() {
        // Setup for 5 days rental
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 1, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 8, 6, 10, 0))); // 5 days
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        setupMocksForEstimates("Standard Product", "XXAR", 36.44, 255.06, 1093.13);

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Assertions
        assertNotNull(response);
        assertNotNull(response.getResEstimate());
        assertNotNull(response.getResEstimate().getRenterEstimate());
        assertEquals("182.20", response.getResEstimate().getRenterEstimate().getTotal()); // 5 days * 36.44
        assertEquals(1, response.getResEstimate().getRenterEstimate().getCharges().size());
        assertEquals("XDAYS", response.getResEstimate().getRenterEstimate().getCharges().get(0).getDescription());
        assertEquals("5", response.getResEstimate().getRenterEstimate().getCharges().get(0).getQuantity());
    }

    @Test
    void testGetEstimatesForWeeksOnly() {
        // Setup for 7 days rental, expecting 1 week
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 1, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 8, 8, 10, 0))); // 7 days
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        setupMocksForEstimates("Standard Product", "XXAR", 36.44, 255.06, 1093.13);

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Assertions
        assertNotNull(response);
        assertNotNull(response.getResEstimate());
        assertNotNull(response.getResEstimate().getRenterEstimate());
        assertEquals("255.06", response.getResEstimate().getRenterEstimate().getTotal()); // 1 week
        assertEquals(1, response.getResEstimate().getRenterEstimate().getCharges().size());
        assertEquals("WEEKS", response.getResEstimate().getRenterEstimate().getCharges().get(0).getDescription());
        assertEquals("1", response.getResEstimate().getRenterEstimate().getCharges().get(0).getQuantity());
    }

    @Test
    void testGetEstimatesForMonthsAndDays() {
        // Setup for 31 days rental, expecting 1 month and 1 day
        ResEstimatesDTO resEstimatesDTO = new ResEstimatesDTO();
        resEstimatesDTO.setPickup(new ResEstimatesDTO.Pickup("LAX", LocalDateTime.of(2024, 8, 1, 10, 0)));
        resEstimatesDTO.setReturnInfo(new ResEstimatesDTO.Return("LAX", LocalDateTime.of(2024, 9, 1, 10, 0))); // 31 days
        resEstimatesDTO.setSource("US");
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", "XXAR"));

        setupMocksForEstimates("Standard Product", "XXAR", 36.44, 255.06, 1093.13);

        // Execute
        ResEstimatesResponseDTO response = resEstimatesService.getEstimates(resEstimatesDTO);

        // Verify
        verify(corporateAccountRepository, times(1)).findByCdpId(anyString());
        verify(rateProductService, times(1)).getSpecificRateProduct(anyString(), anyString(), anyString());

        // Assertions
        assertNotNull(response);
        assertNotNull(response.getResEstimate());
        assertNotNull(response.getResEstimate().getRenterEstimate());
        assertEquals("1129.57", response.getResEstimate().getRenterEstimate().getTotal()); // 1 month + 1 day
        assertEquals(2, response.getResEstimate().getRenterEstimate().getCharges().size());
        assertEquals("MONTHS", response.getResEstimate().getRenterEstimate().getCharges().get(0).getDescription());
        assertEquals("1", response.getResEstimate().getRenterEstimate().getCharges().get(0).getQuantity());
        assertEquals("XDAYS", response.getResEstimate().getRenterEstimate().getCharges().get(1).getDescription());
        assertEquals("1", response.getResEstimate().getRenterEstimate().getCharges().get(1).getQuantity());
    }

    private void setupMocksForEstimates(String rateProductName, String classCode, double dayRate, double weekRate, double monthRate) {
        CorporateAccount corporateAccount = new CorporateAccount();
        CorporateContract corporateContract = new CorporateContract();
        corporateContract.setRateProduct(rateProductName);
        corporateAccount.setCorporateContract(corporateContract);

        ClassCode classCodeObj = ClassCode.builder()
                .classCode(classCode)
                .dayRate(dayRate)
                .weekRate(weekRate)
                .monthRate(monthRate)
                .xDayRate(dayRate)
                .hourRate(12.15)
                .mileRate(0.0)
                .build();

        RateProduct rateProduct = RateProduct.builder()
                .product(rateProductName)
                .classCodes(new ArrayList<>())
                .build();
        rateProduct.getClassCodes().add(classCodeObj);

        when(corporateAccountRepository.findByCdpId(anyString()))
                .thenReturn(Optional.of(corporateAccount));

        when(rateProductService.getSpecificRateProduct(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(rateProduct));
    }
}
