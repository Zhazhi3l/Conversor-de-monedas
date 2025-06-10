package Principal;

import BusquedasYTransformaciones.ConsultarTasa;
import Clases.TasaConversion;
import Excepciones.DivisaNoValida;

import java.util.Scanner;

public class Menu {
    public static void desplegarMenu() {
        Scanner sc = new Scanner(System.in);
        int i = 1;

        System.out.println("*** Bienvenido/a al conversor de monedas ***");
        while (i >= 1 && i <= 8) {
            try {
                mostrarOpciones();
                int opcion = sc.nextInt();
                sc.nextLine();
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
                                System.out.println("Ingrese la abreviación de 3 letras de la moneda que desea convertir:");
                                var moneda = sc.nextLine();
                                TasaConversion tasas = new ConsultarTasa().consultarTasas(moneda);
                                System.out.println("¿Desea conocer las tasas de cambio de la moneda " + moneda + "? (S/N)");
                                var respuesta = sc.nextLine();
                                if (respuesta.equalsIgnoreCase("S")) {
                                    System.out.println(tasas.toString());
                                }
                                System.out.println("Ingrese la segunda moneda que desea convertir:");
                                var segMoneda = sc.nextLine();
                                if(!consultas.verificarMoneda(segMoneda, tasas.getConversion_rates())){
                                    throw new DivisaNoValida("La divisa " + segMoneda.toUpperCase() + " no existe o no está soportada. Inserte de nuevo ambas divisas.");
                                }
                                System.out.println("Ya salió todo bien");
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
                                Opcion no válida. Por favor, intente nuevamente.
                                Seleccione  un numero [1-8].
                                7 si desea seleccionar otra divisa.
                                8 para salir.
                                """);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero [1-6].");
            } catch (DivisaNoValida e) {
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        sc.close();
    }

    private static void convertirMonedas(TasaConversion tasas, String segMoneda) {

    }

    private static void mostrarOpciones() {
        System.out.println("*************************************************************");
        System.out.println("Ingrese el numero de la opción que desea realizar:");
        System.out.println("1) Dólares Americanos[USD] =>> Peso Argentino[ARS]");
        System.out.println("2) Peso Argentino[ARS] =>> Dólares Americanos[USD]");
        System.out.println("3) Dólares americanos[USD] =>> Peso Chileno[CLP]");
        System.out.println("4) Peso Chileno[CLP] =>> Dólares Americanos[USD]");
        System.out.println("5) Peso Chileno[CLP] =>> Peso Argentino[ARS]");
        System.out.println("6) Peso Argentino[ARS] =>> Peso Chileno[CLP]");
        System.out.println("7) Consultar Tasas de Cambio");
        System.out.println("8) Salir");
        System.out.println("*************************************************************");
    }
}
