package model;

import org.json.JSONObject;

/*
 * A budget with the owner's name, the current month, 
 * a list of Income items, and a list of Expense items, 
 */
public class Budget {
    private String name;
    private String month;
    private int year;
    private IncomeList income;
    private ExpenseList expenses;

    /*
     * REQUIRES: the given month must be a valid calendar month
     * (e.g. "April"), the given year must be a valid year
     * EFFECTS: creates a new Budget with the given name and
     * empty lists of Income and Expense items
     */
    public Budget(String name, String month, int year) {
        this.name = name;
        this.month = month;
        this.year = year;
        income = new IncomeList();
        expenses = new ExpenseList();
        Event event = new Event("New budget created");
        EventLog.getInstance().logEvent(event);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the given item to the list of Income items for
     * this Budget
     */
    public void addIncome(Income item) {
        income.addIncome(item);
        Event event = new Event("Income added to budget");
        EventLog.getInstance().logEvent(event);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the given item to the list of Expense items for
     * this Budget
     */
    public void addExpense(Expense item) {
        expenses.addExpense(item);
        Event event = new Event("Expense added to budget");
        EventLog.getInstance().logEvent(event);
    }

    /*
     * REQUIRES: getIncomeList().getSize() > 0 and
     * -1 < index < getIncomeList().getSize()
     * MODIFIES: this
     * EFFECTS: removes the given item from the list of Income items
     * for this Budget
     */
    public void removeIncome(int index) {
        income.removeIncome(index);
        Event event = new Event("Income removed from budget");
        EventLog.getInstance().logEvent(event);
    }

    /*
     * REQUIRES: getExpenseList().getSize() > 0 and
     * -1 < index < getExpenseList().getSize()
     * MODIFIES: this
     * EFFECTS: removes the given item from the list of Expense items
     * for this Budget
     */
    public void removeExpense(int index) {
        expenses.removeExpense(index);
        Event event = new Event("Expense removed from budget");
        EventLog.getInstance().logEvent(event);
    }

    /*
     * REQUIRES: given savings and given cost must be in dollars ($)
     * EFFECTS: returns the number of months one would need
     * to wait in order to accumulate a value >= cost
     * starting from the given savings, based solely on the monthly
     * Income and Expense items in the budget (excl. one-time)
     * returns -1 if it is impossible to reach that amount
     */
    public int getProjection(double savings, double cost) {
        int months = 0;
        double amountNeeded = cost - savings;
        double monthlyNet = income.getMonthlyIncomeTotal() - expenses.getMonthlyExpensesTotal();

        Event event = new Event("Projection made");
        EventLog.getInstance().logEvent(event);

        if (amountNeeded <= 0) {
            return 0;
        } else if (monthlyNet <= 0) {
            return -1;
        } else {
            while (amountNeeded > 0) {
                amountNeeded -= monthlyNet;
                months++;
            }
        }

        return months;
    }

    /*
     * EFFECTS: returns the difference between total value of all
     * Income items and the total value of all Expense items
     */
    public double getNetTotal() {
        return income.getTotal() - expenses.getTotal();
    }

    public String getName() {
        return name;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public IncomeList getIncomeList() {
        return income;
    }

    public ExpenseList getExpenseList() {
        return expenses;
    }

    public void setName(String name) {
        this.name = name;
        Event event = new Event("Set budget's name");
        EventLog.getInstance().logEvent(event);
    }

    public void setMonth(String month) {
        this.month = month;
        Event event = new Event("Set budget's month");
        EventLog.getInstance().logEvent(event);
    }
    
    public void setYear(int year) {
        this.year = year;
        Event event = new Event("Set budget's year");
        EventLog.getInstance().logEvent(event);
    }

    // EFFECTS: returns this budget as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("month", month);
        json.put("year", year);
        json.put("income", income.toJson());
        json.put("expenses", expenses.toJson());
        return json;
    }
}
