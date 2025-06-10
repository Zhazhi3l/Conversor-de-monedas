package Excepciones;

public class DivisaNoValida extends RuntimeException {
    public DivisaNoValida(String mensaje) {
        super(mensaje);
    }
}
