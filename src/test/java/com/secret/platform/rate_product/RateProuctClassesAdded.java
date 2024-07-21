package com.secret.platform.rate_product;

import com.secret.platform.class_code.ClassCode;
import com.secret.platform.class_code.ClassCodeRepository;
import com.secret.platform.location.Location;
import com.secret.platform.rate_set.RateSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateProductClassesAddedTest {
    private static final Logger logger = LoggerFactory.getLogger(RateProductServiceImpl.class);

    @InjectMocks
    private RateProductServiceImpl rateProductService;

    @Mock
    private RateProductRepository rateProductRepository;

    @Mock
    private ClassCodeRepository classCodeRepository;

    private Location defaultLocation;
    private List<ClassCode> classCodes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        RateSet airportRateSet = RateSet.builder()
                .id(1L)
                .rateSetCode("1")
                .description("Airport Rate Set")
                .build();

        defaultLocation = Location.builder()
                .id(1L)
                .locationNumber("DEFAULT")
                .rateSet(airportRateSet)
                .build();

        RateProduct airportRateProduct = RateProduct.builder()
                .id(1L)
                .rateSet(airportRateSet)
                .product("AirportRateProduct")
                .dayRate(50.0f)
                .weekRate(300.0f)
                .monthRate(1200.0f)
                .xDayRate(50.0f)
                .hourRate(15.0f)
                .build();

        ClassCode airportXXAR = ClassCode.builder()
                .id(1L)
                .location(defaultLocation)
                .rateProduct(airportRateProduct)
                .classCode("XXAR")
                .description("Description for XXAR")
                .dayRate(50.0f)
                .weekRate(300.0f)
                .monthRate(1200.0f)
                .xDayRate(50.0f)
                .hourRate(15.0f)
                .build();

        classCodes = Arrays.asList(airportXXAR);

        when(classCodeRepository.findAllByLocation(defaultLocation)).thenReturn(classCodes);
    }

    @Test
    void testLoadAllClassCodesForDefaultLocation() {
        logger.info("Starting test: testLoadAllClassCodesForDefaultLocation");

        List<ClassCode> classCodesResult = classCodeRepository.findAllByLocation(defaultLocation);

        logger.info("Retrieved {} class codes for location {}", classCodesResult.size(), defaultLocation.getLocationNumber());

        for (ClassCode classCode : classCodesResult) {
            logger.info("Class Code: {}, Day Rate: {}, Week Rate: {}, Month Rate: {}, XDay Rate: {}, Hour Rate: {}",
                    classCode.getClassCode(), classCode.getDayRate(), classCode.getWeekRate(), classCode.getMonthRate(), classCode.getXDayRate(), classCode.getHourRate());
        }

        assertEquals(1, classCodesResult.size());
        assertEquals("XXAR", classCodesResult.get(0).getClassCode());
        assertEquals(50.0f, classCodesResult.get(0).getDayRate());
        assertEquals(300.0f, classCodesResult.get(0).getWeekRate());
        assertEquals(1200.0f, classCodesResult.get(0).getMonthRate());
        assertEquals(50.0f, classCodesResult.get(0).getXDayRate());
        assertEquals(15.0f, classCodesResult.get(0).getHourRate());

        verify(classCodeRepository, times(1)).findAllByLocation(defaultLocation);

        logger.info("Test completed: testLoadAllClassCodesForDefaultLocation");
    }

    @Test
    void getAllClassesWithRatesSearchingForLocation() {
        logger.info("Starting test: getAllClassesWithRatesSearchingForLocation");

        List<ClassCode> airportResult = rateProductService.getAllClassesWithRatesByLocation(defaultLocation);

        logger.info("Retrieved {} class codes for airport location {}", airportResult.size(), defaultLocation.getLocationNumber());

        for (ClassCode classCode : airportResult) {
            logger.info("Airport - Class Code: {}, Day Rate: {}, Week Rate: {}, Month Rate: {}, XDay Rate: {}, Hour Rate: {}",
                    classCode.getClassCode(), classCode.getDayRate(), classCode.getWeekRate(), classCode.getMonthRate(), classCode.getXDayRate(), classCode.getHourRate());
        }

        assertEquals(1, airportResult.size());
        ClassCode airportXXARResult = airportResult.get(0);
        assertEquals("XXAR", airportXXARResult.getClassCode());
        assertEquals(50.0f, airportXXARResult.getDayRate());
        assertEquals(300.0f, airportXXARResult.getWeekRate());
        assertEquals(1200.0f, airportXXARResult.getMonthRate());
        assertEquals(50.0f, airportXXARResult.getXDayRate());
        assertEquals(15.0f, airportXXARResult.getHourRate());

        verify(classCodeRepository, times(1)).findAllByLocation(defaultLocation);

        logger.info("Test completed: getAllClassesWithRatesSearchingForLocation");
    }
}
