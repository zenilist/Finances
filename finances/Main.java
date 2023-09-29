package finances;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("------------------Paycheck Calculator-----------------");
        Scanner scanner = new Scanner(System.in);
        double yearlySalary = Double.parseDouble(getInput(scanner, "Enter yearly salary: "));
        String payFrequency = getInput(scanner,"Enter pay frequency (weekly/biweekly/monthly/twice a month):").trim().toLowerCase();
        double rothTspRate = Double.parseDouble(getInput(scanner, "Enter ROTH TSP contribution rate: "));
        double traditionalTspRate = Double.parseDouble(getInput(scanner, "Enter Traditional TSP contribution rate: "));
        String state = getInput(scanner, "Enter state of residency");
        int payPeriod = getPayPeriod(payFrequency);
        scanner.close();
        double grossSalaryPerPayPeriod = (double)yearlySalary / payPeriod;
        GrossPay grossPay = new GrossPay(grossSalaryPerPayPeriod);
        NetPay netPay = new NetPay(grossPay.getGrossPay(), rothTspRate, traditionalTspRate, state);
        System.out.printf("Your " + payFrequency + " pays tub is: $" + netPay.getNetPay());
    }

    private static int getPayPeriod(String payFrequency) {
        return switch (payFrequency) {
            case "weekly" -> 52;
            case "biweekly" -> 26;
            case "monthly" -> 12;
            case "twice a month" -> 24;
            default -> 0;
        };
    }
    private static String getInput(Scanner scanner, String prompt){
        System.out.println(prompt);
        return scanner.next();
    }
}