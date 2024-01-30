package com.secret.platform.bebida;

// Decorador Concreto
class ConLeche extends DecoradorBebida {
    Bebida bebida;

    public ConLeche(Bebida bebida) {
        this.bebida = bebida;
    }

    public String getDescripcion() {
        return bebida.getDescripcion() + ", Con Leche";
    }

    public double costo() {
        return .20 + bebida.costo();
    }

    @Override
    public double tamano() {
        return 0;
    }
}