package model;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
class ExpenseTest {
    private Expense myExpense;

    @BeforeEach
    void runBefore() {
        myExpense = new Expense(39.99, "New t-shirt", "personal care", "one-time");
    }

    @Test
    void testConstructor() {
        assertEquals(39.99, myExpense.getValue());
        assertEquals("New t-shirt", myExpense.getDescription());
        assertEquals("personal care", myExpense.getCategory());
        assertEquals("one-time", myExpense.getFrequency());
    }
}
