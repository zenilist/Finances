package org.finances.income;

public class Fers {
    private static double totalFersContribution;
    private static final double RATE = 4.4;
    private double fersContribution;

    public Fers(double grossPay) {
        this.fersContribution = calculateContribution(grossPay);
        totalFersContribution += fersContribution;
    }

    public Fers() {
    }

    public double getTotalFersContribution() {
        return totalFersContribution;
    }

    public double getFersContribution() {
        return fersContribution;
    }

    private double calculateContribution(double grossPay) {
        return grossPay * RATE / 100;
    }
}
