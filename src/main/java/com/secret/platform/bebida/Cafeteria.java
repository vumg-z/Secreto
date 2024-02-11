package com.secret.platform.bebida;
import  java.lang.Integer;

// Uso del patrón Decorator
public class Cafeteria {
    public static void main(String args[]) {

        System.setProperty("java.lang.Integer.IntegerCache.high", "1000");


        Bebida bebida = new Cafe();
        //System.out.println(bebida.getDescripcion()
         //       + " $" + bebida.costo());

        Bebida bebida2 = new Cafe();
        bebida2 = new ConLeche(bebida2);
        //System.out.println(bebida2.getDescripcion()
          //      + " $" + bebida2.costo());

        Bebida bebida3;
        bebida3 = new Soda();
        //System.out.println(bebida3.getDescripcion() + " $" + bebida3.costo());

        Bebida bebida4 = new Soda();
        bebida4 = new SodaConRusa(bebida4);
        //System.out.println(bebida4.getDescripcion() + " $" + bebida4.costo());

        System.out.println( "equals " + bebida4.getDescripcion() + "< - >" + bebida3.getDescripcion() + " = " + bebida4.equals(bebida3));

        System.out.println(bebida4.toString());


    }
}
