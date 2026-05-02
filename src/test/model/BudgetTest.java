package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
class BudgetTest {
    private Income i1;
    private Income i2;
    private Income i3;

    private Expense e1;
    private Expense e2;
    private Expense e3;

    private Budget myBudget;

    @BeforeEach
    void runBefore() {
        myBudget = new Budget("David", "April", 2025);
        i1 = new Income(100, "a", "work", "monthly");
        i2 = new Income(1000, "b", "gift", "one-time");
        i3 = new Income(34.52, "c", "government benefit", "monthly");
        e1 = new Expense(45, "a", "personal care", "one-time");
        e2 = new Expense(234, "b", "utilities", "one-time");
        e3 = new Expense(100, "c", "food", "monthly");

        myBudget.addIncome(i1);
        myBudget.addIncome(i2);
        myBudget.addIncome(i3);
        myBudget.addExpense(e1);
        myBudget.addExpense(e2);
        myBudget.addExpense(e3);
    }

    @Test
    void testConstructor() {
        myBudget = new Budget("Jack", "January", 1999);
        assertEquals("Jack", myBudget.getName());
        assertEquals("January", myBudget.getMonth());
        assertEquals(1999, myBudget.getYear());
        assertEquals(0, myBudget.getIncomeList().getSize());
        assertEquals(0, myBudget.getExpenseList().getSize());
    }

    @Test
    void testAddIncome() {
        assertEquals(3, myBudget.getIncomeList().getSize());
        myBudget.addIncome(i1);
        assertEquals(4, myBudget.getIncomeList().getSize());
        assertEquals(100, myBudget.getIncomeList().getItem(3).getValue());
        myBudget.addIncome(i1);
        assertEquals(5, myBudget.getIncomeList().getSize());
        assertEquals(100, myBudget.getIncomeList().getItem(4).getValue());
    }

    @Test
    void testRemoveIncome() {
        assertEquals(3, myBudget.getIncomeList().getSize());
        myBudget.removeIncome(0);
        assertEquals(1000, myBudget.getIncomeList().getItem(0).getValue());
        assertEquals(34.52, myBudget.getIncomeList().getItem(1).getValue());
        assertEquals(2, myBudget.getIncomeList().getSize());
        myBudget.removeIncome(1);
        assertEquals(1000, myBudget.getIncomeList().getItem(0).getValue());
        assertEquals(1, myBudget.getIncomeList().getSize());
    }

    @Test
    void testAddExpense() {
        assertEquals(3, myBudget.getExpenseList().getSize());
        myBudget.addExpense(e1);
        assertEquals(4, myBudget.getExpenseList().getSize());
        assertEquals(45, myBudget.getExpenseList().getItem(3).getValue());
        myBudget.addExpense(e1);
        assertEquals(5, myBudget.getExpenseList().getSize());
        assertEquals(45, myBudget.getExpenseList().getItem(4).getValue());
    }

    @Test
    void testRemoveExpense() {
        assertEquals(3, myBudget.getExpenseList().getSize());
        myBudget.removeExpense(0);
        assertEquals(234, myBudget.getExpenseList().getItem(0).getValue());
        assertEquals(100, myBudget.getExpenseList().getItem(1).getValue());
        assertEquals(2, myBudget.getExpenseList().getSize());
        myBudget.removeExpense(1);
        assertEquals(234, myBudget.getExpenseList().getItem(0).getValue());
        assertEquals(1, myBudget.getExpenseList().getSize());
    }

    @Test 
    void testGetProjectionEmptyLists() {
        myBudget = new Budget("Sarah", "July", 2024);
        assertEquals(-1, myBudget.getProjection(0, 100));
    }

    @Test
    void testGetProjectionNoCost() {
        assertEquals(0, myBudget.getProjection(100, 0));
    }

    @Test
    void testGetProjectionExactMonthsRequiredNoSavings() {
        assertEquals(10, myBudget.getProjection(0, 345.20));
    }

    @Test
    void testGetProjectionExactMonthsRequiredWithSavings() {
        assertEquals(10, myBudget.getProjection(100, 445.20));
    }

    @Test
    void testGetProjectionNonExactMonthsRequiredNoSavings() {
        assertEquals(11, myBudget.getProjection(0, 365.20));
    }

    @Test
    void testGetProjectionNonExactMonthsRequiredWithSavings() {
        assertEquals(11, myBudget.getProjection(100, 465.20));
    }

    @Test
    void testGetProjectionNegativeMonthlyNetButJustEnoughSavings() {
        Expense myExpense = new Expense(100, "", "food", "monthly");
        myBudget.addExpense(myExpense);
        assertEquals(0, myBudget.getProjection(500.99, 500.99));
    }

    @Test
    void testGetProjectionNegativeMonthlyNetButExcessiveSavings() {
        Expense myExpense = new Expense(100, "", "food", "monthly");
        myBudget.addExpense(myExpense);
        assertEquals(0, myBudget.getProjection(1000, 500.99));
    }

    @Test
    void testGetProjectionNegativeMonthlyNetAndInsufficientSavings() {
        Expense myExpense = new Expense(100, "", "food", "monthly");
        myBudget.addExpense(myExpense);
        assertEquals(-1, myBudget.getProjection(500.98, 500.99));
    }

    @Test
    void testGetNetTotalPositiveAmount() {
        assertEquals(myBudget.getIncomeList().getTotal() - myBudget.getExpenseList().getTotal(), 
                myBudget.getNetTotal());
    }

    @Test
    void testGetNetTotalNegativeAmount() {
        Expense myExpense = new Expense(800, "", "gift", "one-time");
        myBudget.addExpense(myExpense);
        assertEquals(myBudget.getIncomeList().getTotal() - myBudget.getExpenseList().getTotal(), 
                myBudget.getNetTotal());
    }

    @Test
    void testSetters() {
        assertEquals("David", myBudget.getName());
        assertEquals("April", myBudget.getMonth());
        myBudget.setName("David");
        myBudget.setMonth("April");
        myBudget.setYear(2025);
        assertEquals("David", myBudget.getName());
        assertEquals("April", myBudget.getMonth());
        assertEquals(2025, myBudget.getYear());
        myBudget.setName("david");
        myBudget.setMonth("November");
        myBudget.setYear(2024);
        assertEquals("david", myBudget.getName());
        assertEquals("November", myBudget.getMonth());
        assertEquals(2024, myBudget.getYear());
    }
}
