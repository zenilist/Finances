package finances.income;

import finances.common.State;

import java.util.HashMap;
import java.util.Map;

public class StateTaxes implements Taxes {

    private static double totalStateTaxes;
    private final Map<State, Double> stateTaxRates = new HashMap<>();
    private double stateTax;
    private State state;

    public StateTaxes(double taxableWages, State state) throws IllegalArgumentException {
        this.state = state;
        fillMap();
        this.stateTax = calculateTax(taxableWages);
        totalStateTaxes += this.stateTax;
    }

    public StateTaxes() {
    }

    //add this feature later on, too many variations
    private void fillMap() {
        stateTaxRates.put(State.TX, 0.0);
        stateTaxRates.put(State.TN, 0.0);
        stateTaxRates.put(State.SD, 0.0);
        stateTaxRates.put(State.WY, 0.0);
        stateTaxRates.put(State.AK, 0.0);
        stateTaxRates.put(State.FL, 0.0);
        stateTaxRates.put(State.CO, 4.4);
        stateTaxRates.put(State.IL, 4.95);
        stateTaxRates.put(State.IN, 3.23);
        stateTaxRates.put(State.NH, 5.0);
        stateTaxRates.put(State.PA, 3.07);
        stateTaxRates.put(State.UT, 4.85);
        stateTaxRates.put(State.WA, 7.0);
        stateTaxRates.put(State.KY, 5.0);
        stateTaxRates.put(State.MA, 5.0);
        stateTaxRates.put(State.NV, 0.0);
        stateTaxRates.put(State.MI, 3.07);
    }

    @Override
    public double calculateTax(double taxableWages) {
        Double rate = stateTaxRates.get(state);
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
