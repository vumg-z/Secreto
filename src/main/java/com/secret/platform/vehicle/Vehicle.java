package com.secret.platform.vehicle;

import com.secret.platform.city.City;
import com.secret.platform.rental.RentalItem;
import com.secret.platform.vehicle_class.VehicleClass;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.Set;

@Entity
public class Vehicle extends RentalItem {

    private String brand;
    private String model;
    private Integer year;  // Change to Integer for consistency
    private String color;
    private String vin;  // Vehicle Identification Number
    private int mileage;
    private String fuelType;
    private String transmission;
    private int passengerCapacity;
    private int trunkCapacity;
    private String condition;
    private String model3D;

    @ManyToMany
    @JoinTable(
            name = "vehicle_vehicle_class",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_class_id")
    )
    private Set<VehicleClass> vehicleClasses;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    protected Vehicle(VehicleBuilder<?> builder) {
        super(builder);
        this.brand = builder.brand;
        this.model = builder.model;
        this.year = builder.year;
        this.color = builder.color;
        this.vin = builder.vin;
        this.mileage = builder.mileage;
        this.fuelType = builder.fuelType;
        this.transmission = builder.transmission;
        this.passengerCapacity = builder.passengerCapacity;
        this.trunkCapacity = builder.trunkCapacity;
        this.condition = builder.condition;
        this.model3D = builder.model3D;
        this.vehicleClasses = builder.vehicleClasses;
        this.city = builder.city;
    }

    // Getters and setters

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getVin() {
        return vin;
    }

    public int getMileage() {
        return mileage;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public int getTrunkCapacity() {
        return trunkCapacity;
    }

    public String getCondition() {
        return condition;
    }

    public String getModel3D() {
        return model3D;
    }

    public Set<VehicleClass> getVehicleClasses() {
        return vehicleClasses;
    }

    public City getCity() {
        return city;
    }

    public static class ConcreteVehicleBuilder extends VehicleBuilder<ConcreteVehicleBuilder> {
        @Override
        protected ConcreteVehicleBuilder self() {
            return this;
        }

        @Override
        public Vehicle build() {
            return new Vehicle(this);
        }
    }

    public static abstract class VehicleBuilder<T extends VehicleBuilder<T>> extends RentalItemBuilder<T> {
        private String brand;
        private String model;
        private Integer year;
        private String color;
        private String vin;
        private int mileage;
        private String fuelType;
        private String transmission;
        private int passengerCapacity;
        private int trunkCapacity;
        private String condition;
        private String model3D;
        private Set<VehicleClass> vehicleClasses;
        private City city;

        public T setBrand(String brand) {
            this.brand = brand;
            return self();
        }

        public T setModel(String model) {
            this.model = model;
            return self();
        }

        public T setYear(Integer year) {
            this.year = year;
            return self();
        }

        public T setColor(String color) {
            this.color = color;
            return self();
        }

        public T setVin(String vin) {
            this.vin = vin;
            return self();
        }

        public T setMileage(int mileage) {
            this.mileage = mileage;
            return self();
        }

        public T setFuelType(String fuelType) {
            this.fuelType = fuelType;
            return self();
        }

        public T setTransmission(String transmission) {
            this.transmission = transmission;
            return self();
        }

        public T setPassengerCapacity(int passengerCapacity) {
            this.passengerCapacity = passengerCapacity;
            return self();
        }

        public T setTrunkCapacity(int trunkCapacity) {
            this.trunkCapacity = trunkCapacity;
            return self();
        }

        public T setCondition(String condition) {
            this.condition = condition;
            return self();
        }

        public T setModel3D(String model3D) {
            this.model3D = model3D;
            return self();
        }

        public T setVehicleClasses(Set<VehicleClass> vehicleClasses) {
            this.vehicleClasses = vehicleClasses;
            return self();
        }

        public T setCity(City city) {
            this.city = city;
            return self();
        }

        @Override
        public abstract Vehicle build();
    }
}
