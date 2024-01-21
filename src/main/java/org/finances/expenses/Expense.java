package org.finances.expenses;

import java.util.Scanner;

public class Expense {
    public static void main(String [] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter rent:");
        double rent = scanner.nextDouble();
        System.out.println("Enter electricity:");
        double electricBill = scanner.nextDouble();
        System.out.println("Enter Utilities:");
        double utilities = scanner.nextDouble();
        System.out.println("Enter gas bill:");
        double gasBill = scanner.nextDouble();
        System.out.println("Enter car insurance payment:");
        double carInsurance = scanner.nextDouble();
        System.out.println("Enter auto Loan payment:");
        double autoLoanPayment = scanner.nextDouble();
        System.out.println("Enter auto gas payment");
        double gasPayment = scanner.nextDouble();
        System.out.println("Phone bill:");
        double phoneBill = scanner.nextDouble();
        System.out.println("Enter groceries bill:");
        double groceries = scanner.nextDouble();
        System.out.println("Enter discretionary:");
        double discretionary = scanner.nextDouble();
        System.out.println("Enter discretionary:");

        double totalExpenses = rent + electricBill + utilities + gasBill + carInsurance + autoLoanPayment + gasPayment + phoneBill + groceries + discretionary;
        System.out.println("Total expenses $" + totalExpenses);

    }
}
