package model;

/* An income item with a monetary value ($), an income description,
 * a category (one of "work", "government benefit", "gift", or "other"), and a 
 * frequency (i.e. monthly vs. one-time)
 */
public class Income extends MoneyItem {
    private static final String[] categories = {"work", "government benefit",
        "gift", "other"};

    /*
     * REQUIRES: the given value must be in $, meaning it has 2 decimal points
     * the given description cannot be an empty string
     * the given category must be one of the following:
     * - "work"
     * - "government benefit"
     * - "gift"
     * - "other"
     * the given frequency must either be "monthly" or "one-time"
     * EFFECTS: creates a new Income with the given value, description, category,
     * and frequency
     */
    public Income(double value, String description, String category, String frequency) {
        super(value, description, category, frequency);
    }

    /*
     * EFFECTS: if (category.equals("gift") || category.equals("other")),
     * income is not taxable and thus returns 0
     * otherwise, returns the provincial (BC) income tax (in $,
     * not cut to 2 decimal places) on the annual income received
     * from this individual Income
     */
    @SuppressWarnings("methodlength")
    public double calculateProvincialTax() {
        Event event = new Event("Calculated individual provincial tax for (" + description + ")");
        EventLog.getInstance().logEvent(event);
        if (category.equals("gift") || category.equals("other")) {
            return 0;
        } else {
            double annualIncome = ((frequency.equals("monthly") ? (value * 12) : value));

            if (annualIncome <= 49279) {
                return 0.0506 * annualIncome;
            } else if (annualIncome <= 98560) {
                return 0.0506 * 49279 + 0.077 * (annualIncome - 49279);
            } else if (annualIncome <= 113158) {
                return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * (annualIncome - 98560);
            } else if (annualIncome <= 137407) {
                return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * (annualIncome - 113158);
            } else if (annualIncome <= 186306) {
                return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598
                        + 0.1229 * 24249 + 0.147 * (annualIncome - 137407);
            } else if (annualIncome <= 259829) {
                return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598
                        + 0.1229 * 24249 + 0.147 * 48899 + 0.168 * (annualIncome - 186306);
            } else {
                return 0.0506 * 49279 + 0.077 * 49281 + 0.105 * 14598 + 0.1229 * 24249
                        + 0.147 * 48899 + 0.168 * 73523 + 0.205 * (annualIncome - 259829);
            }
        }
    }

    /*
     * EFFECTS: if (category.equals("gift") || category.equals("other")),
     * income is not taxable and thus returns 0
     * otherwise, returns the federal (Canada) income tax (in $,
     * not cut to 2 decimal places) on the annual income received
     * from this individual Income
     */
    public double calculateFederalTax() {
        Event event = new Event("Calculated individual federal tax for (" + description + ")");
        EventLog.getInstance().logEvent(event);
        if (category.equals("gift") || category.equals("other")) {
            return 0;
        } else {
            double annualIncome = ((frequency.equals("monthly") ? (value * 12) : value));

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
    }

    public static String[] getCategories() {
        return categories;
    }
}
