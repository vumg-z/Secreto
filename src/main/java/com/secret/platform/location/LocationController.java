package com.secret.platform.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/receive")
    public String receiveLocationData(@RequestBody Location location) {
        locationService.saveLocation(location);
        return "Location data received successfully";
    }

    @GetMapping("/all")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
}
