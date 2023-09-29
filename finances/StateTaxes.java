package finances;
import java.util.HashMap;
import java.util.Map;

public class StateTaxes implements Taxes {

    private static double totalStateTaxes;
    private final double stateTax;
    private final String state;
    private final Map<String, Double>  stateTaxRates = new HashMap<>();
    public StateTaxes(double taxableWages, String state) {
        this.state = state;
        fillMap();
        this.stateTax = calculateTax(taxableWages);
        totalStateTaxes += this.stateTax;
    }

    //add this feature later on, too many variations
    private void fillMap() {
        stateTaxRates.put("TEXAS", 0.0);
        stateTaxRates.put("TENNESSEE", 0.0);
        stateTaxRates.put("SOUTH DAKOTA", 0.0);
        stateTaxRates.put("WYOMING", 0.0);
        stateTaxRates.put("ALASKA", 0.0);
        stateTaxRates.put("FLORIDA", 0.0);
        stateTaxRates.put("COLORADO", 4.4);
        stateTaxRates.put("ILLINOIS", 4.95);
        stateTaxRates.put("INDIANA", 3.23);
        stateTaxRates.put("NEW HAMPSHIRE", 5.0);
        stateTaxRates.put("PENNSYLVANIA", 3.07);
        stateTaxRates.put("UTAH", 4.85);
        stateTaxRates.put("WASHINGTON", 7.0);
        stateTaxRates.put("KENTUCKY", 5.0);
        stateTaxRates.put("MASSACHUSETTS", 5.0);
        stateTaxRates.put("NEVADA", 0.0);
        stateTaxRates.put("MICHIGAN", 3.07);
    }

    @Override
    public double calculateTax(double taxableWages) {
        Double rate = stateTaxRates.get(state.toUpperCase());
        return taxableWages * rate / 100;
    }

    @Override
    public double getTaxAmount() {
        return stateTax;
    }
    public static double getTotalStateTaxes() {
        return totalStateTaxes;
    }
}
