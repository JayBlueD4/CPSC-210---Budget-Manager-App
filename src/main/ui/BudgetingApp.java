package ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

//Budgeting Application
@ExcludeFromJacocoGeneratedReport
public class BudgetingApp {
    private Budget budget;
    private Scanner input;
    private JsonReader reader;
    private JsonWriter writer;

    // EFFECTS: runs the budgeting app
    public BudgetingApp() {
        runBudgetingApp();
    }

    // MODIFIES: this
    // EFFECTS: processes input from user
    public void runBudgetingApp() {
        input = new Scanner(System.in);
        int action = 0;

        introduceApp();
        createBudget();
        printBudget();

        while (true) {
            displayMenu();
            action = promptAction();
            commenceAction(action);
            promptMenuReturn();
            printBudget();
        }
    }

    // EFFECTS: displays an introduction to the budgeting app
    public void introduceApp() {
        System.out.println("\u001B[1m" + "Welcome to the Everyday Money Saver's Budgeting App! "
                + "With this app you can create a monthly budget, manage your money, "
                + "and save up for the future!"
                + "\u001B[0m" + "\n");
    }

    // MODIFIES: this
    // EFFECTS: initializes budget based on user input
    public void createBudget() {
        String name;
        String month;
        int year;

        System.out.println("Let's start by setting up your budget! :)");
        System.out.print("The first thing we need is your name: ");
        name = input.nextLine();
        System.out.print("Great! Now please enter the current month (e.g. April): ");
        month = input.next();
        System.out.print("Thanks! Finally, please enter the current year (e.g. 2025): ");
        year = input.nextInt();
        System.out.println("Awesome! Your budget has been created!\n");
        budget = new Budget(name, month, year);
    }

    // EFFECTS: prints the budget to the console
    public void printBudget() {
        printBorder();

        System.out.println("\u001B[1m\u001B[4m" + budget.getName() + "'s Budget - "
                + budget.getMonth() + " " + budget.getYear() + "\u001B[0m\n");

        printBudgetIncomeList();
        printIncomeTotal();

        printBudgetExpenseList();
        printExpensesTotal();

        System.out.printf("\n\u001B[1m\u001B[4m\u001B[34mNet Total: $%,.2f\u001B[0m\n\n", budget.getNetTotal());
        for (int i = 0; i < 200; i++) {
            System.out.print("-");
        }

        printBorder();
    }

    // EFFECTS: prints a horizontal border to the console
    public void printBorder() {
        System.out.println("\n");
        for (int i = 0; i < 200; i++) {
            System.out.print("-");
        }
        System.out.println("\n");
    }

    // EFFECTS: prints a formatted version of budget.getIncomeList()
    public void printBudgetIncomeList() {
        System.out.println("\u001B[1m\u001B[4m\u001B[32m" + "Income" + "\u001B[0m");
        for (int i = 0; i < budget.getIncomeList().getSize(); i++) {
            Income currentIncome = budget.getIncomeList().getItem(i);
            System.out.print((i + 1) + ". ");
            printIncome(currentIncome);
        }
    }

    // EFFECTS: prints information about the given Income item on the console
    public void printIncome(Income item) {
        System.out.print("\u001B[1m"
                + item.getDescription());
        System.out.printf(":  \u001B[32m$%,.2f\u001B[0m  ", item.getValue());
        System.out.println("\u001B[1m(\u001B[35mCategory\u001B[0m: " + item.getCategory() + ",  "
                + "\u001B[1m\u001B[38;5;208mFrequency\u001B[0m: " + item.getFrequency() + ")");
    }

    // EFFECTS: prints a formatted version of budget.getExpenseList()
    public void printBudgetExpenseList() {
        System.out.println("\n\u001B[1m\u001B[4m\u001B[31m" + "Expenses" + "\u001B[0m");
        for (int i = 0; i < budget.getExpenseList().getSize(); i++) {
            Expense currentExpense = budget.getExpenseList().getItem(i);
            System.out.print((i + 1) + ". ");
            printExpense(currentExpense);
        }
    }

    // EFFECTS: prints information about the given Expense item on the console
    public void printExpense(Expense item) {
        System.out.print("\u001B[1m"
                + item.getDescription());
        System.out.printf(":  \u001B[31m$%,.2f\u001B[0m  ", item.getValue());
        System.out.println("\u001B[1m(\u001B[35mCategory\u001B[0m: " + item.getCategory() + ",  "
                + "\u001B[1m\u001B[38;5;208mFrequency\u001B[0m: " + item.getFrequency() + ")");
    }

