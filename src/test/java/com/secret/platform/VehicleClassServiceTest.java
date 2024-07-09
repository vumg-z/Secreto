package com.secret.platform;

import com.secret.platform.city.City;
import com.secret.platform.city.CityRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.vehicle.Vehicle;
import com.secret.platform.vehicle.VehicleRepository;
import com.secret.platform.vehicle.VehicleService;
import com.secret.platform.vehicle_class.VehicleClass;
import com.secret.platform.vehicle_class.VehicleClassRepository;
import com.secret.platform.vehicle_class.VehicleClassService;
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

public class VehicleClassServiceTest {

    @Mock
    private VehicleClassRepository vehicleClassRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private VehicleClassService vehicleClassService;

    @InjectMocks
    private VehicleService vehicleService;

    private VehicleClass vehicleClass;
    private Vehicle vehicle;
    private City city;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        City city = new City();
        city.setId(1L);
        city.setName("Guadalajara");
        city.setDescription("A city in Mexico");
        city.setLatitude(20.6597);
        city.setLongitude(-103.3496);
        city.setCode("GDLMY1");
        city.setCreatedAt(LocalDateTime.now());
        city.setUpdatedAt(LocalDateTime.now());

        vehicleClass = new VehicleClass();
        vehicleClass.setId(1L);
        vehicleClass.setClassName("ECAR");
        vehicleClass.setDescription("Economy Car");
        vehicleClass.setCreatedAt(LocalDateTime.now());
        vehicleClass.setUpdatedAt(LocalDateTime.now());

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
    public void testGetVehiclesByClassName() {
        Vehicle vehicle2 = new Vehicle.ConcreteVehicleBuilder()
                .setBrand("Honda")
                .setModel("Civic")
                .setYear(2021)
                .setCity(city)
                .build();

        when(vehicleRepository.findVehiclesByClassName("ECAR")).thenReturn(Arrays.asList(vehicle, vehicle2));

        List<Vehicle> vehicles = vehicleService.getVehiclesByClassName("ECAR");

        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertEquals("Toyota", vehicles.get(0).getBrand());
        assertEquals("Honda", vehicles.get(1).getBrand());
        verify(vehicleRepository, times(1)).findVehiclesByClassName("ECAR");
    }

    @Test
    public void testGetVehiclesByClassNameAndCity() {
        City city = new City();
        city.setId(1L);
        city.setName("Guadalajara");
        city.setDescription("A city in Mexico");
        city.setLatitude(20.6597);
        city.setLongitude(-103.3496);
        city.setCode("GDLMY1");
        city.setCreatedAt(LocalDateTime.now());
        city.setUpdatedAt(LocalDateTime.now());

        Vehicle vehicle2 = new Vehicle.ConcreteVehicleBuilder()
                .setBrand("Honda")
                .setModel("Civic")
                .setYear(2021)
                .setCity(city)
                .setVehicleClasses(Set.of(vehicleClass))
                .build();

        when(vehicleRepository.findVehiclesByClassNameAndCityCode("ECAR", "GDLMY1")).thenReturn(Arrays.asList(vehicle, vehicle2));

        System.out.println("Input parameters: className = 'ECAR', cityCode = 'GDLMY1'");
        List<Vehicle> vehicles = vehicleService.getVehiclesByClassNameAndCityCode("ECAR", "GDLMY1");

        System.out.println("Output vehicles:");
        for (Vehicle v : vehicles) {
            System.out.println("Vehicle ID: " + v.getId());
            System.out.println("Brand: " + v.getBrand());
            System.out.println("Model: " + v.getModel());
            System.out.println("Year: " + v.getYear());
            System.out.println("Color: " + v.getColor());
            System.out.println("VIN: " + v.getVin());
            System.out.println("Mileage: " + v.getMileage());
            System.out.println("Fuel Type: " + v.getFuelType());
            System.out.println("Transmission: " + v.getTransmission());
            System.out.println("Passenger Capacity: " + v.getPassengerCapacity());
            System.out.println("Trunk Capacity: " + v.getTrunkCapacity());
            System.out.println("Condition: " + v.getCondition());
            System.out.println("Availability: " + v.isAvailability());
            System.out.println("City Code: " + v.getCity().getCode());
            System.out.println("Model 3D Path: " + v.getModel3D());
            System.out.println("Vehicle Classes: ");
            for (VehicleClass vc : v.getVehicleClasses()) {
                System.out.println("Class Name: " + vc.getClassName() + ", Description: " + vc.getDescription());
            }
            System.out.println("---------------------------");
        }

        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertEquals("Toyota", vehicles.get(0).getBrand());
        assertEquals("Honda", vehicles.get(1).getBrand());
        verify(vehicleRepository, times(1)).findVehiclesByClassNameAndCityCode("ECAR", "GDLMY1");
    }

}
