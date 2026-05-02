package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.ExpenseList;
import model.IncomeList;

@ExcludeFromJacocoGeneratedReport
// **Referenced from the JSON demo application provided by CPSC 210 course instructors
//   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.** 
public class JsonReaderTest {

    @Test 
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget json = reader.read();
            fail("IOException Expected");
        }
        catch (IOException e) {
            // test passes!
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
        try {
            Budget testBudget = reader.read();
            assertEquals("David", testBudget.getName());
            assertEquals("April", testBudget.getMonth());
            assertEquals(0, testBudget.getIncomeList().getSize());
            assertEquals(0, testBudget.getExpenseList().getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderRegularBudget() {
        JsonReader reader = new JsonReader("./data/testWriterRegularBudget.json");
        try {
            Budget testBudget = reader.read();
            assertEquals("Jon", testBudget.getName());
            assertEquals("October", testBudget.getMonth());

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
            fail("Couldn't read from file");
        }
    }
}
