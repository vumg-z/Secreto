package com.secret.platform.location;

import org.springframework.stereotype.Service;

@Service
public class LocationService {

    public void saveLocation(Location location) {
        // Implement your save logic here, e.g., save to a database
        System.out.println("Location data: " + location);
    }
}
