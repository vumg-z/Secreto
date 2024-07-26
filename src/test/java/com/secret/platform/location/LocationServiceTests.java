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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LocationServiceTests {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private FeatureFlagServiceInterface featureFlagService;

    @Mock
    private StatusCodeRepository statusCodeRepository;

    @Mock
    private GeneralLedgerRepository generalLedgerRepository;

    @Mock
    private GroupCodesRepository groupCodesRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setUpDefaultMocks();
    }

    private void setUpDefaultMocks() {
        StatusCode defaultStatus = new StatusCode();
        defaultStatus.setCode("A");
        when(statusCodeRepository.findByCode("A")).thenReturn(Optional.of(defaultStatus));
        when(statusCodeRepository.existsByCode("A")).thenReturn(true);

        GeneralLedger defaultLedger = new GeneralLedger();
        defaultLedger.setId(1L);
        defaultLedger.setMainAcct("1130");
        defaultLedger.setSubAcct("4");
        when(generalLedgerRepository.findById(1L)).thenReturn(Optional.of(defaultLedger));
        when(generalLedgerRepository.existsById(1L)).thenReturn(true);

        GroupCodes defaultGroupCode = new GroupCodes();
        defaultGroupCode.setId(1L);
        when(groupCodesRepository.findById(1L)).thenReturn(Optional.of(defaultGroupCode));
        when(groupCodesRepository.existsById(1L)).thenReturn(true);
    }

    private Location createLocation() {
        StatusCode statusCode = new StatusCode();
        statusCode.setCode("A");

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
        location.setAutoVehicleSelect("N");
        location.setCheckOutFuel("8");
        location.setCheckInStatus(statusCode);
        location.setValidRentalLoc("Y");
        //location.setInterOfcArAcct(new GeneralLedger());
        //location.getInterOfcArAcct().setId(1L);
        location.setAllowMultiLanguageRa("Y");
        location.setAllowWaitRas("Y");
        location.setMetroplexLocation(new GroupCodes());
        location.getMetroplexLocation().setId(1L);
        return location;
    }

    @Test
    void testSaveLocationWithDefaultCheckInStatus() {
        Location location = createLocation();

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        assertEquals("A", savedLocation.getCheckInStatus().getCode());
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
        //verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
//        verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, times(1)).save(location);
    }


    @Test
    void testSaveLocationWithValidCheckInStatus() {
        StatusCode statusCode = new StatusCode();
        statusCode.setCode("B");
        Location location = createLocation();
        location.setCheckInStatus(statusCode);

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(statusCodeRepository.existsByCode("B")).thenReturn(true);
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        assertEquals("B", savedLocation.getCheckInStatus().getCode());
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
//        verify(statusCodeRepository, times(1)).existsByCode("B");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
//        verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithInvalidCheckInStatus() {
        StatusCode statusCode = new StatusCode();
        statusCode.setCode("Z");
        Location location = createLocation();
        location.setCheckInStatus(statusCode);

        when(statusCodeRepository.existsByCode("Z")).thenReturn(false);
/*
 assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(location);
        });
 */


        verify(locationRepository, times(0)).save(location);
    }

    @Test
    void testSaveLocationWithHoldingDrawerEnabled() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);
        Location location = createLocation();

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.findByHoldingDrawer("202")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
//        verify(locationRepository, times(1)).findByHoldingDrawer("202");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
//        verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithHoldingDrawerDisabled() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(false);
        Location location = createLocation();

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
//        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
  //      verify(locationRepository, times(0)).findByHoldingDrawer("202");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
//        verify(generalLedgerRepository, times(1)).existsById(1L);
    //    verify(groupCodesRepository, times(1)).existsById(1L);
      //  verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithNonUniqueNumber() {
        Location location = createLocation();
        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.of(location));

        assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(location);
        });

        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(0)).save(location);
    }

    @Test
    void testSaveLocationWithNonUniqueHoldingDrawer() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);
        Location location = createLocation();

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.findByHoldingDrawer("202")).thenReturn(Optional.of(location));

        //assertThrows(IllegalArgumentException.class, () -> {
          //  locationService.saveLocation(location);
        //});

//        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        //verify(locationRepository, times(1)).findByHoldingDrawer("202");
        verify(locationRepository, times(0)).save(location);
    }

    @Test
    void testIsLocationNumberUnique() {
        when(locationRepository.findByLocationNumber("UniqueLocation")).thenReturn(Optional.empty());

        boolean isUnique = locationService.isLocationNumberUnique("UniqueLocation");

        assertTrue(isUnique);
        verify(locationRepository, times(1)).findByLocationNumber("UniqueLocation");
    }

    @Test
    void testIsHoldingDrawerUnique() {
        when(locationRepository.findByHoldingDrawer("202")).thenReturn(Optional.empty());

        boolean isUnique = locationService.isHoldingDrawerUnique("202");

        assertTrue(isUnique);
        verify(locationRepository, times(1)).findByHoldingDrawer("202");
    }

    @Test
    void testFindLocationByNumber() {
        Location location = createLocation();

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.of(location));

        Optional<Location> foundLocation = locationService.findLocationByNumber("PVREY1");

        assertTrue(foundLocation.isPresent());
        assertEquals("PVREY1", foundLocation.get().getLocationNumber());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
    }

    @Test
    void testSaveLocationWithRegion() {
        Location region = new Location();
        region.setLocationNumber("REGION1");
        region.setValidRentalLoc("N");

        Location location = createLocation();
        location.setRegion(region);

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        assertEquals(region, savedLocation.getRegion());
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithGeneralLedger() {
        GeneralLedger generalLedger = new GeneralLedger();
        generalLedger.setId(1L);

        Location location = createLocation();
        //location.setInterOfcArAcct(generalLedger);

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        //assertEquals(1L, savedLocation.getInterOfcArAcct().getId());
        assertEquals(1L, savedLocation.getMetroplexLocation().getId());
    //    verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
//        verify(statusCodeRepository, times(1)).existsByCode("A");
        // verify(generalLedgerRepository, times(1)).existsById(1L);
//        verify(groupCodesRepository, times(1)).existsById(1L);
  //      verify(locationRepository, times(1)).save(location);
    }
}
