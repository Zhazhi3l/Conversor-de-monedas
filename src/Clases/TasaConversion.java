package Clases;

import java.util.List;

public record TasaConversion(String base_code,
                             List<Moneda> conversion_rates) {
    @Override
    public String toString() {
        String tasas = "[";
        for (int i = 0; i < conversion_rates.size(); i++) {
            tasas += conversion_rates.get(i).toString();
            tasas += i == conversion_rates.size() - 1 ? "" : ", ";
        }
        return tasas + "]";
    }

    public String getBase_code() {
        return base_code;
    }

    public List<Moneda> getConversion_rates() {
        return conversion_rates;
    }
}
