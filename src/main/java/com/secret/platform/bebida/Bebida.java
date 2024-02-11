package com.secret.platform.bebida;

// Componente Abstracto
abstract class Bebida {
    String descripcion = "Bebida desconocida";

    public String getDescripcion() {
        return descripcion;
    }

    public abstract double costo();

    public abstract double tamano();


    @Override
    public String toString() {
        // Assuming tamano returns a numerical value that corresponds to size (e.g., 1 for small, 2 for medium, etc.)
        // You might need to adjust this part to fit how you're actually handling sizes.
        String size;
        switch ((int) tamano()) {
            case 1:
                size = "Pequeño";
                break;
            case 2:
                size = "Mediano";
                break;
            case 3:
                size = "Grande";
                break;
            default:
                size = "Tamaño desconocido"; // Or any logic you prefer for handling size
        }
        return getDescripcion() + " [" + size + "], Costo: $" + costo();
    }
}