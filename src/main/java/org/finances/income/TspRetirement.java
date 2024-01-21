package org.finances.income;

public interface TspRetirement {
    double calculateDeductions(double GrossPay, double retirementContributionRate);

    double getTotalContributions();

    double getContribution();
}
