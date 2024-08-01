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
        resEstimatesDTO.setQuotedRate(new ResEstimatesDTO.QuotedRate(null, "CORP123", null));

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

        // Additional assertions can be added based on response
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
}
