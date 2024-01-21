package org.finances.income;

public interface Taxes {
    double calculateTax(double taxableWages);

    double getTaxAmount();
}
