package finances;

public class NetPay {
    private final double grossPay;
    private double netPay;
    private final double RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
    private final double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
    private final String state;
    public NetPay(double grossPay, double RETIREMENT_ROTH_CONTRIBUTIONS_RATE,
                  double RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE, String state){
        this.RETIREMENT_ROTH_CONTRIBUTIONS_RATE = RETIREMENT_ROTH_CONTRIBUTIONS_RATE;
        this.RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE = RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE;
        this.grossPay = grossPay;
        this.state = state;
        calculateNetPay();

    }

    private void calculateNetPay() {
        double rothContribution = getRothContribution();
        double traditionalContribution = getTraditionalContribution();

        double socialSecurityWithholding = calculateSocialSecurityWithholding();

        double taxableWages = grossPay - rothContribution;

        double federalTaxAmount = calculateFederalTaxes(taxableWages);

        double stateTaxAmount = calculateStateTaxes(taxableWages);

        double fersContribution = calculateFersContribution();

        double medicareDeduction = calculateMedicareDedection();

        netPay = taxableWages - federalTaxAmount - traditionalContribution - socialSecurityWithholding
                - stateTaxAmount - fersContribution - medicareDeduction;
    }

    private double getTraditionalContribution() {
        TspRetirement traditional = new TraditionalTspRetirement(grossPay, RETIREMENT_TRADITIONAL_CONTRIBUTIONS_RATE);
        return traditional.getContribution();
    }

    private double getRothContribution() {
        TspRetirement roth = new RothTspRetirement(grossPay, RETIREMENT_ROTH_CONTRIBUTIONS_RATE);
        return roth.getContribution();
    }

    private double calculateMedicareDedection() {
        Medicare medicare = new Medicare(grossPay);
        return medicare.getMedicareDeduction();
    }

    private double calculateFersContribution() {
        Fers fers = new Fers(grossPay);
        return fers.getFersContribution();
    }

    private static double calculateFederalTaxes(double taxableWages) {
        Taxes federalTaxes = new FederalTaxes(taxableWages);

         return federalTaxes.getTaxAmount();
    }

    private double calculateStateTaxes(double taxableWages) {
        Taxes stateTaxes = new StateTaxes(taxableWages, state);

        return stateTaxes.getTaxAmount();
    }

    private double calculateSocialSecurityWithholding() {
        SocialSecurity socialSecurity = new SocialSecurity(grossPay);
        return socialSecurity.getSocialSecurityWithholding();
    }
    public double getNetPay() {
        return netPay;
    }

    public void showResult(){

    }

}
