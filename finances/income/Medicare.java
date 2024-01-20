package finances.income;

public class Medicare {
    private static double totalMedicareDeductions = 0;
    private final double RATE = 1.45;
    private double medicareDeduction;

    public Medicare(double grossPay) {
        this.medicareDeduction = calculateDeduction(grossPay);
        totalMedicareDeductions += medicareDeduction;
    }

    public Medicare() {
    }

    public static double getTotalMedicareDeductions() {
        return totalMedicareDeductions;
    }

    public double getMedicareDeduction() {
        return medicareDeduction;
    }

    private double calculateDeduction(double grossPay) {
        return grossPay * RATE / 100;
    }
}
