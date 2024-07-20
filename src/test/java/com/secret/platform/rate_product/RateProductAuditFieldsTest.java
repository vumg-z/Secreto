package com.secret.platform.rate_product;

import com.secret.platform.option_set.OptionSet;
import com.secret.platform.option_set.OptionSetRepository;
import com.secret.platform.options.OptionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateProductAuditFieldsTest {

    @InjectMocks
    private RateProductServiceImpl rateProductService;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private OptionsServiceImpl optionsService;

    @Mock
    private OptionSetRepository optionSetRepository;

    private RateProduct existingRateProduct;
    private RateProduct rateProductDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        existingRateProduct = RateProduct.builder()
                .id(1L)
                .rateSet("Old Rate Set")
                .product("Old Product")
                .effPkupDate(new Date())
                .effPkupTime("10:00")
                .mustPkupBefore("18:00")
                .comment("Old Comment")
                .rateType("Daily")
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
                .inclOptSet(new OptionSet())
                .currency("USD")
                .paidFreeDay("4/1")
                .modDate(new Date())
                .modTime(1000.0f)
                .modEmpl("OldUser")
                .empl("User")
                .build();

        rateProductDetails = RateProduct.builder()
                .rateSet("New Rate Set")
                .product("New Product")
                .effPkupDate(new Date())
                .effPkupTime("12:00")
                .mustPkupBefore("18:00")
                .comment("New Comment")
                .rateType("Weekly")
                .inclCvg1(false)
                .inclCvg2(true)
                .inclCvg3(true)
                .inclCvg4(false)
                .unused(12.0f)
                .milesMeth(18.0f)
                .week(220.0f)
                .extraWeek(190.0f)
                .freeMilesHour(55.0f)
                .graceMinutes(25)
                .chargeForGrace(false)
                .discountable(false)
                .editable(false)
                .minDaysForWeek(5)
                .periodMaxDays(25)
                .daysPerMonth(30)
                .commYn("N")
                .commCat("B")
                .inclOptSet(new OptionSet())
                .currency("EUR")
                .paidFreeDay("5/1")
                .empl("NewUser")
                .build();
    }

    @Test
    void testUpdateRateProductAuditFields() throws IllegalAccessException {
        when(rateProductRepository.findById(1L)).thenReturn(Optional.of(existingRateProduct));
        when(rateProductRepository.save(any(RateProduct.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RateProduct updatedRateProduct = rateProductService.updateRateProduct(1L, rateProductDetails, new HashMap<>());

        assertNotNull(updatedRateProduct.getModDate());
        assertNotNull(updatedRateProduct.getModTime());
        assertEquals("MOD_USER", updatedRateProduct.getModEmpl());

        verify(rateProductRepository, times(1)).findById(1L);
        verify(rateProductRepository, times(1)).save(any(RateProduct.class));
    }
}
