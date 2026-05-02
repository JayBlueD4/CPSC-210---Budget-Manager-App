package persistance;

import java.io.*;

import org.json.JSONObject;

import model.Budget;
import model.Event;
import model.EventLog;

// A writer that writes the JSON representation of Budget objects to file
// **Referenced from the JSON demo application provided by CPSC 210 course instructors
//   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.** 
public class JsonWriter {

    private String destination;
    private PrintWriter writer;

    // EFFECTS: creates a new JsonWriter that writes to the destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer
    //          throws FileNotFoundException if destination file can't be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes the JSON representation of the given Budget b to file
    public void write(Budget b) {
        JSONObject json = b.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the given string to file
    public void saveToFile(String json) {
        writer.print(json);
        Event event = new Event("Saved current budget");
        EventLog.getInstance().logEvent(event);
    }
}
