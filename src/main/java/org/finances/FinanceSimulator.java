package org.finances;

import org.finances.expenses.Expense;
import org.finances.income.Income;

public class FinanceSimulator {
    public static void main(String [] args){
        Income.runIncomeSimulator();
        Expense.runExpenseSimulator();
    }
}
