package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Budget;
import model.Event;
import model.EventLog;
import model.Expense;
import model.Income;

// A reader that reads JSON data stored in file and retrieves desired Budget data
// **Referenced from the JSON demo application provided by CPSC 210 course instructors
//   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.** 
public class JsonReader {

    private String source;

    // EFFECTS: creates a new JsonReader to read from the given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseBudget(json);
    }

    // EFFECTS: reads the given source file as a string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget from the given JSON object and returns it
    public Budget parseBudget(JSONObject json) {
        String name = json.getString("name");
        String month = json.getString("month");
        int year = json.getInt("year");
        Budget bg = new Budget(name, month, year);
        addIncomeList(bg, json);
        addExpenseList(bg, json);
        Event event = new Event("Loaded budget (" + name + ", " + month + " " + year + ")");
        EventLog.getInstance().logEvent(event);
        return bg;
    }

    // MODIFIES: bg
    // EFFECTS: parses the list of Income items from the 
    //          given JSON object and adds them to the given Budget
    private void addIncomeList(Budget bg, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("income");
        for (Object incomeJson : jsonArray) {
            JSONObject nextIncome = (JSONObject) incomeJson;
            addIncome(bg, nextIncome);
        }
    }

    // MODIFIES: bg
    // EFFECTS: parses the Income item from the given 
    //          JSON object and adds it to the given Budget
    private void addIncome(Budget bg, JSONObject json) {
        double value = json.getDouble("value");
        String description = json.getString("description");
        String category = json.getString("category");
        String frequency = json.getString("frequency");
        Income income = new Income(value, description, category, frequency);
        bg.getIncomeList().addIncome(income);
    }

    // MODIFIES: bg
    // EFFECTS: parses the list of Expense items from the 
    //          given JSON object and adds them to the given Budget
    private void addExpenseList(Budget bg, JSONObject json) {
        JSONArray jsonArray = json.getJSONArray("expenses");
        for (Object expenseJson : jsonArray) {
            JSONObject nextExpense = (JSONObject) expenseJson;
            addExpense(bg, nextExpense);
        }
    }

    // MODIFIES: bg
    // EFFECTS: parses the Expense item from the given 
    //          JSON object and adds it to the given Budget
    private void addExpense(Budget bg, JSONObject json) {
        double value = json.getDouble("value");
        String description = json.getString("description");
        String category = json.getString("category");
        String frequency = json.getString("frequency");
        Expense expense = new Expense(value, description, category, frequency);
        bg.getExpenseList().addExpense(expense);
    }
}
