package finances.income;

import java.util.Scanner;

public class Income {
    private static double grossPayPerPayPeriod;

    public static void main(String[] args) {
        runSimulator();
    }

    public static void setGrossPayPerPayPeriod(double grossPayPerPayPeriod) {
        Income.grossPayPerPayPeriod = grossPayPerPayPeriod;
    }

    public static void runSimulator() {
        System.out.println("--------------------------Run Income Simulator--------------------------");
        System.out.println("How many iterations do you want the simulator to run for?");
        Scanner scanner = new Scanner(System.in);
        final int ITERATIONS = scanner.nextInt();
        for (int i = 0; i < ITERATIONS; i++) {
            if (i == 0) firstRun(scanner);
            else run(scanner);
            System.out.println("\n----------------------------Pay Period #" + (i + 1) + "----------------------------");
        }
    }

    private static void run(Scanner scanner) {
        double rothTspRate = Double.parseDouble(getInput(scanner, "Enter ROTH TSP contribution percent rate(1/2/...100): "));
        double traditionalTspRate = Double.parseDouble(getInput(scanner, "Enter Traditional TSP contribution percent rate(1/2/...100): "));
        GrossPay grossPay = new GrossPay(grossPayPerPayPeriod);
        NetPay netPay = new NetPay(grossPay.getGrossPay(), rothTspRate, traditionalTspRate, NetPay.state);
        System.out.println();
        netPay.showIncomeBreakdown();
    }

    private static void firstRun(Scanner scanner) {
        System.out.println("\n--------------------------Paycheck Calculator--------------------------");
        double yearlySalary = Double.parseDouble(getInput(scanner, "Enter yearly salary: "));
        String state = getInput(scanner, "Enter state of residency");
        String payFrequency = getInput(scanner, "Enter pay frequency (weekly/biweekly/monthly/twice a month):").trim().toLowerCase();
        int payPeriod = getPayPeriod(payFrequency);
        setGrossPayPerPayPeriod(yearlySalary / payPeriod);
        NetPay.state = state;
        run(scanner);
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

    private static String getInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.next();
    }
}
