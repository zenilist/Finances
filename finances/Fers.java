package finances;

public class Fers {
    private static double totalFersContribution;
    private final double fersContribution;
    private final double RATE = 4.4;

    public Fers(double grossPay){
        this.fersContribution = calculateContribution(grossPay);
        totalFersContribution += fersContribution;
    }

    public static double getTotalFersContribution() {
        return totalFersContribution;
    }

    public double getFersContribution() {
        return fersContribution;
    }

    private double calculateContribution(double grossPay) {
        return grossPay * RATE / 100 ;
    }
}
