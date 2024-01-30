package com.secret.platform.bebida;

// Uso del patrón Decorator
public class Cafeteria {
    public static void main(String args[]) {
        Bebida bebida = new Cafe();
        System.out.println(bebida.getDescripcion()
                + " $" + bebida.costo());

        Bebida bebida2 = new Cafe();
        bebida2 = new ConLeche(bebida2);
        System.out.println(bebida2.getDescripcion()
                + " $" + bebida2.costo());

        Bebida bebida3;
        bebida3 = new Soda();
        System.out.println(bebida3.getDescripcion() + " $" + bebida3.costo());

        Bebida bebida4 = new Soda();
        bebida4 = new SodaConRusa(bebida4);
        System.out.println(bebida4.getDescripcion() + " $" + bebida4.costo());

    }
}
