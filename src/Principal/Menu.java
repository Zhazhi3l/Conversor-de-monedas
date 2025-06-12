package Principal;

import BusquedasYTransformaciones.ConsultarTasa;
import Clases.Moneda;
import Clases.TasaConversion;
import Excepciones.DivisaNoValida;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void desplegarMenu() {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        while (i >= 1 && i <= 8) {
            try {
                mostrarOpciones();
                int opcion = Integer.valueOf(sc.nextLine().trim());
                ConsultarTasa consultas = new ConsultarTasa();
                switch (opcion) {
                    case 1:
                        //tasas = new ConsultarTasa().consultarTasas("USD");
                        //tasas.convertirMonedas("USD", "ARS");

                        i = 0;
                        break;
                    case 7:
                        while (true) {
                            try {
                                // Parte 1: Consultar Tasas de Cambio
                                System.out.println("Ingrese la abreviación de 3 letras de la moneda que desea convertir:");
                                var moneda = sc.nextLine();
                                TasaConversion tasas = new ConsultarTasa().consultarTasas(moneda);

                                // Parte 2: Mostrar tasas de cambio de la primer moneda
                                System.out.println("¿Desea conocer las tasas de cambio de la moneda " + moneda + "? (S/N)");
                                var respuesta = sc.nextLine();
                                if (respuesta.equalsIgnoreCase("S") || respuesta.equalsIgnoreCase("SI")
                                    || respuesta.equalsIgnoreCase("Y") || respuesta.equalsIgnoreCase("YES")) {
                                    System.out.println(tasas.toString());
                                }

                                // Parte 3: Convertir monedas
                                String segMoneda;
                                while (true) {
                                    System.out.println("Ingrese la segunda moneda que desea convertir:");
                                    segMoneda = sc.nextLine();
                                    if(consultas.verificarMoneda(segMoneda, tasas.getConversion_rates())){
                                        break;
                                    }else{
                                        System.out.println("La divisa " + segMoneda.toUpperCase() +
                                                " no existe o no está soportada. Por favor, intente nuevamente.");
                                    }
                                }
                                convertirMonedas(tasas, segMoneda);
                                break;
                            } catch (DivisaNoValida e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        i = 0;
                        break;
                    case 8:
                        i = 0;
                        break;
                    default:
                        System.out.println("""
                                *\t*\t*\t*\t*\t*\t*
                                Opción no válida. Por favor, intente nuevamente.
                                Seleccione un número [1-8].
                                7 si desea seleccionar otra divisa.
                                8 para salir.
                                *\t*\t*\t*\t*\t*\t*
                                """);
                        i=1;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero [1-6].");
            } catch (DivisaNoValida e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void convertirMonedas(TasaConversion tasas, String segMoneda) {
        Scanner sc = new Scanner(System.in);
        Moneda primeraMoneda = new Moneda(tasas.getBase_code(), tasas.getConversion_rates().get(0).getValor());
        Moneda segundaMoneda = buscarMonedaEnLista(segMoneda, tasas.getConversion_rates());

        System.out.println("¿Que cantidad desea convertir de: "+ "[" + primeraMoneda.getNombre() + "] =>>> " + "[" + segundaMoneda.getNombre() + "]?");
        double valorAConvertir = Double.valueOf(sc.nextLine().trim()); sc.close();
        double valorFinal = valorAConvertir * segundaMoneda.getValor();

        String resultado = "El valor de " + valorAConvertir + "[" + primeraMoneda.getNombre() + "]"
                + " corresponde al valor final de =>>> " + valorFinal + "[" + segundaMoneda.getNombre() + "]";
        System.out.println(resultado);
    }

    private static Moneda buscarMonedaEnLista(String moneda, List<Moneda> listaMonedas) {
        for (Moneda buscada : listaMonedas) {
            if (buscada.getNombre().equalsIgnoreCase(moneda)) {
                return buscada;
            }
        }
        return null; // Si llegó aquí algo muy malo pasó aquí. Jaja
    }

    private static void mostrarOpciones() {
        System.out.println("""
                *** Bienvenido/a al conversor de monedas ***
                Ingrese el numero de la opción que desea realizar:
                1) Dólares Americanos[USD] =>> Peso Argentino[ARS]
                2) Peso Argentino[ARS] =>> Dólares Americanos[USD]
                3) Dólares americanos[USD] =>> Peso Chileno[CLP]
                4) Peso Chileno[CLP] =>> Dólares americanos[USD]
                5) Peso Chileno[CLP] =>> Peso Argentino[ARS]
                6) Peso Argentino[ARS] =>> Peso Chileno[CLP]
                7) Consultar Tasas de Cambio
                8) Salir
                *************************************************************""");
    }
}
