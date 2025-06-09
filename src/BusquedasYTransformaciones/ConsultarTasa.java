package BusquedasYTransformaciones;

import Clases.Moneda;
import Clases.Tasa;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ConsultarTasa {
    public Tasa consultarTasas(String moneda) {
        if(moneda.length() != 3){
            throw new RuntimeException("La moneda debe contener 3 letras.");
        }

        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/e2a1dc54f1ea9e34dc9949b1/latest/" + moneda.toUpperCase());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body() + "\n---------------------");
            JsonObject objetoJson = JsonParser.parseString(response.body()).getAsJsonObject();
            String divisa = objetoJson.get("base_code").getAsString();
            JsonObject tasas = objetoJson.get("conversion_rates").getAsJsonObject();
            List<Moneda> listaMonedas = new ArrayList<>();

            for(String nombreMoneda : tasas.keySet()){ // Él tasas.keySet devuelve el VALOR correspondiente a cada "llave", lo que en Json es un "nombre de moneda"
                double valor = tasas.get(nombreMoneda).getAsDouble();
                Moneda monedita = new Moneda(nombreMoneda, valor);
                listaMonedas.add(monedita);
            }

            return new Tasa(divisa, listaMonedas);
        } catch (Exception e) {
            throw new RuntimeException("Hubo un error." + e);
        }
    }




}
