package com.secret.platform.vehicle;

import com.secret.platform.city.City;
import com.secret.platform.city.CityRepository;
import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.vehicle_class.VehicleClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private CityRepository cityRepository;

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam int year,
            @RequestParam String color,
            @RequestParam String vin,
            @RequestParam int mileage,
            @RequestParam String fuelType,
            @RequestParam String transmission,
            @RequestParam int passengerCapacity,
            @RequestParam int trunkCapacity,
            @RequestParam String condition,
            @RequestParam boolean availability,
            @RequestParam String cityCode,
            @RequestParam MultipartFile model3DFile,
            @RequestParam Set<VehicleClass> vehicleClasses) {

        // Handle file upload and convert MultipartFile to a URL or path
        String model3DPath = handleFileUpload(model3DFile);

        // Fetch the city by its code
        City city = cityRepository.findByCode(cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("City not found for this code :: " + cityCode));

        Vehicle vehicle = new Vehicle.ConcreteVehicleBuilder()
                .setBrand(brand)
                .setModel(model)
                .setYear(year)
                .setColor(color)
                .setVin(vin)
                .setMileage(mileage)
                .setFuelType(fuelType)
                .setTransmission(transmission)
                .setPassengerCapacity(passengerCapacity)
                .setTrunkCapacity(trunkCapacity)
                .setCondition(condition)
                .setAvailability(availability)
                .setCity(city)
                .setModel3D(model3DPath)
                .setVehicleClasses(vehicleClasses)
                .build();

        Vehicle createdVehicle = vehicleService.saveVehicle(vehicle);

        return ResponseEntity.ok(createdVehicle);
    }

    @GetMapping("/class/{className}/city/{cityCode}")
    public ResponseEntity<List<Vehicle>> getVehiclesByClassNameAndCityCode(
            @PathVariable String className,
            @PathVariable String cityCode) {

        List<Vehicle> vehicles = vehicleService.getVehiclesByClassNameAndCityCode(className, cityCode);

        return ResponseEntity.ok(vehicles);
    }

    private String handleFileUpload(MultipartFile file) {
        // Implement your file upload logic here
        // For example, save the file to a directory and return the file path
        return "path/to/uploaded/file";
    }
}
