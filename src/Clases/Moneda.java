package Clases;

public class Moneda {
    String nombre;
    double valor;

    public Moneda(String nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return nombre + " = " + valor;
    }
}
