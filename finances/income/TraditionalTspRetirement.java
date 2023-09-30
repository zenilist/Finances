package finances.income;

public class TraditionalTspRetirement implements TspRetirement {
    static double totalTraditionalContributions = 0;
    private double contribution;

    public TraditionalTspRetirement(double grossPay, double retirementTraditionalContributionsRate) {
        this.contribution = calculateDeductions(grossPay, retirementTraditionalContributionsRate);
    }

    public TraditionalTspRetirement() {
    }

    private static void addToTotalContribution(double contribution) {
        totalTraditionalContributions += contribution;
    }

    @Override
    public double calculateDeductions(double GrossPay, double retirementContributionRate) {
        double contribution = GrossPay * (retirementContributionRate / 100);
        addToTotalContribution(contribution);
        return contribution;
    }

    @Override
    public double getTotalContributions() {
        return totalTraditionalContributions;
    }

    public double getContribution() {
        return contribution;
    }
}
