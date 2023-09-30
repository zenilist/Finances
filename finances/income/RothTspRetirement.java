package finances.income;

public class RothTspRetirement implements TspRetirement {
    private static double totalRothRetirement;
    private double contribution;

    public RothTspRetirement(double grossPay, double retirementContributionsRate) {
        this.contribution = calculateDeductions(grossPay, retirementContributionsRate);
    }

    public RothTspRetirement() {
    }

    ;

    private static void addToTotalContribution(double contribution) {
        totalRothRetirement += contribution;
    }

    @Override
    public double calculateDeductions(double GrossPay, double retirementContributionRate) {
        double contribution = GrossPay * (retirementContributionRate / 100);
        addToTotalContribution(contribution);
        return contribution;
    }

    @Override
    public double getTotalContributions() {
        return totalRothRetirement;
    }


    public double getContribution() {
        return contribution;
    }
}
