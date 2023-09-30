package finances.income;

public class GrossPay {
    static double totalGrossPay;
    private double grossPay;

    public GrossPay(double grossPay) {
        this.grossPay = grossPay;
        totalGrossPay += this.grossPay;
    }

    public GrossPay() {

    }

    public double getGrossPay() {
        return grossPay;
    }

    public double getTotalGrossPay() {
        return totalGrossPay;
    }

}
