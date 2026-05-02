package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

//A list of Income items
public class IncomeList {
    private ArrayList<Income> list;

    /*
     * EFFECTS: creates a new empty IncomeList
     */
    public IncomeList() {
        list = new ArrayList<Income>();
    }

    /*
     * REQUIRES: given item must be a valid Income item
     *           as specified in the specification for 
     *           the Income class constructor
     * MODIFIES: this
     * EFFECTS: adds the given item to this IncomeList
     */
    public void addIncome(Income item) {
        list.add(item);
    }

    /*
     * REQUIRES: 0 < getSize() and
     *           -1 < index < getSize()
     * MODIFIES: this
     * EFFECTS: removes the item at the given index
     *          of this IncomeList 
     */
    public void removeIncome(int index) {
        list.remove(index);
    }

    /*
     * REQUIRES: 0 < getSize(),
     *           -1 < index < getSize(),
     *           and the given value 
     *           must be a monetary amount in ($)
     * MODIFIES: this
     * EFFECTS: sets the value of the item at the given 
     *          index of this IncomeList to the given value
     */
    public void modifyIncomeValue(int index, double value) {
        Income item = list.get(index);
        item.setValue(value);
    }

    /*
     * REQUIRES: 0 < getSize() and
     *           -1 < index < getSize()
     * MODIFIES: this
     * EFFECTS: sets the description of the item at the 
     *          given index of this IncomeList to the
     *          given description
     */
    public void modifyIncomeDescription(int index, String description) {
        Income item = list.get(index);
        item.setDescription(description);
    }

    /*
     * REQUIRES: 0 < getSize(),
     *           -1 < index < getSize(), 
     *           and the given category 
     *           must be valid category, meaning it adheres 
     *           to the specifications for the Income class
     *           constructor
     * MODIFIES: this
     * EFFECTS: sets the category of the item at the given 
     *          index of this IncomeList to the given category
     */
    public void modifyIncomeCategory(int index, String category) {
        Income item = list.get(index);
        item.setCategory(category);
    }

    /* REQUIRES: 0 < getSize(),
     *           -1 < index < getSize(),
     *           and the given frequency
     *           must adhere to the requirements outlined in 
     *           the specifications for the constructor of the
     *           Income class
     * MODIFIES: this
     * EFFECTS: sets the frequency of the item at the given
     *          index of this IncomeList to the given frequency 
     */
    public void modifyIncomeFrequency(int index, String frequency) {
        Income item = list.get(index);
        item.setFrequency(frequency);
    }

    /* 
     * EFFECTS: returns the BC provincial tax for the overall annual 
     *          taxable income (in $, not cut to 2 decimal places)
     *          based on the Income items in this IncomeList 
     *          (i.e. all Income items whose categories are not 
     *          "gift" or "other")
     *          if this IncomeList is empty, returns 0
     */
    @SuppressWarnings("methodlength")
    public double calculateProvincialTax() {
        Event event = new Event("Calculated overall provincial tax");
        EventLog.getInstance().logEvent(event);
        double annualIncome = 0;

        for (Income i : list) {
            if (i.getCategory().equals("work") || i.getCategory().equals("government benefit")) {
                annualIncome += ((i.getFrequency().equals("monthly") ? (i.getValue() * 12) : i.getValue()));
            }
        }
        
        if (annualIncome <= 49279) {
            return 0.0506 * annualIncome;
        } else if (annualIncome <= 98560) {
            return 0.0506 * 49279 + 0.077 * (annualIncome - 49279);
        } else if (annualIncome <= 113158) {
            return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * (annualIncome - 98560); 
        } else if (annualIncome <= 137407) {
            return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * (annualIncome - 113158);
        } else if (annualIncome <= 186306) {
            return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * 24249 + 0.147 * (annualIncome - 137407);
        } else if (annualIncome <= 259829) {
            return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * 24249 + 0.147 * 48899 
                   + 0.168 * (annualIncome - 186306);
        } else {
            return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * 24249 + 0.147 * 48899 
                   + 0.168 * 73523 + 0.205 * (annualIncome - 259829); 
        }
    }

    /* 
     * EFFECTS: returns the Canada federal tax for the overall annual 
     *          taxable income (in $, not cut to 2 decimal places)
     *          based on the Income items in this IncomeList 
     *          (i.e. all Income items whose categories are not 
     *          "gift" or "other")
     *          if this IncomeList is empty, returns 0
     */
    public double calculateFederalTax() {
        Event event = new Event("Calculated overall federal tax");
        EventLog.getInstance().logEvent(event);
        double annualIncome = 0;

        for (Income i : list) {
            if (i.getCategory().equals("work") || i.getCategory().equals("government benefit")) {
                annualIncome += ((i.getFrequency().equals("monthly") ? (i.getValue() * 12) : i.getValue()));
            }
        }

        if (annualIncome <= 57375) {
            return 0.145 * annualIncome;
        } else if (annualIncome <= 114750) {
            return 0.145 * 57375 + 0.205 * (annualIncome - 57375);
        } else if (annualIncome <= 177882) {
            return 0.145 * 57375 + 0.205 * 57375 + 0.26 * (annualIncome - 114750);
        } else if (annualIncome <= 253414) {
            return 0.145 * 57375 + 0.205 * 57375 + 0.26 * 63132 + 0.29 * (annualIncome - 177882);
        } else {
            return 0.145 * 57375 + 0.205 * 57375 + 0.26 * 63132 + 0.29 * 75532 + 0.33 * (annualIncome - 253414);
        }
    }

