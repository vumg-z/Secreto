package com.secret.platform.location;

import com.secret.platform.config.FeatureFlagServiceInterface;
import com.secret.platform.general_ledger.GeneralLedger;
import com.secret.platform.general_ledger.GeneralLedgerRepository;
import com.secret.platform.group_code.GroupCodes;
import com.secret.platform.group_code.GroupCodesRepository;
import com.secret.platform.status_code.StatusCode;
import com.secret.platform.status_code.StatusCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationGroupCodesTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private GeneralLedgerRepository generalLedgerRepository;

    @Mock
    private StatusCodeRepository statusCodeRepository;

    @Mock
    private FeatureFlagServiceInterface featureFlagService;

    @Mock
    private GroupCodesRepository groupCodesRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the default status code
        StatusCode defaultStatus = new StatusCode();
        defaultStatus.setCode("A");
        when(statusCodeRepository.findByCode("A")).thenReturn(Optional.of(defaultStatus));

        // Mock the general ledger account
        GeneralLedger defaultLedger = new GeneralLedger();
        defaultLedger.setId(1L);
        defaultLedger.setMainAcct("1130");
        defaultLedger.setSubAcct("4");
        when(generalLedgerRepository.findById(1L)).thenReturn(Optional.of(defaultLedger));
        when(generalLedgerRepository.existsById(1L)).thenReturn(true);

        // Mock the feature flag service
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);
    }

    @Test
    void testSaveLocationWithValidMetroplexGroup() {
        StatusCode statusCode = new StatusCode();
        statusCode.setCode("A");

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setId(1L);

        GroupCodes groupCode = new GroupCodes();
        groupCode.setId(1L);

        Location location = new Location();
        location.setLocationNumber("PVREY1");
        location.setLocationName("PVR ECONOMY 1");
        location.setAddressLine1("Blv. Fco Medina Ascencio");
        location.setAddressLine2("Puerto Vallarta");
        location.setAddressLine3("48335");
        location.setPhone("3222212413");
        location.setProfitCenterNumber("001");
        location.setDoFuelCalc("Y");
        location.setHoldingDrawer("202");
        location.setAutoVehicleSelect("Y");
        location.setCheckOutFuel("8");
        location.setValidRentalLoc("Y");
        location.setCheckInStatus(statusCode);
        //location.setInterOfcArAcct(generalLedger);
        location.setMetroplexLocation(groupCode);
        location.setAllowMultiLanguageRa("Y");
        location.setAllowWaitRas("Y");

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(statusCodeRepository.existsByCode("A")).thenReturn(true);
        when(generalLedgerRepository.existsById(1L)).thenReturn(true);
        when(groupCodesRepository.existsById(1L)).thenReturn(true);
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        assertEquals("A", savedLocation.getCheckInStatus().getCode());
       // assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
        assertEquals("Y", savedLocation.getAllowMultiLanguageRa());
        assertEquals("Y", savedLocation.getAllowWaitRas());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
//        verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithInvalidMetroplexGroup() {
        StatusCode statusCode = new StatusCode();
        statusCode.setCode("A");

        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setId(1L);

        GroupCodes groupCode = new GroupCodes();
        groupCode.setId(1L);

        Location location = new Location();
        location.setLocationNumber("PVREY2");
        location.setLocationName("PVR ECONOMY 2");
        location.setAddressLine1("Blv. Fco Medina Ascencio");
        location.setAddressLine2("Puerto Vallarta");
        location.setAddressLine3("48335");
        location.setPhone("3222212414");
        location.setProfitCenterNumber("002");
        location.setDoFuelCalc("Y");
        location.setHoldingDrawer("203");
        location.setAutoVehicleSelect("Y");
        location.setCheckOutFuel("8");
        location.setValidRentalLoc("Y");
        location.setCheckInStatus(statusCode);
        //location.setInterOfcArAcct(generalLedger);
        location.setMetroplexLocation(groupCode);
        location.setAllowMultiLanguageRa("Y");
        location.setAllowWaitRas("Y");

        when(locationRepository.findByLocationNumber("PVREY2")).thenReturn(Optional.empty());
        when(statusCodeRepository.existsByCode("A")).thenReturn(true);
        when(generalLedgerRepository.existsById(1L)).thenReturn(true);
        when(groupCodesRepository.existsById(1L)).thenReturn(false);

        /*
        assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(location);
        });
         */


//        verify(locationRepository, times(1)).findByLocationNumber("PVREY2");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
    //    verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, never()).save(location);
    }

    @Test
    void testFindLocationsByGroupCode() {
        GroupCodes groupCode = new GroupCodes();
        groupCode.setId(1L);
        groupCode.setCode("+TEST");

        Location location1 = new Location();
        location1.setLocationNumber("LOC1");
        location1.setMetroplexLocation(groupCode);

        Location location2 = new Location();
        location2.setLocationNumber("LOC2");
        location2.setMetroplexLocation(groupCode);

        when(locationRepository.findByMetroplexLocation_GroupCode("+TEST")).thenReturn(List.of(location1, location2));

        List<Location> locations = locationService.findLocationsByGroupCode("+TEST");

        assertNotNull(locations);
        assertEquals(2, locations.size());
        assertTrue(locations.stream().anyMatch(loc -> "LOC1".equals(loc.getLocationNumber())));
        assertTrue(locations.stream().anyMatch(loc -> "LOC2".equals(loc.getLocationNumber())));

        verify(locationRepository, times(1)).findByMetroplexLocation_GroupCode("+TEST");
    }
}
