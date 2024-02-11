package com.secret.platform;

import com.secret.platform.bebida.*;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;

public class Tests {
    
    private static LinkedList<Integer> lista = new LinkedList<>();

    private static String greeting(String text, Callable<String> callable) {
        try {
            // Llama al Callable y obtiene el resultado
            String result = callable.call();
            // Combina el resultado del Callable con el texto proporcionado y devuelve el resultado
            return text + result;
        } catch (Exception e) {
            // Manejo de excepción: podría retornar un mensaje de error o re-lanzar una excepción
            return "Error during callable execution: " + e.getMessage();
        }
    }
    /*
    public static void main(String[] args) {

        // Llamada al método greeting
        String result = greeting("Hello, ", () -> "World!");
        System.out.println(result);

        // Otro ejemplo, usando una expresión lambda que realiza una operación más compleja
        String complexResult = greeting("The result is: ", () -> {
            // Simula alguna lógica compleja aquí
            int number = 42; // Por ejemplo, algún cálculo
            return "The answer to everything is " + number;
        });
        System.out.println(complexResult);
    }*/
}
