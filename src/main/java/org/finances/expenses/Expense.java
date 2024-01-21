package org.finances.expenses;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.finances.common.Utils.getFile;
import static org.finances.common.Utils.getInput;

public class Expense {
    public static double getRent() {
        return rent;
    }

    public static double getElectricity() {
        return electricity;
    }

    public static double getUtilities() {
        return utilities;
    }

    public static double getGas() {
        return gas;
    }

    public static double getAutoInsurance() {
        return autoInsurance;
    }

    public static double getAutoLoan() {
        return autoLoan;
    }

    public static double getAutoGas() {
        return autoGas;
    }

    public static double getPhone() {
        return phone;
    }

    public static double getGroceries() {
        return groceries;
    }

    public static double getFood() {
        return food;
    }

    public static double getDiscretionary() {
        return discretionary;
    }
    public static double getAutoMaintenance(){
        return autoMaintenance;
    }

    private static double rent = 0;
    private static double electricity = 0;
    private static double utilities = 0;
    private static double gas = 0;
    private static double autoInsurance = 0;
    private static double autoLoan = 0;
    private static double autoGas = 0;
    private static double phone = 0;
    private static double groceries = 0;
    private static double food = 0;
    private static double discretionary = 0;
    private static double autoMaintenance = 0;

    private static double totalRent = 0;
    private static double totalElectricity = 0;
    private static double totalUtilities = 0;
    private static double totalGas = 0;
    private static double totalAutoInsurance = 0;
    private static double totalAutoLoan = 0;
    private static double totalAutoGas = 0;
    private static double totalPhone = 0;
    private static double totalGroceries = 0;
    private static double totalFood = 0;
    private static double totalDiscretionary = 0;
    private static double totalAutoMaintenance = 0;

    private static File csvFile;

    private static final Logger LOGGER = Logger.getLogger(Expense.class.getName());

    public static void runExpenseSimulator() {
        run();
    }

    private static void run() {
        System.out.println("--------------------------Run Expense Simulator--------------------------");
        System.out.println("-----------------------------Monthly expenses----------------------------");
        Scanner scanner = new Scanner(System.in);
        double month = getAmount(scanner, "---------------How many months do you want to simulate?------------------");
        boolean generateCsv = getInput(scanner, "Do you want a csv report for your expenses? (Y/N)").equals("Y");
        if (generateCsv) {
            csvFile = getFile("expense.csv");
            generateCsvHeader();
        }
        runCalculations(scanner);
        double totalExpenses = rent + electricity + utilities + gas + autoGas + autoInsurance + autoLoan + phone + discretionary + food + groceries;
        System.out.println("-----------------Total expenses : $" + totalExpenses + "------------------");
        addDataToCsv(month);
        multiplyExpensesByMonth(month, totalExpenses);
    }

    private static void addDataToCsv(double month) {
        for (double i = 0 ; i < month; i++){
            addRow();
        }
    }

    private static void addRow() {
        String[] csvData = getCsvData();
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile, true))) {
            csvWriter.writeNext(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] getCsvData() {
        List <Double> data = List.of(getRent(), getElectricity(), getUtilities(),
                getAutoInsurance(), getGas(), getAutoMaintenance(), getPhone(), getGroceries(),
                getDiscretionary(), getFood() );
        String[] csvData = new String[data.size()];
        for (int i = 0; i < csvData.length; i++){
            csvData[i] = String.valueOf(data.get(i));
        }
        return csvData;
    }

    private static void generateCsvHeader() {
        List<String> header = new ArrayList<>();
        for (ExpenseType expenseType : ExpenseType.values()){
            header.add(expenseType.toString());
        }
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
            csvWriter.writeNext(header.toArray(new String[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void multiplyExpensesByMonth(double month, double totalExpenses) {
        System.out.println("Expenses after " + month + " months : " + (month * totalExpenses));
    }

    private static void runCalculations(Scanner scanner) {
        rent = getAmount(scanner, "Enter rent");
        totalRent += rent;
        electricity = getAmount(scanner, "Enter electricity: ");
        totalElectricity += electricity;
        utilities = getAmount(scanner, "Enter utilities: ");
        totalUtilities += utilities;
        gas = getAmount(scanner, "Enter gas bill: ");
        totalGas += gas;
        autoGas = getAmount(scanner, "Enter auto gasoline bill: ");
        totalAutoGas += autoGas;
        autoInsurance = getAmount(scanner, "Enter autoInsurance bill: ");
        totalAutoInsurance += autoInsurance;
        autoLoan = getAmount(scanner, "Enter auto loan bill: ");
        totalAutoLoan += autoLoan;
        autoMaintenance = getAmount(scanner, "Enter auto maintenance bill: ");
        totalAutoMaintenance += autoMaintenance;
        phone = getAmount(scanner, "Enter phone bill: " );
        totalPhone += phone;
        discretionary = getAmount(scanner, "Enter discretionary: ");
        totalDiscretionary += discretionary;
        food = getAmount(scanner, "Enter dining: ");
        totalFood += food;
        groceries = getAmount(scanner, "Enter groceries");
        totalGroceries += groceries;
    }

    private static double getAmount(Scanner scanner, String prompt) {
        double result = 100;
        String in = getInput(scanner, prompt);
        if (in.isEmpty()) {
            LOGGER.severe("No value given!");
            LOGGER.info("Defaulting to 100");
            return result;
        }
        try {
            result = Double.parseDouble(in);
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid value!");
            LOGGER.info("Defaulting to 100");
            return result;
        }
        return result;
    }
}
