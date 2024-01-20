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

    /**
     *
     * @return current gross pay
     */
    public double getGrossPay() {
        return grossPay;
    }

    /**
     *
     * @return total accrued gross pay from checks
     */
    public static double getTotalGrossPay() {
        return totalGrossPay;
    }

}
