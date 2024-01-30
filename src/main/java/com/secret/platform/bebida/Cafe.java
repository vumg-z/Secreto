package com.secret.platform.bebida;

// Componente Concreto
class Cafe extends Bebida {
    public Cafe() {
        descripcion = "Café";
    }

    public double costo() {
        return 1.99;
    }


    public double tamano() {
        return 350;
    }
}