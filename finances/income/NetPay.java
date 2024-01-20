package finances.income;

import finances.common.State;

public class NetPay {
    static State state;
    private static double totalNetPay;
    private final double RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
    private final double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;

    public NetPay(double grossPay, double RETIREMENT_ROTH_CONTRIBUTIONS_RATE,
                  double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE, State state) {
        this.RETIREMENT_ROTH_CONTRIBUTIONS_RATE = RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
        this.RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE = RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
        NetPay.state = state;
        calculateNetPay(grossPay);

    }

    private static double calculateFederalTaxes(double taxableWages) {
        Taxes federalTaxes = new FederalTaxes(taxableWages);

        return federalTaxes.getTaxAmount();
    }

    public static double getTotalNetPay() {
        return totalNetPay;
    }

    private static void fillOutputTable(String[][] table) {
        table[0] = new String[]{"Gross Pay", String.format("%.2f", new GrossPay().getTotalGrossPay())};
        table[1] = new String[]{"Federal Taxes", String.format("%.2f",new FederalTaxes().getTotalFederalTaxes())};
        table[2] = new String[]{"State Taxes", String.format("%.2f",new StateTaxes().getTotalStateTaxes())};
        table[3] = new String[]{"Social Security Taxes", String.format("%.2f", new SocialSecurity().getTotalSocialSecurityWithheld())};
        table[4] = new String[]{"Medicare", String.format("%.2f", new Medicare().getTotalMedicareDeductions())};
        table[5] = new String[]{"Traditional TSP Retirement Fund", String.format("%.2f", new TraditionalTspRetirement().getTotalContributions())};
        table[6] = new String[]{"Roth TSP Retirement Fund", String.format("%.2f", new RothTspRetirement().getTotalContributions())};
        table[7] = new String[]{"FERS FUND", String.format("%.2f", new Fers().getTotalFersContribution())};
        table[8] = new String[]{"Net Pay", String.format("%.2f",getTotalNetPay())};
    }

    private void calculateNetPay(double grossPay) {
        double rothContribution = getRothContribution(grossPay);
        double traditionalContribution = getTraditionalContribution(grossPay);

        double socialSecurityWithholding = calculateSocialSecurityWithholding(grossPay);

        double taxableWages = grossPay - rothContribution;

        double federalTaxAmount = calculateFederalTaxes(taxableWages);

        double stateTaxAmount = calculateStateTaxes(taxableWages);

        double fersContribution = calculateFersContribution(grossPay);

        double medicareDeduction = calculateMedicareDeduction(grossPay);

        double netPay = taxableWages - federalTaxAmount - traditionalContribution - socialSecurityWithholding
                - stateTaxAmount - fersContribution - medicareDeduction;

        totalNetPay += netPay;
    }

    private double getTraditionalContribution(double grossPay) {
        TspRetirement traditional = new TraditionalTspRetirement(grossPay, RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE);
        return traditional.getContribution();
    }

    private double getRothContribution(double grossPay) {
        TspRetirement roth = new RothTspRetirement(grossPay, RETIREMENT_ROTH_CONTRIBUTIONS_RATE);
        return roth.getContribution();
    }

    private double calculateMedicareDeduction(double grossPay) {
        Medicare medicare = new Medicare(grossPay);
        return medicare.getMedicareDeduction();
    }

    private double calculateFersContribution(double grossPay) {
        Fers fers = new Fers(grossPay);
        return fers.getFersContribution();
    }

    private double calculateStateTaxes(double taxableWages) {
        Taxes stateTaxes = new StateTaxes(taxableWages, state);

        return stateTaxes.getTaxAmount();
    }

    private double calculateSocialSecurityWithholding(double grossPay) {
        SocialSecurity socialSecurity = new SocialSecurity(grossPay);
        return socialSecurity.getSocialSecurityWithholding();
    }

    public void showIncomeBreakdown() {
        String[][] table = new String[9][];

        fillOutputTable(table);

        System.out.format("--------------------------Income Breakdown--------------------------\n");
        System.out.format("%-50s%-50s\n", "Type", "Total Amount");
        for (String[] row :
                table) {
            System.out.format("%-50s%-50s\n", row[0], row[1]);
        }
    }

}
