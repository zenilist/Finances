package org.finances.income;

import com.opencsv.CSVWriter;
import org.finances.common.IncomeType;
import org.finances.common.State;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Income {
    private static final Logger LOGGER = Logger.getLogger("troubleshoot.log");
    private static double grossPayPerPayPeriod;
    private static boolean generateCsv = false;
    private static File csvFile;

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
        generateCsv = getInput(scanner, "Do you want a csv with all the data? (Y/N)").equals("Y");
        if (generateCsv) {
            csvFile = createCsvFile();
            addHeaderToCsv();
        }
        for (int i = 0; i < ITERATIONS; i++) {
            if (i == 0) firstRun(scanner);
            else run(scanner);
            System.out.println("\n----------------------------Pay Period #" + (i + 1) + "----------------------------");
        }
    }

    private static void addHeaderToCsv() {
        List<String> header = new ArrayList<>(List.of(IncomeType.GROSS_PAY.name(), IncomeType.FEDERAL_TAXES.name(),
                IncomeType.STATE_TAXES.name(), IncomeType.SOCIAL_SECURITY_TAXES.name(),
                IncomeType.MEDICARE_TAXES.name(), IncomeType.TRADITIONAL_TSP.name(),
                IncomeType.ROTH_TSP.name(), IncomeType.FERS.name(), IncomeType.NET_PAY.name()));
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            csvWriter.writeNext(header.toArray(new String[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createCsvFile() {
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
        return new File(currentDir + "/latestincome.csv");
    }

    private static void run(Scanner scanner) {
        double rothTspRate = Double.parseDouble(getInput(scanner, "Enter ROTH TSP contribution percent rate(1/2/...100): "));
        double traditionalTspRate = Double.parseDouble(getInput(scanner, "Enter Traditional TSP contribution percent rate(1/2/...100): "));
        GrossPay grossPay = new GrossPay(grossPayPerPayPeriod);
        NetPay netPay = new NetPay(grossPay.grossPay(), rothTspRate, traditionalTspRate, NetPay.state);
        System.out.println();
        netPay.showIncomeBreakdown();
        if (generateCsv){
            addPayPeriodDataToCsv(netPay);
        }
    }

    private static void addPayPeriodDataToCsv(NetPay netPay) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile, true))) {
            String[] csvData = getCsvData(netPay);
            csvWriter.writeNext(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] getCsvData(NetPay netPay) {
        List<Double> data = netPay.getCurrentData();
        String[] csvData = new String[data.size()];
        for (int i = 0; i < data.size(); i++){
            csvData[i] = String.valueOf(data.get(i));
        }
        return csvData;
    }

    private static void firstRun(Scanner scanner) {
        System.out.println("\n--------------------------Paycheck Calculator--------------------------");
        double yearlySalary = Double.parseDouble(getInput(scanner, "Enter yearly salary: "));
        State state;
        try {
            state = State.getValue(getInput(scanner, "Enter state of residency"));
        } catch (IllegalArgumentException e) {
            LOGGER.severe("Invalid state! Defaulting to Washington state.");
            state = State.WA;
        }
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
