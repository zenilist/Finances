package finances;

public class Medicare {
    private static double totalMedicareDeductions = 0;
    private double medicareDeduction;
    private final double RATE = 1.45;
    public Medicare(double grossPay) {
        this.medicareDeduction = calculateDeduction(grossPay);
    }

    public static double getTotalMedicareDeductions() {
        return totalMedicareDeductions;
    }

    public double getMedicareDeduction() {
        return medicareDeduction;
    }

    private double calculateDeduction(double grossPay) {
        return grossPay * RATE / 100 ;
    }
}
