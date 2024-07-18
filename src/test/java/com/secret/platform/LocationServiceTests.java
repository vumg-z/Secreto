package com.secret.platform;

import com.secret.platform.config.FeatureFlagServiceInterface;
import com.secret.platform.location.Location;
import com.secret.platform.location.LocationRepository;
import com.secret.platform.location.LocationService;
import com.secret.platform.location.LocationServiceInterface;
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

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveLocationWithHoldingDrawerEnabled() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);

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

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.findByHoldingDrawer("202")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(1)).findByHoldingDrawer("202");
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithHoldingDrawerDisabled() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(false);

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

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.save(location)).thenReturn(location);

        Location savedLocation = locationService.saveLocation(location);

        assertNotNull(savedLocation);
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(0)).findByHoldingDrawer("202");
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void testSaveLocationWithNonUniqueNumber() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);

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

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.of(location));

        assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(location);
        });

        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(0)).findByHoldingDrawer("202");
        verify(locationRepository, times(0)).save(location);
    }

    @Test
    void testSaveLocationWithNonUniqueHoldingDrawer() {
        when(featureFlagService.isHoldingDrawerEnabled()).thenReturn(true);

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

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.empty());
        when(locationRepository.findByHoldingDrawer("202")).thenReturn(Optional.of(location));

        assertThrows(IllegalArgumentException.class, () -> {
            locationService.saveLocation(location);
        });

        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
        verify(locationRepository, times(1)).findByHoldingDrawer("202");
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

        when(locationRepository.findByLocationNumber("PVREY1")).thenReturn(Optional.of(location));

        Optional<Location> foundLocation = locationService.findLocationByNumber("PVREY1");

        assertTrue(foundLocation.isPresent());
        assertEquals("PVREY1", foundLocation.get().getLocationNumber());
        verify(locationRepository, times(1)).findByLocationNumber("PVREY1");
    }
}
