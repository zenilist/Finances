package finances.income;

import finances.common.State;

/**
 * This class is used to calculate net pay from gross pay
 * @author avi
 */
public class NetPay {
    static State state;
    private static double totalNetPay;
    private final double RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
    private final double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;

    /**
     * Builds the constructor to various calculations on net pay
     * @param grossPay grossPay on each check
     * @param RETIREMENT_ROTH_CONTRIBUTIONS_RATE roth percent rate
     * @param RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE traditional percent rate
     * @param state state of residency
     */
    public NetPay(double grossPay, double RETIREMENT_ROTH_CONTRIBUTIONS_RATE,
                  double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE, State state) {
        this.RETIREMENT_ROTH_CONTRIBUTIONS_RATE = RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
        this.RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE = RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
        NetPay.state = state;
        calculateNetPay(grossPay);

    }

    /**
     *
     * @return Total accrued net pay
     */
    public static double getTotalNetPay() {
        return totalNetPay;
    }
    private void calculateNetPay(double grossPay) {
        double rothContribution = new RothTspRetirement(grossPay, RETIREMENT_ROTH_CONTRIBUTIONS_RATE).getContribution();
        double traditionalContribution = new TraditionalTspRetirement(grossPay, RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE).getContribution();
        double socialSecurityWithholding = new SocialSecurity(grossPay).getSocialSecurityWithholding();
        double federalTaxAmount = new FederalTaxes(getTaxableWages(grossPay, rothContribution)).getTaxAmount();
        double stateTaxAmount = new StateTaxes(getTaxableWages(grossPay, rothContribution), state).getTaxAmount();
        double fersContribution = new Fers(grossPay).getFersContribution();
        double medicareDeduction = new Medicare(grossPay).getMedicareDeduction();

        double netPay = getTaxableWages(grossPay, rothContribution) - federalTaxAmount - traditionalContribution - socialSecurityWithholding
                - stateTaxAmount - fersContribution - medicareDeduction;

        totalNetPay += netPay;
    }
    private static double getTaxableWages(double grossPay, double rothContribution) {
        return grossPay - rothContribution;
    }

    /**
     * Prints out a 2 column table with type and amount to the console
     */
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
    private static void fillOutputTable(String[][] table) {
        table[0] = new String[]{"Gross Pay", String.format("%.2f", GrossPay.getTotalGrossPay())};
        table[1] = new String[]{"Federal Taxes", String.format("%.2f", FederalTaxes.getTotalFederalTaxes())};
        table[2] = new String[]{"State Taxes", String.format("%.2f", StateTaxes.getTotalStateTaxes())};
        table[3] = new String[]{"Social Security Taxes", String.format("%.2f", SocialSecurity.getTotalSocialSecurityWithheld())};
        table[4] = new String[]{"Medicare", String.format("%.2f", Medicare.getTotalMedicareDeductions())};
        table[5] = new String[]{"Traditional TSP Retirement Fund", String.format("%.2f", new TraditionalTspRetirement().getTotalContributions())};
        table[6] = new String[]{"Roth TSP Retirement Fund", String.format("%.2f", new RothTspRetirement().getTotalContributions())};
        table[7] = new String[]{"FERS FUND", String.format("%.2f", new Fers().getTotalFersContribution())};
        table[8] = new String[]{"Net Pay", String.format("%.2f",getTotalNetPay())};
    }

}
