package com.secret.platform.bebida;

// Componente Abstracto
abstract class Bebida {
    String descripcion = "Bebida desconocida";

    public String getDescripcion() {
        return descripcion;
    }

    public abstract double costo();

    public abstract double tamano();
}