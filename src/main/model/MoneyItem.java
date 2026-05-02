package model;

import org.json.JSONObject;

/*An item with a monetary value ($), a description of that item,
a category, and a frequency (i.e. monthly vs. one-time)
*/
public class MoneyItem {
    private static final String[] frequencies = {"monthly", "one-time"};

    protected double value;
    protected String description;
    protected String category;
    protected String frequency;

    /*
     * REQUIRES: the given value must be in $ 
     *           the given description cannot be an empty string
     *           the given category cannot be an empty string
     *           the given frequency must either be "monthly" or "one-time" 
     * EFFECTS: creates a new MoneyItem with the given value, description, category, 
     *          and frequency
     */
    public MoneyItem(double value, String description, String category, String frequency) {
        this.value = value;
        this.description = description;
        this.category = category;
        this.frequency = frequency;
    }

    public double getValue() {
        return value;
    }
    
    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setValue(double value) {
        this.value = value;
        Event event = new Event("Value of (" + description + ") set");
        EventLog.getInstance().logEvent(event);
    }

    public void setDescription(String description) {
        Event event = new Event("Description of (" + description + ") set");
        EventLog.getInstance().logEvent(event);
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
        Event event = new Event("Category of (" + description + ") set");
        EventLog.getInstance().logEvent(event);
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
        Event event = new Event("Frequency of (" + description + ") set");
        EventLog.getInstance().logEvent(event);
    }

    // EFFECTS: returns this MoneyItem as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        json.put("description", description);
        json.put("category", category);
        json.put("frequency", frequency);
        return json;
    }
    
    public static String[] getFrequencies() {
        return frequencies;
    }
}