    // EFFECTS: displays the sum of the Income items in the budget on the console
    public void printIncomeTotal() {
        double total = budget.getIncomeList().getTotal();
        System.out.printf("\u001B[1m\u001B[32mTotal Income: $%,.2f\u001B[0m\n", total);
    }

    // EFFECTS: displays the sum of the Expense items in the budget on the console
    public void printExpensesTotal() {
        double total = budget.getExpenseList().getTotal();
        System.out.printf("\u001B[1m\u001B[31mTotal Expenses: $%,.2f\u001B[0m\n", total);
    }

    // EFFECTS: displays the menu with possible actions to the user
    public void displayMenu() {
        System.out.println("You can perform the following actions:");
        System.out.println("\t1: Add a new income");
        System.out.println("\t2: Add a new expense");
        System.out.println("\t3: Remove an income");
        System.out.println("\t4: Remove an expense");
        System.out.println("\t5: Modify an income");
        System.out.println("\t6: Modify an expense");
        System.out.println("\t7: Calculate the provincial tax on an individual income (for the year)");
        System.out.println("\t8: Calculate the federal tax on an individual income (for the year)");
        System.out.println("\t9: Calculate the provincial tax on overall income (for the year)");
        System.out.println("\t10: Calculate the federal tax on overall income (for the year)");
        System.out.println("\t11: Project with current savings how long to wait to buy an item");
        System.out.println("\t12: Calculate the total income from monthly income");
        System.out.println("\t13: Calculate the total income from one-time income");
        System.out.println("\t14: Calculate the total expenses from monthly expenses");
        System.out.println("\t15: Calculate the total expenses from one-time expenses");
        System.out.println("\t16: Get the distribution of income (%)");
        System.out.println("\t17: Get the distribution of expenses (%)");
        System.out.println("\t18: Save current budget");
        System.out.println("\t19: Load a budget from file");
        System.out.println("\t20: Start a new budget");
    }

    // EFFECTS: prompts the user to input an action
    public int promptAction() {
        System.out.print("\nPlease choose an action: ");
        return input.nextInt();
    }

    // MODIFIES: this
    // EFFECTS: carries out the given action
    @SuppressWarnings("methodlength")
    public void commenceAction(int action) {
        System.out.println("\n");
        switch (action) {
            case 1:
                addIncome();
                input.nextLine();
                break;
            case 2:
                addExpense();
                input.nextLine();
                break;
            case 3:
                removeIncome();
                input.nextLine();
                break;
            case 4:
                removeExpense();
                input.nextLine();
                break;
            case 5:
                modifyIncome();
                input.nextLine();
                break;
            case 6:
                modifyExpense();
                input.nextLine();
                break;
            case 7:
                calculateIndividualPT();
                input.nextLine();
                break;
            case 8:
                calculateIndividualFT();
                input.nextLine();
                break;
            case 9:
                calculateOverallPT();
                input.nextLine();
                break;
            case 10:
                calculateOverallFT();
                input.nextLine();
                break;
            case 11:
                makeProjection();
                input.nextLine();
                break;
            case 12:
                calculateTotalMonthlyIncome();
                input.nextLine();
                break;
            case 13:
                calculateTotalOneTimeIncome();
                input.nextLine();
                break;
            case 14:
                calculateTotalMonthlyExpenses();
                input.nextLine();
                break;
            case 15:
                calculateTotalOneTimeExpenses();
                input.nextLine();
                break;
            case 16:
                getIncomeDistribution();
                input.nextLine();
                break;
            case 17:
                getExpenseDistribution();
                input.nextLine();
                break;
            case 18:
                saveBudget();
                break;
            case 19:
                loadBudget();
                input.nextLine();
                break;
            case 20:
                startNewBudget();
                input.nextLine();
                break;
        }
    }

