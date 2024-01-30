package com.secret.platform.bebida;

//Decorador concreto
class SodaConRusa extends DecoradorBebida {

    Bebida bebida;

    SodaConRusa(Bebida bebida){
        this.bebida = bebida;
    }
    @Override
    public double costo() {
        return bebida.costo() + 40;
    }

    @Override
    public double tamano() {
        return 0;
    }

    @Override
    public String getDescripcion() {
        return bebida.getDescripcion() + " Soda Rusa";
    }
}
