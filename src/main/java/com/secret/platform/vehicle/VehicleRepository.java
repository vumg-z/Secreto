package com.secret.platform.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT v FROM Vehicle v JOIN v.vehicleClasses vc WHERE vc.className = :className")
    List<Vehicle> findVehiclesByClassName(@Param("className") String className);

    @Query("SELECT v FROM Vehicle v JOIN v.vehicleClasses vc WHERE vc.className = :className AND v.city.code = :cityCode")
    List<Vehicle> findVehiclesByClassNameAndCityCode(@Param("className") String className, @Param("cityCode") String cityCode);


}
