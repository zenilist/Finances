package org.finances.income;

public record GrossPay(double grossPay) {
    static double totalGrossPay;

    public GrossPay(double grossPay) {
        this.grossPay = grossPay;
        totalGrossPay += this.grossPay;
    }

    /**
     * @return current gross pay
     */
    @Override
    public double grossPay() {
        return grossPay;
    }

    /**
     * @return total accrued gross pay from checks
     */
    public static double getTotalGrossPay() {
        return totalGrossPay;
    }

}
