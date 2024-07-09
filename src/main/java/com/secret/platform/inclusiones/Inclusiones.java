package com.secret.platform.inclusiones;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Inclusiones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;  // Changed from name to nombre to match the context
    private String texto;   // Changed from text to texto to match the context

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

    public String getNombre() {  // Changed to nombre
        return nombre;
    }

    public void setNombre(String nombre) {  // Changed to nombre
        this.nombre = nombre;
    }

    public String getTexto() {  // Changed to texto
        return texto;
    }

    public void setTexto(String texto) {  // Changed to texto
        this.texto = texto;
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
