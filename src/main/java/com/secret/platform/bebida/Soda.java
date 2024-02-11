package com.secret.platform.bebida;

// Componente Concreto
class Soda extends Bebida{

    public Soda(){
        descripcion = "Soda";

    }
    @Override
    public double costo() {
        return 2.33;
    }

    @Override
    public double tamano() {
        return 400;
    }
}
