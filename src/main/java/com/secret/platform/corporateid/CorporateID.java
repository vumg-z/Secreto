package com.secret.platform.corporateid;

import com.secret.platform.inclusiones.Inclusiones;
import com.secret.platform.terminos_alquiler.TerminosAlquiler;
import com.secret.platform.productos.Productos;
import com.secret.platform.productos.PaqueteProductosExtras;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
/**
 * @deprecated This class is deprecated and will be removed in future versions.
 *             Please use {@link com.secret.platform.corporate_account}
 */
@Deprecated
@Entity
public class CorporateID {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String codigoMoneda;
    private Double iva;
    private Double discount;

    @ManyToMany
    @JoinTable(
            name = "corporate_id_inclusiones",
            joinColumns = @JoinColumn(name = "corporate_id_id"),
            inverseJoinColumns = @JoinColumn(name = "inclusiones_id")
    )
    private Set<Inclusiones> inclusiones;

    @ManyToMany
    @JoinTable(
            name = "corporate_id_terminos_alquiler",
            joinColumns = @JoinColumn(name = "corporate_id_id"),
            inverseJoinColumns = @JoinColumn(name = "terminos_alquiler_id")
    )
    private Set<TerminosAlquiler> terminosAlquiler;



    @ManyToMany
    @JoinTable(
            name = "corporate_id_paquete_productos_extras",
            joinColumns = @JoinColumn(name = "corporate_id_id"),
            inverseJoinColumns = @JoinColumn(name = "paquete_productos_extras_id")
    )
    private Set<PaqueteProductosExtras> paqueteProductosExtras;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Set<Inclusiones> getInclusiones() {
        return inclusiones;
    }

    public void setInclusiones(Set<Inclusiones> inclusiones) {
        this.inclusiones = inclusiones;
    }

    public Set<TerminosAlquiler> getTerminosAlquiler() {
        return terminosAlquiler;
    }

    public void setTerminosAlquiler(Set<TerminosAlquiler> terminosAlquiler) {
        this.terminosAlquiler = terminosAlquiler;
    }

    public Set<PaqueteProductosExtras> getPaqueteProductosExtras() {
        return paqueteProductosExtras;
    }

    public void setPaqueteProductosExtras(Set<PaqueteProductosExtras> paqueteProductosExtras) {
        this.paqueteProductosExtras = paqueteProductosExtras;
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
