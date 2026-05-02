package persistance;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.Expense;
import model.ExpenseList;
import model.Income;
import model.IncomeList;

@ExcludeFromJacocoGeneratedReport
// **Referenced from the JSON demo application provided by CPSC 210 course instructors
//   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.** 
class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // test passes!
        }
    }

    @Test
    void testWriterEmptyBudget() {
        try {
            Budget testBudget = new Budget("David", "April", 2025);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");

            writer.open();
            writer.write(testBudget);
            writer.close();
            
            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            testBudget = reader.read();

            assertEquals("David", testBudget.getName());
            assertEquals("April", testBudget.getMonth());
            assertEquals(2025, testBudget.getYear());
            assertEquals(0, testBudget.getIncomeList().getSize());
            assertEquals(0, testBudget.getExpenseList().getSize());
        } catch (IOException e) {
            fail("No exception should be thrown!");
        }
    }

    @Test
    void testWriterRegularBudget() {
        try {
            Budget testBudget = new Budget("Jon", "October", 2024);
            testBudget.addIncome(new Income(100, "Domino's", "work", "monthly"));
            testBudget.addIncome(new Income(1000, "a", "gift", "one-time"));
            testBudget.addExpense(new Expense(11.11, "b", "membership", "monthly"));
            testBudget.addExpense(new Expense(1234, "c", "utilities", "one-time"));
            JsonWriter writer = new JsonWriter("./data/testWriterRegularBudget.json");

            writer.open();
            writer.write(testBudget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterRegularBudget.json");
            testBudget = reader.read();

            assertEquals("Jon", testBudget.getName());
            assertEquals("October", testBudget.getMonth());
            assertEquals(2024, testBudget.getYear());

            IncomeList il = testBudget.getIncomeList();
            assertEquals(2, il.getSize());
            assertEquals(100, il.getItem(0).getValue());
            assertEquals("Domino's", il.getItem(0).getDescription());
            assertEquals("work", il.getItem(0).getCategory());
            assertEquals("monthly", il.getItem(0).getFrequency());
            assertEquals(1000, il.getItem(1).getValue());
            assertEquals("a", il.getItem(1).getDescription());
            assertEquals("gift", il.getItem(1).getCategory());
            assertEquals("one-time", il.getItem(1).getFrequency());

            ExpenseList el = testBudget.getExpenseList();
            assertEquals(2, el.getSize());
            assertEquals(11.11, el.getItem(0).getValue());
            assertEquals("b", el.getItem(0).getDescription());
            assertEquals("membership", el.getItem(0).getCategory());
            assertEquals("monthly", el.getItem(0).getFrequency());
            assertEquals(1234, el.getItem(1).getValue());
            assertEquals("c", el.getItem(1).getDescription());
            assertEquals("utilities", el.getItem(1).getCategory());
            assertEquals("one-time", el.getItem(1).getFrequency());
        } catch (IOException e) {
            fail("No exception should be thrown!");
        }
    }
}