    /*
     * EFFECTS: prompts the user to press the ENTER key to return to the budget menu
     * if INCORRECT input is given, an error message displays and the user is
     * prompted once again to press the ENTER key
     */
    public void promptMenuReturn() {
        System.out.print("Press \u001B[1m\u001B[32mENTER\u001B[0m to return to the budget menu: ");
        while (!input.nextLine().isEmpty()) {
            System.out.println("\n\u001B[1m\u001B[31mError: Please press ENTER.\u001B[0m");
            System.out.print("Press \u001B[1m\u001B[32mENTER\u001B[0m to return to the budget menu: ");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an Income item to budget.getIncomeList() based on user input
    public void addIncome() {
        double value;
        String description;
        String category;
        String frequency;

        System.out.print("Enter value: ");
        value = input.nextDouble();
        System.out.print("Enter description: ");
        description = input.nextLine();
        description = input.nextLine();
        System.out.print("Enter category (i.e. work, government benefit, gift, other): ");
        category = input.nextLine();
        System.out.print("Enter frequency (monthly or one-time): ");
        frequency = input.next();

        budget.addIncome(new Income(value, description, category, frequency));
        System.out.println("\nOkay, new income added");
    }

    // MODIFIES: this
    // EFFECTS: adds an Expense item to budget.getExpenseList() based on user input
    public void addExpense() {
        double value;
        String description;
        String category;
        String frequency;

        System.out.print("Enter value: ");
        value = input.nextDouble();
        System.out.print("Enter description: ");
        description = input.nextLine();
        description = input.nextLine();
        System.out.println("Enter category (i.e. food, entertainment, personal care, "
                + "gift, donation, transportation, insurance, medical, ");
        System.out.print("\t\ttravel, utilities, housing, education, membership, savings, other): ");
        category = input.nextLine();
        System.out.print("Enter frequency (monthly or one-time): ");
        frequency = input.next();

        budget.addExpense(new Expense(value, description, category, frequency));
        System.out.println("\nOkay, new expense added");
    }

    // MODIFIES: this
    // EFFECTS: removes an Income item from budget.getIncomeList() based on user
    // input
    public void removeIncome() {
        int index;

        System.out.print("Give the corresponding number of the income you wish to remove: ");
        index = input.nextInt() - 1;

        budget.removeIncome(index);
        System.out.println("\nOkay, given income removed");
    }

    // MODIFIES: this
    // EFFECTS: removes an Expense item from budget.getExpenseList() based on user
    // input
    public void removeExpense() {
        System.out.print("Give the corresponding number of the expense you wish to remove: ");
        int index = input.nextInt() - 1;

        budget.removeExpense(index);
        System.out.println("\nOkay, given expense removed");
    }

    // MODIFIES: this
    // EFFECTS: modifies an Income item in budget.getIncomeList() based on user
    // input
    public void modifyIncome() {
        System.out.print("Give the corresponding number of the income you wish to modify: ");
        int index = input.nextInt() - 1;

        System.out.println("\nWhich do you want to modify?");
        System.out.println("\t1: value");
        System.out.println("\t2: description");
        System.out.println("\t3: category");
        System.out.println("\t4: frequency");
        System.out.print("\nEnter option: ");

        changeIncomeField(index);

        System.out.println("\nOkay, given income modified");
    }

    // MODIFIES: this
    // EFFECTS: modifies a certain field of the Income item at the given
    // index of budget.getIncomeList() based on user input
    public void changeIncomeField(int index) {
        switch (input.nextInt()) {
            case 1:
                System.out.print("\nOkay, enter the new value: ");
                budget.getIncomeList().modifyIncomeValue(index, input.nextDouble());
                break;
            case 2:
                System.out.print("\nOkay, enter the new description: ");
                input.nextLine();
                budget.getIncomeList().modifyIncomeDescription(index, input.nextLine());
                break;
            case 3:
                System.out.print("Okay, enter the new category (i.e. work, government benefit, gift, other): ");
                budget.getIncomeList().modifyIncomeCategory(index, input.next());
                break;
            case 4:
                System.out.print("\nOkay, enter the new frequency: ");
                budget.getIncomeList().modifyIncomeFrequency(index, input.next());
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies an Expense item from budget.getExpenseList() based on user
    // input
    public void modifyExpense() {
        System.out.print("Give the corresponding number of the expense you wish to modify: ");
        int index = input.nextInt() - 1;

        System.out.println("\nWhich do you want to modify?");
        System.out.println("\t1: value");
        System.out.println("\t2: description");
        System.out.println("\t3: category");
        System.out.println("\t4: frequency");
        System.out.print("\nEnter option: ");

        changeExpenseField(index);

        System.out.println("\nOkay, given expense modified");
    }

    // MODIFIES: this
    // EFFECTS: modifies a certain field of the Expense item at the given
    // index of budget.getExpenseList() based on user input
    public void changeExpenseField(int index) {
        switch (input.nextInt()) {
            case 1:
                System.out.print("\nOkay, enter the new value: ");
                budget.getExpenseList().modifyExpenseValue(index, input.nextDouble());
                break;
            case 2:
                System.out.print("\nOkay, enter the new description: ");
                input.nextLine();
                budget.getExpenseList().modifyExpenseDescription(index, input.nextLine());
                break;
            case 3:
                System.out.println("Okay, enter the new category (i.e. food, entertainment, personal care, "
                        + "gift, donation, transportation, insurance, medical, ");
                System.out.print("\t\ttravel, utilities, housing, education, membership, savings, other): ");
                budget.getExpenseList().modifyExpenseCategory(index, input.next());
                break;
            case 4:
                System.out.print("\nOkay, enter the new frequency: ");
                budget.getExpenseList().modifyExpenseFrequency(index, input.next());
                break;
        }
    }

    // EFFECTS: calculates and displays provincial tax on the yearly income of an
    // Income item from budget.getIncomeList() based on user input
    public void calculateIndividualPT() {
        double tax;

        System.out.print("Give the corresponding number of the income you wish to calculate tax for: ");
        int index = input.nextInt() - 1;

        tax = budget.getIncomeList().getItem(index).calculateProvincialTax();

        System.out.print("\nOkay, the \u001B[1mprovincial tax\u001B[0m on this income (for the year) is ");
        System.out.printf("\u001B[1m$%,.2f\u001B[0m\n\n", tax);
    }

    // EFFECTS: calculates and displays federal tax on the yearly income of an
    // Income item from budget.getIncomeList() based on user input
    public void calculateIndividualFT() {
        double tax;

        System.out.print("Give the corresponding number of the income you wish to calculate tax for: ");
        int index = input.nextInt() - 1;

        tax = budget.getIncomeList().getItem(index).calculateFederalTax();

        System.out.print("\nOkay, the \u001B[1mfederal tax\u001B[0m on this income (for the year) is ");
        System.out.printf("\u001B[1m$%,.2f\u001B[0m\n\n", tax);
    }

    // EFFECTS: calculates and displays provincial tax on the yearly income of all
    // taxable Income items from budget.getIncomeList() based on user input
    public void calculateOverallPT() {
        double tax;
        tax = budget.getIncomeList().calculateProvincialTax();

        System.out.print("\nOkay, the \u001B[1mprovincial tax\u001B[0m on your overall income (for the year) is ");
        System.out.printf("\u001B[1m$%,.2f\u001B[0m\n\n", tax);
    }

    // EFFECTS: calculates and displays federal tax on the yearly income of all
    // taxable Income items from budget.getIncomeList() based on user input
    public void calculateOverallFT() {
        double tax;
        tax = budget.getIncomeList().calculateFederalTax();

        System.out.print("\nOkay, the \u001B[1mfederal tax\u001B[0m on your overall income (for the year) is ");
        System.out.printf("\u001B[1m$%,.2f\u001B[0m\n\n", tax);
    }

    // EFFECTS: makes a projection on how long the user would need to save to
    // be able to purchase a particular item, based on user input
    public void makeProjection() {
        System.out.print("Enter the amount saved up thus far: ");
        double savings = input.nextDouble();
        System.out.print("Enter the value of the item you wish to purchase: ");
        double cost = input.nextDouble();

        int months = budget.getProjection(savings, cost);

        if (months == -1) {
            System.out.println("\nSorry, it is \u001B[31m\u001B[1mimpossible\u001B[0m "
                    + "for you to purchase this item with the current spending");
        } else {
            System.out.println("\nIt would take you \u001B[32m\u001B[1m" + months + " months\u001B[0m "
                    + "to purchase this item with the current spending\n");
        }
    }

    // EFFECTS: calculates and displays the total income from all monthly
    // Income items in budget.getIncomeList()
    public void calculateTotalMonthlyIncome() {
        double total = budget.getIncomeList().getMonthlyIncomeTotal();

        System.out.printf("\nThe total income you have from monthly income is \u001B[1m$%,.2f\u001B[0m\n\n", total);
    }

    // EFFECTS: calculates and displays the total income from all one-time
    // Income items in budget.getIncomeList()
    public void calculateTotalOneTimeIncome() {
        double total = budget.getIncomeList().getOneTimeIncomeTotal();

        System.out.printf("\nThe total income you have from one-time income is \u001B[1m$%,.2f\u001B[0m\n\n", total);
    }

    // EFFECTS: calculates and displays the total expense from all monthly
    // Expense items in budget.getExpenseList()
    public void calculateTotalMonthlyExpenses() {
        double total = budget.getExpenseList().getMonthlyExpensesTotal();

        System.out.printf("\nThe total expenses you have from monthly expenses is \u001B[1m$%,.2f\u001B[0m\n\n", total);
    }

    // EFFECTS: calculates and displays the total expense from all one-time
    // Expense items in budget.getExpenseList()
    public void calculateTotalOneTimeExpenses() {
        double total = budget.getExpenseList().getOneTimeExpensesTotal();

        System.out.print("\nThe total expenses you have from one-time expenses is ");
        System.out.printf("\u001B[1m$%,.2f\u001B[0m\n\n", total);
    }

    // EFFECTS: displays the percentage distribution of each Income category
    // based on budget.getIncomeList()
    public void getIncomeDistribution() {
        ArrayList<String> distribution = budget.getIncomeList().getDistribution();

        System.out.println("Okay, here is the distribution of your income:");
        for (int i = 0; i + 1 < distribution.size(); i += 2) {
            System.out.print("\t- " + distribution.get(i) + ": ");
            System.out.printf("%.1f", Double.parseDouble(distribution.get(i + 1)));
            System.out.println("%");
        }

        System.out.println();
    }

    // EFFECTS: displays the percentage distribution of each Expense category
    // based on budget.getExpenseList()
    public void getExpenseDistribution() {
        ArrayList<String> distribution = budget.getExpenseList().getDistribution();

        System.out.println("Okay, here is the distribution of your expenses:");
        for (int i = 0; i + 1 < distribution.size(); i += 2) {
            System.out.print("\t- " + distribution.get(i) + ": ");
            System.out.printf("%.1f", Double.parseDouble(distribution.get(i + 1)));
            System.out.println("%");
        }

        System.out.println();
    }

    // EFFECTS: saves the budget to a NEW file
    public void saveBudget() {
        System.out.print("What would you like to call your file (\u001B[1m\u001B[33m"
                + "typing an existing file name overwrites it\u001B[0m): ");
        input.nextLine();
        String savedName = input.nextLine();
        writer = new JsonWriter("./data/" + savedName + ".json");
        try {
            writer.open();
            writer.write(budget);
            writer.close();
            System.out.println("\n\u001B[1m\u001B[32mBudget successfully saved!\u001B[0m");
        } catch (IOException e) {
            System.out.println("\u001B[1m\u001B[31mError: Unable to write to file\u001B[0m");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a budget from file based on user input
    public void loadBudget() {
        File directory = new File("./data");
        File[] files = directory.listFiles();
        if (files.length == 0) {
            System.out.println("\u001B[1m\u001B[31mSorry! No saved budgets to load!\u001B[0m");
            return;
        } else {
            String[] fileNames = directory.list();
            displayFiles(fileNames);
            System.out.print("\nPlease enter the number corresponding to the budget you wish to load: ");
            String fileSource = "./data/" + fileNames[input.nextInt() - 1];
            System.out.println("\u001B[1m\u001B[32mOkay, loading file now...\u001B[0m\n");
            reader = new JsonReader(fileSource);
            try {
                budget = reader.read();
                System.out.println("\u001B[1m\u001B[32mBudget successfully loaded!\u001B[0m\n");
            } catch (IOException e) {
                System.out.println("\u001B[1m\u001B[31mError: Unable to read from file\u001B[0m");
            }
        }
    }

    // EFFECTS: displays the given list of fileNames
    public void displayFiles(String[] fileNames) {
        System.out.println("You currently have \u001B[1m\u001B[33m " 
                            + fileNames.length + " \u001B[0m files saved:");
        for (int i = 0; i < fileNames.length; i++) {
            String currentFileName = fileNames[i];
            System.out.println("\t" + (i + 1) + ". " + currentFileName);
        }
    }
    

    // MODIFIES: this
    // EFFECTS: creates a new Budget, assigning it to budget
    public void startNewBudget() {
        System.out.println("Okay, creating a new budget...\n");
        input.nextLine();
        createBudget();
    }
}
