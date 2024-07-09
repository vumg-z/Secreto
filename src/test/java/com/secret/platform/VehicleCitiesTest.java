package com.secret.platform;

import com.secret.platform.city.City;
import com.secret.platform.city.CityRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.vehicle.Vehicle;
import com.secret.platform.vehicle.VehicleRepository;
import com.secret.platform.vehicle.VehicleService;
import com.secret.platform.vehicle_class.VehicleClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleCitiesTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;
    private VehicleClass vehicleClass;
    private City city;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        city = new City();
        city.setId(1L);
        city.setName("Guadalajara");
        city.setDescription("City in Mexico");
        city.setLatitude(20.659698);
        city.setLongitude(-103.349609);
        city.setAltitude(1566.0);
        city.setGoogleMapsLink("https://maps.google.com/?q=Guadalajara");

        vehicleClass = new VehicleClass();
        vehicleClass.setId(1L);
        vehicleClass.setClassName("ECAR");
        vehicleClass.setDescription("Economy Car");

        vehicle = new Vehicle.ConcreteVehicleBuilder()
                .setBrand("Toyota")
                .setModel("Corolla")
                .setYear(2020)
                .setColor("Red")
                .setVin("1HGBH41JXMN109186")
                .setMileage(15000)
                .setFuelType("Petrol")
                .setTransmission("Automatic")
                .setPassengerCapacity(5)
                .setTrunkCapacity(450)
                .setCondition("New")
                .setAvailability(true)
                .setCity(city)
                .setModel3D("path/to/model3D")
                .setVehicleClasses(Set.of(vehicleClass))
                .build();
    }

    @Test
    public void testSaveVehicle() {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);

        assertNotNull(savedVehicle);
        assertEquals("Toyota", savedVehicle.getBrand());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
    public void testGetVehicleById() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicle = vehicleService.getVehicleById(1L);

        assertNotNull(foundVehicle);
        assertEquals("Toyota", foundVehicle.getBrand());
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetVehicleById_NotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.getVehicleById(1L);
        });

        String expectedMessage = "Vehicle not found for this id :: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteVehicle() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(1L);

        verify(vehicleRepository, times(1)).findById(1L);
        verify(vehicleRepository, times(1)).delete(vehicle);
    }

    @Test
    public void testDeleteVehicle_NotFound() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.deleteVehicle(1L);
        });

        String expectedMessage = "Vehicle not found for this id :: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(vehicleRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllVehicles() {
        Vehicle vehicle2 = new Vehicle.ConcreteVehicleBuilder()
                .setBrand("Honda")
                .setModel("Civic")
                .setYear(2021)
                .setCity(city)
                .build();

        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle, vehicle2));

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertEquals("Toyota", vehicles.get(0).getBrand());
        assertEquals("Honda", vehicles.get(1).getBrand());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    public void testGetVehiclesByClassNameAndCity() {
        Vehicle vehicle2 = new Vehicle.ConcreteVehicleBuilder()
                .setBrand("Honda")
                .setModel("Civic")
                .setYear(2021)
                .setCity(city)
                .build();

        when(vehicleRepository.findVehiclesByClassNameAndCityCode("ECAR", "GDLMY1")).thenReturn(Arrays.asList(vehicle, vehicle2));

        List<Vehicle> vehicles = vehicleService.getVehiclesByClassNameAndCityCode("ECAR", "GDLMY1");

        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertEquals("Toyota", vehicles.get(0).getBrand());
        assertEquals("Honda", vehicles.get(1).getBrand());
        verify(vehicleRepository, times(1)).findVehiclesByClassNameAndCityCode("ECAR", "GDLMY1");
    }

    @Test
    public void testCreateCity() {
        when(cityRepository.save(city)).thenReturn(city);

        City savedCity = cityRepository.save(city);

        assertNotNull(savedCity);
        assertEquals("Guadalajara", savedCity.getName());
        verify(cityRepository, times(1)).save(city);
    }

    @Test
    public void testGetCityById() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        Optional<City> foundCity = cityRepository.findById(1L);

        assertTrue(foundCity.isPresent());
        assertEquals("Guadalajara", foundCity.get().getName());
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCityById_NotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cityRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("City not found for this id :: 1"));
        });

        String expectedMessage = "City not found for this id :: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteCity() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        cityRepository.delete(city);

        verify(cityRepository, times(1)).findById(1L);
        verify(cityRepository, times(1)).delete(city);
    }

    @Test
    public void testDeleteCity_NotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cityRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("City not found for this id :: 1"));
        });

        String expectedMessage = "City not found for this id :: 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(cityRepository, times(1)).findById(1L);
    }
}
