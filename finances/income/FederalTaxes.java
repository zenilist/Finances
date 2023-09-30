package finances.income;

public class FederalTaxes implements Taxes {

    private static double totalFederalTaxes = 0;
    private final int PAY_PERIODS = 26;
    private final double STANDARD_DEDUCTION = 12975;
    private final double[] taxBrackets = {11000, 44725, 95375, 182100, 231250, 578126, Double.MAX_VALUE};
    private final int[] taxRates = {10, 12, 22, 24, 32, 35, 37};
    private double federalTax = 0;

    public FederalTaxes(double taxableWages) {
        this.federalTax = calculateTax(taxableWages);
        totalFederalTaxes += federalTax;
    }

    public FederalTaxes() {
    }

    public double getTotalFederalTaxes() {
        return totalFederalTaxes;
    }

    public double getTaxAmount() {
        return federalTax;
    }

    @Override
    public double calculateTax(double taxableWages) {
        double projectedYearlyIncome = taxableWages * PAY_PERIODS;
        double federalTaxes = 0;
        int taxBracket = 0;
        double incomeLeftOver = projectedYearlyIncome - STANDARD_DEDUCTION;
        while (incomeLeftOver > 0) {
            federalTaxes += incomeLeftOver * taxRates[taxBracket] / 100;
            incomeLeftOver -= taxBrackets[taxBracket];
            taxBracket += 1;
        }
        return federalTaxes / PAY_PERIODS;
    }
}