package Principal;

import BusquedasYTransformaciones.ConsultarTasa;

import java.util.Scanner;

public class Menu {
    public static void desplegarMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido al conversor de monedas");
        System.out.println("Inserte la abreviación de 3 letras de la moneda que desea convertir:");
        var moneda = sc.nextLine();
        sc.close();

        System.out.println(new ConsultarTasa().consultarTasas(moneda));
    }
}
