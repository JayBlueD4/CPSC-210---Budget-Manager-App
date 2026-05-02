package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

//A list of Expense items
public class ExpenseList {
    private ArrayList<Expense> list;

    /*
     * EFFECTS: Creates a new empty ExpenseList
     */
    public ExpenseList() {
        list = new ArrayList<Expense>();
    }

    /*
     * REQUIRES: given item must be a valid Expense item
     * as specified in the specification for
     * the Expense class constructor
     * MODIFIES: this
     * EFFECTS: adds the given item to this ExpenseList
     */
    public void addExpense(Expense item) {
        list.add(item);
    }

    /*
     * REQUIRES: 0 < getSize() and
     * -1 < index < getSize()
     * MODIFIES: this
     * EFFECTS: removes the item at the given index
     * of this ExpenseList
     */
    public void removeExpense(int index) {
        list.remove(index);
    }

    /*
     * REQUIRES: 0 < getSize(),
     * -1 < index < getSize(),
     * and the given value
     * must be a monetary amount in ($)
     * MODIFIES: this
     * EFFECTS: sets the value of the item at the given
     * index of this ExpenseList to the given value
     */
    public void modifyExpenseValue(int index, double value) {
        Expense item = list.get(index);
        item.setValue(value);
    }

    /*
     * REQUIRES: 0 < getSize() < index
     * MODIFIES: this
     * EFFECTS: sets the description of the item at the
     * given index of this ExpenseList to the
     * given description
     */
    public void modifyExpenseDescription(int index, String description) {
        Expense item = list.get(index);
        item.setDescription(description);
    }

    /*
     * REQUIRES: 0 < getSize(),
     * -1 < index < getSize(),
     * and the given category
     * must be a valid category, meaning it adheres
     * to the specifications for the Expense class
     * constructor
     * MODIFIES: this
     * EFFECTS: sets the category of the item at the given
     * index of this ExpenseList to the given category
     */
    public void modifyExpenseCategory(int index, String category) {
        Expense item = list.get(index);
        item.setCategory(category);
    }

    /*
     * REQUIRES: 0 < getSize(),
     * -1 < index < getSize(),
     * and the given frequency
     * must adhere to the requirements outlined in
     * the specifications for the constructor of the
     * Expense class
     * MODIFIES: this
     * EFFECTS: sets the frequency of the item at the given
     * index of this ExpenseList to the given frequency
     */
    public void modifyExpenseFrequency(int index, String frequency) {
        Expense item = list.get(index);
        item.setFrequency(frequency);
    }

