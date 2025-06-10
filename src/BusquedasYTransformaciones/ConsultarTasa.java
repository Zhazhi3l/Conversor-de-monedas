package BusquedasYTransformaciones;

import Clases.Moneda;
import Clases.TasaConversion;
import Excepciones.DivisaNoValida;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ConsultarTasa {
    public TasaConversion consultarTasas(String moneda) {
        if (moneda == null) {
            throw new DivisaNoValida("No se encontró esa divisa.");
        }
        if (moneda.trim().isEmpty()) {
            throw new DivisaNoValida("La divisa no puede estar vacía");
        }
        if (moneda.length() != 3) {
            throw new RuntimeException("La moneda debe contener 3 letras.");
        }

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/e2a1dc54f1ea9e34dc9949b1/latest/" + moneda.toUpperCase());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body() + "\n---------------------");
            JsonObject objetoJson = JsonParser.parseString(response.body()).getAsJsonObject();

            // Verificar si la divisa existe
            /*
            if (objetoJson.has("result") && objetoJson.get("result").getAsString().equals("error")) {
                throw new DivisaNoValida("La divisa " + moneda.toUpperCase() + " no existe o no está soportada.");
            }*/

            String divisa = objetoJson.get("base_code").getAsString();
            JsonObject tasas = objetoJson.get("conversion_rates").getAsJsonObject();
            List<Moneda> listaMonedas = new ArrayList<>();

            for (String nombreMoneda : tasas.keySet()) { // El tasas.keySet devuelve el VALOR correspondiente a cada "llave", lo que en Json es un "nombre de moneda"
                double valor = tasas.get(nombreMoneda).getAsDouble();
                Moneda monedita = new Moneda(nombreMoneda, valor);
                listaMonedas.add(monedita);
            }

            return new TasaConversion(divisa, listaMonedas);
        } catch (Exception e) {
            throw new DivisaNoValida("La divisa " + moneda.toUpperCase() + " no existe o no está soportada."
                                        + "\nInserte de nuevo la divisa.");
        }
    }


    public boolean verificarMoneda(String monedaBusca, List<Moneda> listaMonedas) {
        for (Moneda moneda : listaMonedas) {
            if (moneda.getNombre().equalsIgnoreCase(monedaBusca)) {
                return true;
            }
        }
        return false;
    }


}
