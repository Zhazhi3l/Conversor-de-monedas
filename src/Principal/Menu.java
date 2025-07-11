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
                TasaConversion tasas;
                switch (opcion) {
                    case 1:
                        tasas = new ConsultarTasa().consultarTasas("USD");
                        convertirMonedas(tasas, "ARS");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 2:
                        tasas = new ConsultarTasa().consultarTasas("ARS");
                        convertirMonedas(tasas, "USD");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 3:
                        tasas = new ConsultarTasa().consultarTasas("USD");
                        convertirMonedas(tasas, "CLP");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 4:
                        tasas = new ConsultarTasa().consultarTasas("CLP");
                        convertirMonedas(tasas, "USD");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 5:
                        tasas = new ConsultarTasa().consultarTasas("CLP");
                        convertirMonedas(tasas, "ARS");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 6:
                        tasas = new ConsultarTasa().consultarTasas("ARS");
                        convertirMonedas(tasas, "CLP");
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 7:
                        while (true) {
                            try {
                                // Parte 1: Consultar Tasas de Cambio
                                System.out.println("Ingrese la abreviación de 3 letras de la moneda que desea convertir:");
                                var moneda = sc.nextLine();
                                tasas = consultas.consultarTasas(moneda);

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
                                    if (consultas.verificarMoneda(segMoneda, tasas.getConversion_rates())) {
                                        break;
                                    } else {
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
                        i = repetirOperaciones() ? 1 : 0;
                        break;
                    case 8:
                        i = 0;
                        break;
                    default:
                        System.out.println("""
                                *\t*\t*\t*\t*\t*\t*
                                Opción no válida. Por favor, intente nuevamente.
                                Seleccione un número [1-8],
                                7 si desea seleccionar otras divisa,
                                8 para salir.
                                *\t*\t*\t*\t*\t*\t*
                                """);
                        i = 1;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero [1-8].");
            } catch (DivisaNoValida e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        sc.close();
        System.out.println("""
                Gracias por usar el conversor de monedas. :)
                Saliendo...
                """);
    }

    private static boolean repetirOperaciones() {
        System.out.println("¿Desea realizar otra operación? (S/N)");
        var respuesta = new Scanner(System.in).nextLine();
        if (respuesta.equalsIgnoreCase("S") || respuesta.equalsIgnoreCase("SI")
                || respuesta.equalsIgnoreCase("Y") || respuesta.equalsIgnoreCase("YES")) {
            return true;
        } else {
            return false;
        }
    }

    private static void convertirMonedas(TasaConversion tasas, String segMoneda) {
        Scanner sc = new Scanner(System.in);
        Moneda primeraMoneda = new Moneda(tasas.getBase_code(), 1);
        Moneda segundaMoneda = buscarMonedaEnLista(segMoneda, tasas.getConversion_rates());

        while (true) {
            try {
                System.out.println("¿Que cantidad desea convertir de: " + "[" + primeraMoneda.getNombre() + "] =>>> " + "[" + segundaMoneda.getNombre() + "]?");
                double valorAConvertir = Double.valueOf(sc.nextLine().trim());
                double valorFinal = valorAConvertir * segundaMoneda.getValor();

                // Más precisión con 2 decimales
                valorFinal = Math.round(valorFinal * 100.0) / 100.0;

                String resultado = "El valor de " + valorAConvertir + "[" + primeraMoneda.getNombre() + "]"
                        + " corresponde al valor final de =>>> " + valorFinal + "[" + segundaMoneda.getNombre() + "]";
                System.out.println(resultado);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un valor unitario a convertir.");
            }
        }
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
                1) Dólares Estadounidenses[USD] =>> Peso Argentino[ARS]
                2) Peso Argentino[ARS] =>> Dólares Estadounidenses[USD]
                3) Dólares Estadounidenses[USD] =>> Peso Chileno[CLP]
                4) Peso Chileno[CLP] =>> Dólares Estadounidenses[USD]
                5) Peso Chileno[CLP] =>> Peso Argentino[ARS]
                6) Peso Argentino[ARS] =>> Peso Chileno[CLP]
                7) Consultar Tasas de Cambio
                8) Salir
                *************************************************************""");
    }
}
