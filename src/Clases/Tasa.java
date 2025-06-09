package Clases;

import java.util.List;

public record Tasa(String base_code,
                   List<Moneda> conversion_rates) {

}
