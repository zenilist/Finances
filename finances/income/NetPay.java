package finances.income;

public class NetPay {
    static String state;
    private static double totalNetPay;
    private final double RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
    private final double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
    private double netPay;

    public NetPay(double RETIREMENT_ROTH_CONTRIBUTIONS_RATE, double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE) {
        this.RETIREMENT_ROTH_CONTRIBUTIONS_RATE = RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
        this.RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE = RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
    }

    public NetPay(double grossPay, double RETIREMENT_ROTH_CONTRIBUTIONS_RATE,
                  double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE, String state) {
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

    private static void fillOutputTable(Object[][] table) {
        GrossPay grossPay = new GrossPay();
        FederalTaxes federalTaxes = new FederalTaxes();
        StateTaxes stateTaxes = new StateTaxes();
        SocialSecurity socialSecurity = new SocialSecurity();
        Medicare medicare = new Medicare();
        TraditionalTspRetirement traditionalTspRetirement = new TraditionalTspRetirement();
        RothTspRetirement rothTspRetirement = new RothTspRetirement();
        Fers fers = new Fers();

        table[0] = new String[]{"Gross Pay", String.valueOf(grossPay.getTotalGrossPay())};
        table[1] = new String[]{"Federal Taxes", String.valueOf(federalTaxes.getTotalFederalTaxes())};
        table[2] = new String[]{"State Taxes", String.valueOf(stateTaxes.getTotalStateTaxes())};
        table[3] = new String[]{"Social Security Taxes", String.valueOf(socialSecurity.getTotalSocialSecurityWithheld())};
        table[4] = new String[]{"Medicare", String.valueOf(medicare.getTotalMedicareDeductions())};
        table[5] = new String[]{"Traditional TSP Retirement Fund", String.valueOf(traditionalTspRetirement.getTotalContributions())};
        table[6] = new String[]{"Roth TSP Retirement Fund", String.valueOf(rothTspRetirement.getTotalContributions())};
        table[7] = new String[]{"FERS FUND", String.valueOf(fers.getTotalFersContribution())};
        table[8] = new String[]{"Net Pay", String.valueOf(getTotalNetPay())};
    }

    private void calculateNetPay(double grossPay) {
        double rothContribution = getRothContribution(grossPay);
        double traditionalContribution = getTraditionalContribution(grossPay);

        double socialSecurityWithholding = calculateSocialSecurityWithholding(grossPay);

        double taxableWages = grossPay - rothContribution;

        double federalTaxAmount = calculateFederalTaxes(taxableWages);

        double stateTaxAmount = calculateStateTaxes(taxableWages);

        double fersContribution = calculateFersContribution(grossPay);

        double medicareDeduction = calculateMedicareDedection(grossPay);

        netPay = taxableWages - federalTaxAmount - traditionalContribution - socialSecurityWithholding
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

    private double calculateMedicareDedection(double grossPay) {
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

    public double getNetPay() {
        return netPay;
    }

    public void showIncomeBreakdown() {
        Object[][] table = new String[9][];

        fillOutputTable(table);

        System.out.format("--------------------------Income Breakdown--------------------------\n");
        System.out.format("%-50s%-50s\n", "Type", "Total Amount");
        for (Object[] row :
                table) {
            System.out.format("%-50s%-50s\n", row[0], row[1]);
        }
    }

}
