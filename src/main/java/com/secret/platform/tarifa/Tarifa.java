package com.secret.platform.tarifa;

import com.secret.platform.corporateid.CorporateID;
import com.secret.platform.inclusiones.Inclusiones;
import com.secret.platform.productos.Productos;
import com.secret.platform.terminos_alquiler.TerminosAlquiler;
import com.secret.platform.vehicle_class.VehicleClass;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "corporate_id", nullable = false)
    private CorporateID corporateID;

    @ManyToOne
    @JoinColumn(name = "vehicle_class_id", nullable = false)
    private VehicleClass vehicleClass;

    private boolean availability;
    private String codigoMoneda;
    private double estimate;
    private double rateOnlyEstimate;
    private double dropCharge;
    private double distance;
    private boolean prepaid;
    private double iva;
    private double discount;

    @ManyToMany
    @JoinTable(
            name = "tarifa_inclusiones",
            joinColumns = @JoinColumn(name = "tarifa_id"),
            inverseJoinColumns = @JoinColumn(name = "inclusiones_id")
    )
    private Set<Inclusiones> inclusiones;

    @ManyToMany
    @JoinTable(
            name = "tarifa_productos_obligatorios",
            joinColumns = @JoinColumn(name = "tarifa_id"),
            inverseJoinColumns = @JoinColumn(name = "productos_id")
    )
    private Set<Productos> productosObligatorios;

    @ManyToMany
    @JoinTable(
            name = "tarifa_productos_extras",
            joinColumns = @JoinColumn(name = "tarifa_id"),
            inverseJoinColumns = @JoinColumn(name = "productos_id")
    )
    private Set<Productos> productosExtras;

    @ManyToOne
    @JoinColumn(name = "terminos_alquiler_id")
    private TerminosAlquiler terminosAlquiler;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CorporateID getCorporateID() {
        return corporateID;
    }

    public void setCorporateID(CorporateID corporateID) {
        this.corporateID = corporateID;
    }

    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public double getRateOnlyEstimate() {
        return rateOnlyEstimate;
    }

    public void setRateOnlyEstimate(double rateOnlyEstimate) {
        this.rateOnlyEstimate = rateOnlyEstimate;
    }

    public double getDropCharge() {
        return dropCharge;
    }

    public void setDropCharge(double dropCharge) {
        this.dropCharge = dropCharge;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isPrepaid() {
        return prepaid;
    }

    public void setPrepaid(boolean prepaid) {
        this.prepaid = prepaid;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Set<Inclusiones> getInclusiones() {
        return inclusiones;
    }

    public void setInclusiones(Set<Inclusiones> inclusiones) {
        this.inclusiones = inclusiones;
    }

    public Set<Productos> getProductosObligatorios() {
        return productosObligatorios;
    }

    public void setProductosObligatorios(Set<Productos> productosObligatorios) {
        this.productosObligatorios = productosObligatorios;
    }

    public Set<Productos> getProductosExtras() {
        return productosExtras;
    }

    public void setProductosExtras(Set<Productos> productosExtras) {
        this.productosExtras = productosExtras;
    }

    public TerminosAlquiler getTerminosAlquiler() {
        return terminosAlquiler;
    }

    public void setTerminosAlquiler(TerminosAlquiler terminosAlquiler) {
        this.terminosAlquiler = terminosAlquiler;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
