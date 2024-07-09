package com.secret.platform.vehicle_class;

import com.secret.platform.exception.ResourceNotFoundException;
import com.secret.platform.vehicle.Vehicle;
import com.secret.platform.vehicle.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleClassService {

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<VehicleClass> getAllVehicleClasses() {
        return vehicleClassRepository.findAll();
    }

    public Optional<VehicleClass> getVehicleClassById(Long id) {
        return vehicleClassRepository.findById(id);
    }

    public VehicleClass createVehicleClass(VehicleClass vehicleClass) {
        return vehicleClassRepository.save(vehicleClass);
    }

    public VehicleClass updateVehicleClass(Long id, VehicleClass vehicleClassDetails) {
        VehicleClass vehicleClass = vehicleClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleClass not found for this id :: " + id));
        vehicleClass.setClassName(vehicleClassDetails.getClassName());
        vehicleClass.setDescription(vehicleClassDetails.getDescription());
        vehicleClass.setUpdatedAt(LocalDateTime.now());
        return vehicleClassRepository.save(vehicleClass);
    }

    public void deleteVehicleClass(Long id) {
        VehicleClass vehicleClass = vehicleClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleClass not found for this id :: " + id));
        vehicleClassRepository.delete(vehicleClass);
    }

    public List<Vehicle> getVehiclesByClassName(String className) {
        return vehicleRepository.findVehiclesByClassName(className);
    }
}
