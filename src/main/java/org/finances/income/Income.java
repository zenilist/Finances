package org.finances.income;

import com.opencsv.CSVWriter;
import org.finances.common.IncomeType;
import org.finances.common.State;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.finances.common.Utils.getFile;
import static org.finances.common.Utils.getInput;

public class Income {
    private static final Logger LOGGER = Logger.getLogger("troubleshoot.log");
    private static double grossPayPerPayPeriod;
    private static boolean generateCsv = false;
    private static boolean isVariableRate = false;
    private static File csvFile;
    private static double rothTspRate = 0;
    private static double traditionalTspRate = 0;
    public static void setGrossPayPerPayPeriod(double grossPayPerPayPeriod) {
        Income.grossPayPerPayPeriod = grossPayPerPayPeriod;
    }

    public static void runIncomeSimulator() {
        System.out.println("--------------------------Run Income Simulator--------------------------");
        System.out.println("How many iterations do you want the income simulator to run for?");
        Scanner scanner = new Scanner(System.in);
        final int ITERATIONS = scanner.nextInt();
        if (ITERATIONS < 1) {
            throw new IllegalArgumentException("Must be at least 1!");
        }
        generateCsv = getInput(scanner, "Do you want a csv with all the data? (Y/N)").equals("Y");
        isVariableRate = getInput(scanner, "Do you want to change retirement contribution on each pay period? (Y/N)").equals("Y");
        if (generateCsv) {
            csvFile = getFile("income.csv");
            addHeaderToCsv();
        }
        firstRun(scanner);
        System.out.println("\n----------------------------Pay Period #1----------------------------");
            for (int i = 1; i < ITERATIONS; i++) {
                generateNewRun(scanner, false);
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

    private static void generateNewRun(Scanner scanner, boolean firstRun) {
        if (isVariableRate || firstRun)
            getNewRates(scanner);
        GrossPay grossPay = new GrossPay(grossPayPerPayPeriod);
        NetPay netPay = new NetPay(grossPay.grossPay(), rothTspRate, traditionalTspRate, NetPay.state);
        System.out.println();
        netPay.showIncomeBreakdown();
        if (generateCsv){
            addPayPeriodDataToCsv(netPay);
        }
    }

    private static void getNewRates(Scanner scanner) {
        rothTspRate = Double.parseDouble(getInput(scanner, "Enter ROTH TSP contribution percent rate(1/2/...100): "));
        traditionalTspRate = Double.parseDouble(getInput(scanner, "Enter Traditional TSP contribution percent rate(1/2/...100): "));
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
            csvData[i] = String.format("%.2f",data.get(i));
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
        generateNewRun(scanner, true);
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

}