    /*
     * EFFECTS: returns a list of paired strings, with
     * each first string matching an Expense category and
     * each second string representing the percentage (e.g. "11.254"
     * for 11.254%) that getCategoryTotal() for that Expense category
     * contributes to getTotal()
     * the order the categories are displayed in match the order of
     * categories listed in the Expense class specification
     * every category is checked, and those with no Expense items
     * will have a percentage of 0
     */
    @SuppressWarnings("methodlength")
    public ArrayList<String> getDistribution() {
        Event event = new Event("Retrieved expense distribution");
        EventLog.getInstance().logEvent(event);
        ArrayList<String> distribution = new ArrayList<String>();
        double total = this.getTotal();
        double foodTotal = this.getCategoryTotal("food");
        double entertainmentTotal = this.getCategoryTotal("entertainment");
        double personalCareTotal = this.getCategoryTotal("personal care");
        double giftTotal = this.getCategoryTotal("gift");
        double donationTotal = this.getCategoryTotal("donation");
        double transportationTotal = this.getCategoryTotal("transportation");
        double insuranceTotal = this.getCategoryTotal("insurance");
        double medicalTotal = this.getCategoryTotal("medical");
        double travelTotal = this.getCategoryTotal("travel");
        double utilitiesTotal = this.getCategoryTotal("utilities");
        double housingTotal = this.getCategoryTotal("housing");
        double educationTotal = this.getCategoryTotal("education");
        double membershipTotal = this.getCategoryTotal("membership");
        double savingsTotal = this.getCategoryTotal("savings");
        double otherTotal = this.getCategoryTotal("other");

        if (total == 0) {
            distribution.add("Food");
            distribution.add("0.0");
            distribution.add("Entertainment");
            distribution.add("0.0");
            distribution.add("Personal Care");
            distribution.add("0.0");
            distribution.add("Gift");
            distribution.add("0.0");
            distribution.add("Donation");
            distribution.add("0.0");
            distribution.add("Transportation");
            distribution.add("0.0");
            distribution.add("Insurance");
            distribution.add("0.0");
            distribution.add("Medical");
            distribution.add("0.0");
            distribution.add("Travel");
            distribution.add("0.0");
            distribution.add("Utilities");
            distribution.add("0.0");
            distribution.add("Housing");
            distribution.add("0.0");
            distribution.add("Education");
            distribution.add("0.0");
            distribution.add("Membership");
            distribution.add("0.0");
            distribution.add("Savings");
            distribution.add("0.0");
            distribution.add("Other");
            distribution.add("0.0");
        } else {
            distribution.add("Food");
            distribution.add(Double.toString(foodTotal / total * 100));
            distribution.add("Entertainment");
            distribution.add(Double.toString(entertainmentTotal / total * 100));
            distribution.add("Personal Care");
            distribution.add(Double.toString(personalCareTotal / total * 100));
            distribution.add("Gift");
            distribution.add(Double.toString(giftTotal / total * 100));
            distribution.add("Donation");
            distribution.add(Double.toString(donationTotal / total * 100));
            distribution.add("Transportation");
            distribution.add(Double.toString(transportationTotal / total * 100));
            distribution.add("Insurance");
            distribution.add(Double.toString(insuranceTotal / total * 100));
            distribution.add("Medical");
            distribution.add(Double.toString(medicalTotal / total * 100));
            distribution.add("Travel");
            distribution.add(Double.toString(travelTotal / total * 100));
            distribution.add("Utilities");
            distribution.add(Double.toString(utilitiesTotal / total * 100));
            distribution.add("Housing");
            distribution.add(Double.toString(housingTotal / total * 100));
            distribution.add("Education");
            distribution.add(Double.toString(educationTotal / total * 100));
            distribution.add("Membership");
            distribution.add(Double.toString(membershipTotal / total * 100));
            distribution.add("Savings");
            distribution.add(Double.toString(savingsTotal / total * 100));
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
     * EFFECTS: returns the size of this ExpenseList
     */
    public int getSize() {
        return list.size();
    }

    /*
     * REQUIRES: 0 < getSize() and
     * -1 < index < getSize()
     * EFFECTS: returns the Expense item at the given index of this
     * ExpenseList
     */
    public Expense getItem(int index) {
        return list.get(index);
    }

    /*
     * EFFECTS: returns a list of names of all Expense items in this ExpenseList
     *          if there are no items, returns an empty list
     */
    public HashMap<String, Integer> getExpenseDescriptions() {
        HashMap<String, Integer> descriptions = new HashMap<>();
        for (int i = 0; i < getSize(); i++) {
            descriptions.put(getItem(i).getDescription(), i);
        }

        return descriptions;
    }

    /*
     * REQUIRES: the given category must be a valid Expense category
     * (see the specification for the Expense class)
     * EFFECTS: returns the sum of the values for all Expense items in
     * this ExpenseList that have the given category (if none are
     * found, return 0)
     */
    public double getCategoryTotal(String category) {
        double total = 0;

        for (Expense e : list) {
            if (e.getCategory().equals(category)) {
                total += e.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if (getSize() == 0) or no monthly expenses in this
     * ExpenseList, return 0
     * otherwise, return the sum of the values of all monthly
     * expenses in this ExpenseList
     */
    public double getMonthlyExpensesTotal() {
        double total = 0;
        for (Expense e : list) {
            if (e.getFrequency().equals("monthly")) {
                total += e.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if (getSize() == 0) or no one-time expenses in this
     * ExpenseList, return 0
     * otherwise, return the sum of the values of all one-time
     * expenses in this ExpenseList
     */
    public double getOneTimeExpensesTotal() {
        double total = 0;
        for (Expense e : list) {
            if (e.getFrequency().equals("one-time")) {
                total += e.getValue();
            }
        }

        return total;
    }

    /*
     * EFFECTS: if (getSize() == 0) return 0
     * otherwise returns the sum of the values of each
     * Expense item in this ExpenseList
     */
    public double getTotal() {
        double total = 0;

        for (Expense e : list) {
            total += e.getValue();
        }
        return total;
    }

    // EFFECTS: returns Expense items in this ExpenseList as a JSON array
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < getSize(); i++) {
            jsonArray.put(getItem(i).toJson());
        }

        return jsonArray;
    }
}
