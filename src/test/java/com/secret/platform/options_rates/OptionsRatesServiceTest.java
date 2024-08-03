package com.secret.platform.options_rates;

import com.secret.platform.options.*;
import com.secret.platform.pricing_code.PricingCode;
import com.secret.platform.pricing_code.PricingCodeRepository;
import com.secret.platform.privilege_code.PrivilegeCode;
import com.secret.platform.privilege_code.PrivilegeCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class OptionsRatesServiceTest {

    @InjectMocks
    private OptionsRatesService optionsRatesService;

    @Mock
    private OptionsRepository optionsRepository;

    @Mock
    private OptionsRatesRepository optionsRatesRepository;

    @Mock
    private PricingCodeRepository pricingCodeRepository;

    @Mock
    private PrivilegeCodeRepository privilegeCodeRepository;

    private OptionsRates optionsRates;
    private PricingCode pricingCode;
    private PrivilegeCode privilegeCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        optionsRates = OptionsRates.builder()
                .optionCode("OPT123")
                .locationCode("LOC01")
                .currency("USD")
                .primaryRate(100.0)
                .weeklyRate(700.0)
                .monthlyRate(3000.0)
                .xdayRate(10.0)
                .build();

        pricingCode = PricingCode.builder()
                .id(1L)
                .code("PRC123")
                .description("Pricing Code 123")
                .build();

        privilegeCode = PrivilegeCode.builder()
                .id(1L)
                .code("PVC123")
                .description("Privilege Code 123")
                .build();
    }

    @Test
    public void testAddRateToOption_OptionNotFound() {
        when(optionsRepository.findByOptionCode(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            optionsRatesService.addRateToOption("OPT123", "LOC01", "USD", 100.0, 700.0, 3000.0, 10.0, "LCO01", "PRC123");
        });
    }

    @Test
    public void testAddRateToOption_PricingCodeNotFound() {
        when(optionsRepository.findByOptionCode(anyString())).thenReturn(Optional.of(new Options()));
        when(pricingCodeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            optionsRatesService.addRateToOption("OPT123", "LOC01", "USD", 100.0, 700.0, 3000.0, 10.0, "LCO01", "PRC123");
        });
    }

    @Test
    public void testAddRateToOption_PrivilegeCodeNotFound() {
        when(optionsRepository.findByOptionCode(anyString())).thenReturn(Optional.of(new Options()));
        when(pricingCodeRepository.findById(anyLong())).thenReturn(Optional.of(pricingCode));
        when(privilegeCodeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            optionsRatesService.addRateToOption("OPT123", "LOC01", "USD", 100.0, 700.0, 3000.0, 10.0, "LCO01", "PRC123");
        });
    }

    @Test
    public void testAddRateToOption_Success() {
        when(optionsRepository.findByOptionCode(anyString())).thenReturn(Optional.of(new Options()));
        when(pricingCodeRepository.findByCode(anyString())).thenReturn(Optional.of(pricingCode));
        when(privilegeCodeRepository.findByCode(anyString())).thenReturn(Optional.of(privilegeCode));
        when(optionsRatesRepository.save(any(OptionsRates.class))).thenReturn(optionsRates);

        OptionsRates newRate = optionsRatesService.addRateToOption("OPT123", "LOC01", "USD", 100.0, 700.0, 3000.0, 10.0, "PRC123", "PVC123");

        verify(optionsRepository, times(1)).findByOptionCode(anyString());
        verify(pricingCodeRepository, times(1)).findByCode(anyString());
        verify(privilegeCodeRepository, times(1)).findByCode(anyString());
        verify(optionsRatesRepository, times(1)).save(any(OptionsRates.class));
    }


    @Test
    public void testFindRatesByCriteria_Success() {
        when(privilegeCodeRepository.findByCode(anyString())).thenReturn(Optional.of(privilegeCode));
        when(pricingCodeRepository.findByCode(anyString())).thenReturn(Optional.of(pricingCode));
        when(optionsRatesRepository.findByOptionCodeAndPrivilegeCodeAndPricingCode(anyString(), any(PrivilegeCode.class), any(PricingCode.class)))
                .thenReturn(List.of(optionsRates));

        List<OptionsRates> rates = optionsRatesService.findRatesByCriteria("OPT123", "PVC123", "PRC123");

        verify(privilegeCodeRepository, times(1)).findByCode(anyString());
        verify(pricingCodeRepository, times(1)).findByCode(anyString());
        verify(optionsRatesRepository, times(1)).findByOptionCodeAndPrivilegeCodeAndPricingCode(anyString(), any(PrivilegeCode.class), any(PricingCode.class));

        assertEquals(1, rates.size());
        assertEquals("OPT123", rates.get(0).getOptionCode());
    }

    @Test
    public void testFindRatesByCriteria_PricingCodeNotFound() {
        when(privilegeCodeRepository.findById(anyLong())).thenReturn(Optional.of(privilegeCode));
        when(pricingCodeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            optionsRatesService.findRatesByCriteria("OPT123", "PVC123", "PVC123");
        });
    }

    @Test
    public void testFindRatesByCriteria_PrivilegeCodeNotFound() {
        when(privilegeCodeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            optionsRatesService.findRatesByCriteria("OPT123", "PVC123", "PRC123");
        });
    }
}
