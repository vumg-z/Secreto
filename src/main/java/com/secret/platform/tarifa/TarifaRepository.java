package com.secret.platform.tarifa;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.vehicle_class.VehicleClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    Optional<Tarifa> findByCorporateIDAndVehicleClass(CorporateID corporateID, VehicleClass vehicleClass);

    List<Tarifa> findByCorporateID(String corpRateID);
}