    /*
     * EFFECTS: returns a list of paired strings, with 
     *          each first string matching an Income category and 
     *          each second string representing the percentage (e.g. "11.254"
     *          for 11.254%) that getCategoryTotal() for that Income category 
     *          contributes to getTotal() 
     *          the order the categories are displayed in match the order of 
     *          categories listed in the Income class specification
     *          every category is checked, and those with no Income items 
     *          will have a percentage of 0
     */
    @SuppressWarnings("methodlength")
    public ArrayList<String> getDistribution() {
        Event event = new Event("Retrieved income distribution");
        EventLog.getInstance().logEvent(event);
        ArrayList<String> distribution = new ArrayList<String>();
        double total = this.getTotal();
        double workTotal = this.getCategoryTotal("work");
        double governmentBenefitTotal = this.getCategoryTotal("government benefit");
        double giftTotal = this.getCategoryTotal("gift");
        double otherTotal = this.getCategoryTotal("other");

        if (total == 0) {
            distribution.add("Work");
            distribution.add("0.0");
            distribution.add("Government Benefit");
            distribution.add("0.0");
            distribution.add("Gift");
            distribution.add("0.0");
            distribution.add("Other");
            distribution.add("0.0"); 
        } else {
            distribution.add("Work");
            distribution.add(Double.toString(workTotal / total * 100));
            distribution.add("Government Benefit");
            distribution.add(Double.toString(governmentBenefitTotal / total * 100));
            distribution.add("Gift");
            distribution.add(Double.toString(giftTotal / total * 100));
            distribution.add("Other");
            distribution.add(Double.toString(otherTotal / total * 100));
        }
        return distribution;
    }

    // EFFECTS: returns a list of the percentages retrieved from getDistribution() in order
    public ArrayList<Double> getDistributionPercentages() {
        ArrayList<Double> percentages = new ArrayList<>();
        ArrayList<String> percentageList = getDistribution();
        for (int i = 1; i < percentageList.size(); i += 2) {
            percentages.add(Double.parseDouble(percentageList.get(i)) / 100.0);
        }

        return percentages;
    }

    /*
     * EFFECTS: returns the size of this IncomeList
     */
    public int getSize() {
        return list.size();
    }

    /*
     * REQUIRES: 0 < getSize() and
     *           -1 < index < getSize()
     * EFFECTS: returns the Income item at the given index of this
     *          IncomeList
     */
    public Income getItem(int index) {
        return list.get(index);
    }

    /*
     * EFFECTS: returns a map of the descriptions & indices of all
     *          Income items in this IncomeList
     *          if there are no items, returns an empty map
     */
    public HashMap<String, Integer> getIncomeDescriptions() {
        HashMap<String, Integer> descriptions = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            descriptions.put(getItem(i).getDescription(), i);
        }

        return descriptions;
    }

    /*
     * REQUIRES: the given category must be a valid Income category
     *           (see the specification for the Income class)
     * EFFECTS: returns the sum of the values for all Income items in
     *          this IncomeList that have the given category (if none are
     *          found, return 0)
     */
    public double getCategoryTotal(String category) {
        double total = 0;

        for (Income i : list) {
            if (i.getCategory().equals(category)) {
                total += i.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if (getSize() == 0) or no monthly income in this
     *          IncomeList, return 0
     *          otherwise, return the sum of the values of all monthly
     *          income in this IncomeList
     */
    public double getMonthlyIncomeTotal() {
        double total = 0;
        for (Income i : list) {
            if (i.getFrequency().equals("monthly")) {
                total += i.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if (getSize() == 0) or no one-time income in this
     *          IncomeList, return 0
     *          otherwise, return the sum of the values of all one-time
     *          income in this IncomeList 
     */
    public double getOneTimeIncomeTotal() {
        double total = 0;
        for (Income i : list) {
            if (i.getFrequency().equals("one-time")) {
                total += i.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if getSize() == 0, returns 0
     *          otherwise returns the sum of the values of each
     *          Income item in this IncomeList
     */
    public double getTotal() {
        double total = 0;
        for (Income i : list) {
            total += i.getValue();
        }
        return total;
    }

    // EFFECTS: returns Income items in this IncomeList as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < getSize(); i++) {
            jsonArray.put(getItem(i).toJson());
        }

        return jsonArray;
    }
}
