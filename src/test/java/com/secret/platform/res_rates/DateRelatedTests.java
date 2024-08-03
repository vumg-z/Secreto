package com.secret.platform.res_rates;

import com.secret.platform.rate_product.RateProduct;
import com.secret.platform.class_code.ClassCode;
import com.secret.platform.exception.InvalidDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DateRelatedTests {

    private ResRatesService resRatesService;
    private RateProduct rateProduct;
    private ClassCode xxarClassCode;

    @BeforeEach
    void setUp() {
        resRatesService = new ResRatesService(null, null, null, null, null);
        rateProduct = createRateProduct();
    }

    private RateProduct createRateProduct() {
        RateProduct rateProduct = new RateProduct();
        rateProduct.setId(1L);
        rateProduct.setProduct("TestProduct");
        rateProduct.setCurrency("USD");
        xxarClassCode = createClassCode("XXAR", 36.44, 255.06, 1093.13, 36.44, 12.15, 0.0);
        rateProduct.setClassCodes(Arrays.asList(xxarClassCode, createClassCode("MCAR", 30.00, 200.00, 900.00, 30.00, 10.00, 0.0)));
        return rateProduct;
    }

    private ClassCode createClassCode(String code, double dayRate, double weekRate, double monthRate, double xDayRate, double hourRate, double mileRate) {
        ClassCode classCode = new ClassCode();
        classCode.setClassCode(code);
        classCode.setDayRate(dayRate);
        classCode.setWeekRate(weekRate);
        classCode.setMonthRate(monthRate);
        classCode.setXDayRate(xDayRate);
        classCode.setHourRate(hourRate);
        classCode.setMileRate(mileRate);
        return classCode;
    }

    @Test
    void testCalculateEstimateForOneWeek() {
        LocalDateTime pickupDateTime = LocalDateTime.of(2024, 8, 5, 10, 0);
        LocalDateTime returnDateTime = LocalDateTime.of(2024, 8, 12, 10, 0);

        double estimate = resRatesService.calculateEstimate(xxarClassCode, pickupDateTime, returnDateTime);

        double expectedEstimate = xxarClassCode.getWeekRate();

        assertEquals(expectedEstimate, estimate);
    }

    @Test
    void testCalculateEstimateForDifferentDuration() {
        LocalDateTime pickupDateTime = LocalDateTime.of(2024, 8, 5, 10, 0);
        LocalDateTime returnDateTime = LocalDateTime.of(2024, 8, 10, 10, 0);

        double estimate = resRatesService.calculateEstimate(xxarClassCode, pickupDateTime, returnDateTime);

        double expectedEstimate = 5 * xxarClassCode.getDayRate();

        assertEquals(expectedEstimate, estimate);
    }

    @Test
    void testCalculateEstimateForTwoWeeks() {
        LocalDateTime pickupDateTime = LocalDateTime.of(2024, 8, 1, 10, 0);
        LocalDateTime returnDateTime = LocalDateTime.of(2024, 8, 15, 10, 0);

        double estimate = resRatesService.calculateEstimate(xxarClassCode, pickupDateTime, returnDateTime);

        double expectedEstimate = 2 * xxarClassCode.getWeekRate();

        assertEquals(expectedEstimate, estimate);
    }

    @Test
    void testInvalidPickupDate() {
        LocalDateTime pickupDateTime = LocalDateTime.now().minusDays(1);
        LocalDateTime returnDateTime = LocalDateTime.now().plusDays(1);

        assertThrows(InvalidDateException.class, () -> {
            resRatesService.validateDates(pickupDateTime, returnDateTime);
        });
    }

    @Test
    void testInvalidReturnDate() {
        LocalDateTime pickupDateTime = LocalDateTime.now().plusDays(1);
        LocalDateTime returnDateTime = LocalDateTime.now();

        assertThrows(InvalidDateException.class, () -> {
            resRatesService.validateDates(pickupDateTime, returnDateTime);
        });
    }

    @Test
    void testPickupDateWithinOneHour() {
        LocalDateTime pickupDateTime = LocalDateTime.now().plusMinutes(30);
        LocalDateTime returnDateTime = LocalDateTime.now().plusDays(1);

        assertThrows(InvalidDateException.class, () -> {
            resRatesService.validateDates(pickupDateTime, returnDateTime);
        });
    }

    @Test
    void testReturnDateBeforePickupDate() {
        LocalDateTime pickupDateTime = LocalDateTime.now().plusDays(2);
        LocalDateTime returnDateTime = LocalDateTime.now().plusDays(1);

        assertThrows(InvalidDateException.class, () -> {
            resRatesService.validateDates(pickupDateTime, returnDateTime);
        });
    }
}
