package com.secret.platform.vehicle;

import com.secret.platform.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + id));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByClassName(String className) {
        return vehicleRepository.findVehiclesByClassName(className);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + id));
        vehicleRepository.delete(vehicle);
    }

    public List<Vehicle> getVehiclesByClassNameAndCityCode(String className, String cityCode) {
        return vehicleRepository.findVehiclesByClassNameAndCityCode(className, cityCode);
    }
}
