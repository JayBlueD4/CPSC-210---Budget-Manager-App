package model;

/* 
 * An expense item with a monetary value ($), an income description,
 * a category (one of "food", "entertainment", "personal care", "gift", 
 * "donation", "transportation", "insurance", "medical", "travel",
 * "utilities", "housing", "education", "membership", "savings" or "other"), 
 * and a frequency (i.e. monthly vs. one-time)
*/
public class Expense extends MoneyItem {    
    private static final String[] categories = {"food", "entertainment",
        "personal care", "gift", "donation", "transportation", "insurance",
        "medical", "travel", "utilities", "housing", "education", "membership",
        "savings", "other"};

    /*
     * REQUIRES: the given value must be in $, meaning it has 2 decimal points
     *           the given description cannot be an empty string
     *           the given category must be one of the following:
     *              - "food"
     *              - "entertainment"
     *              - "personal care"
     *              - "gift"
     *              - "donation"
     *              - "transportation"
     *              - "insurance"
     *              - "medical"
     *              - "travel"
     *              - "utilities"
     *              - "housing"
     *              - "education"
     *              - "membership"
     *              - "savings"
     *              - "other"
     *           the given frequency must either be "monthly" or "one-time" 
     * EFFECTS: creates a new Expense with the given value, description, category, 
     *          and frequency
     */
    public Expense(double value, String description, String category, String frequency) {
        super(value, description, category, frequency);
    }

    public static String[] getCategories() {
        return categories;
    }
}
