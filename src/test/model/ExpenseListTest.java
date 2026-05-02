package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class ExpenseListTest {
    private ExpenseList myList; 

    @BeforeEach
    void runBefore() {
        myList = new ExpenseList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, myList.getSize());
    }

    @Test
    void testAddExpense() {
        Expense expenseA = new Expense(50, "a", "education", "one-time");
        Expense expenseB = new Expense(100, "b", "utilities", "monthly");

        assertEquals(0, myList.getSize());
        myList.addExpense(expenseA);
        assertEquals(50, myList.getItem(0).getValue());
        assertEquals(1, myList.getSize());
        myList.addExpense(expenseB);
        assertEquals(100, myList.getItem(1).getValue());
        assertEquals(2, myList.getSize());
    }

    @Test
    void testRemoveExpense() {
        Expense expenseA = new Expense(100, "a", "food", "one-time");
        Expense expenseB = new Expense(300, "b", "entertainment", "monthly");
        Expense expenseC = new Expense(500, "c", "gift", "one-time");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(100, myList.getItem(0).getValue());
        assertEquals(3, myList.getSize());
        myList.removeExpense(0);
        assertEquals(300, myList.getItem(0).getValue());
        assertEquals(2, myList.getSize());
        myList.removeExpense(1);
        assertEquals(300, myList.getItem(0).getValue());
        assertEquals(1, myList.getSize());
        myList.removeExpense(0);
        assertEquals(0, myList.getSize());
    }

    @Test
    void testModifyExpenseValue() {
        Expense expenseA = new Expense(100, "a", "donation", "one-time");
        Expense expenseB = new Expense(300, "b", "housing", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        
        assertEquals(100, myList.getItem(0).getValue());
        myList.modifyExpenseValue(0, 10);
        assertEquals(10, myList.getItem(0).getValue());
        myList.modifyExpenseValue(0, 1000);
        assertEquals(1000, myList.getItem(0).getValue());
        assertEquals(300, myList.getItem(1).getValue());
        myList.modifyExpenseValue(1, 30);
        assertEquals(30, myList.getItem(1).getValue());
    }

    @Test
    void testModifyExpenseDescription() {
        Expense expenseA = new Expense(100, "a", "personal care", "one-time");
        Expense expenseB = new Expense(300, "b", "membership", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        
        assertEquals("a", myList.getItem(0).getDescription());
        myList.modifyExpenseDescription(0, "a");
        assertEquals("a", myList.getItem(0).getDescription());
        myList.modifyExpenseDescription(0, "b");
        assertEquals("b", myList.getItem(0).getDescription());
        assertEquals("b", myList.getItem(1).getDescription());
        myList.modifyExpenseDescription(1, "c");
        assertEquals("c", myList.getItem(1).getDescription());
    }

    @Test
    void testModifyExpenseCategory() {
        Expense expenseA = new Expense(100, "a", "savings", "one-time");
        Expense expenseB = new Expense(300, "b", "entertainment", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        
        assertEquals("savings", myList.getItem(0).getCategory());
        myList.modifyExpenseCategory(0, "gift");
        assertEquals("gift", myList.getItem(0).getCategory());
        myList.modifyExpenseCategory(0, "other");
        assertEquals("other", myList.getItem(0).getCategory());
        assertEquals("entertainment", myList.getItem(1).getCategory());
        myList.modifyExpenseCategory(1, "medical");
        assertEquals("medical", myList.getItem(1).getCategory());
    }

    @Test
    void testModifyExpenseFrequency() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        
        assertEquals("one-time", myList.getItem(0).getFrequency());
        myList.modifyExpenseFrequency(0, "monthly");
        assertEquals("monthly", myList.getItem(0).getFrequency());
        myList.modifyExpenseFrequency(0, "one-time");
        assertEquals("one-time", myList.getItem(0).getFrequency());
        assertEquals("monthly", myList.getItem(1).getFrequency());
        myList.modifyExpenseFrequency(1, "one-time");
        assertEquals("one-time", myList.getItem(1).getFrequency());
    }

    @Test
    void testGetDistributionEmpty() {
        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(30, distribution.size());
        assertEquals("Food", distribution.get(0));
        assertEquals("0.0", distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals("0.0", distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals("0.0", distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals("0.0", distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals("0.0", distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals("0.0", distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals("0.0", distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals("0.0", distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals("0.0", distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals("0.0", distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals("0.0", distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals("0.0", distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals("0.0", distribution.get(29));
    }

    @Test
    void testGetDistributionSingleExpense() {
        Expense e1 = new Expense(100.11, "a", "entertainment", "one-time");
        myList.addExpense(e1);

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(30, distribution.size());
        assertEquals("Food", distribution.get(0));
        assertEquals("0.0", distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals("100.0", distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals("0.0", distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals("0.0", distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals("0.0", distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals("0.0", distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals("0.0", distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals("0.0", distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals("0.0", distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals("0.0", distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals("0.0", distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals("0.0", distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals("0.0", distribution.get(29));

        myList.modifyExpenseCategory(0, "food");
        distribution = myList.getDistribution();
        assertEquals("Food", distribution.get(0));
        assertEquals("100.0", distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals("0.0", distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals("0.0", distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals("0.0", distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals("0.0", distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals("0.0", distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals("0.0", distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals("0.0", distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals("0.0", distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals("0.0", distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals("0.0", distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals("0.0", distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals("0.0", distribution.get(29));
    }

    @Test 
    void testGetDistributionMultipleExpensesOfSameCategory() {
        Expense e1 = new Expense(100.11, "a", "entertainment", "one-time");
        Expense e2 = new Expense(11.25, "b", "entertainment", "monthly");
        myList.addExpense(e1);
        myList.addExpense(e2);

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(30, distribution.size());
        assertEquals("Food", distribution.get(0));
        assertEquals("0.0", distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals("100.0", distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals("0.0", distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals("0.0", distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals("0.0", distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals("0.0", distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals("0.0", distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals("0.0", distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals("0.0", distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals("0.0", distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals("0.0", distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals("0.0", distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals("0.0", distribution.get(29));
    }

    @Test
    void testGetDistributionOneExpenseInMultipleCategories() {
        Expense e1 = new Expense(123.75, "a", "entertainment", "one-time");
        Expense e2 = new Expense(26.25, "b", "food", "monthly");
        Expense e3 = new Expense(100, "c", "other", "one-time");
        myList.addExpense(e1);
        myList.addExpense(e2);
        myList.addExpense(e3);

        double total = myList.getTotal();

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(30, distribution.size());
        assertEquals("Food", distribution.get(0));
        assertEquals(Double.toString(26.25 / total * 100), distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals(Double.toString(123.75 / total * 100), distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals("0.0", distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals("0.0", distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals("0.0", distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals("0.0", distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals("0.0", distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals("0.0", distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals("0.0", distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals("0.0", distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals("0.0", distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals("0.0", distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals(Double.toString(100 / total * 100), distribution.get(29));
    }

    @Test
    void testGetDistributionMixedNumExpensesInAllCategories() {
        Expense e1 = new Expense(123.75, "a", "entertainment", "one-time");
        Expense e2 = new Expense(103.55, "b", "entertainment", "monthly");
        Expense e3 = new Expense(26.25, "c", "food", "monthly");
        Expense e4 = new Expense(21.50, "d", "food", "one-time");
        Expense e5 = new Expense(100, "e", "other", "one-time");
        Expense e6 = new Expense(20.44, "f", "utilities", "one-time");
        Expense e7 = new Expense(20.49, "g", "medical", "monthly");
        Expense e8 = new Expense(100, "h", "other", "one-time");
        Expense e9 = new Expense(13.55, "i", "entertainment", "monthly");
        Expense e10 = new Expense(12.99, "j", "entertainment", "monthly");
        Expense e11 = new Expense(109.11, "k", "food", "one-time");
        Expense e12 = new Expense(2.44, "l", "utilities", "one-time");
        Expense e13 = new Expense(200, "m", "medical", "one-time");
        Expense e14 = new Expense(319, "n", "travel", "one-time");
        Expense e15 = new Expense(10, "o", "travel", "monthly");
        Expense e16 = new Expense(10.99, "p", "insurance", "monthly");
        Expense e17 = new Expense(14.99, "q", "insurance", "monthly");
        Expense e18 = new Expense(5.99, "r", "personal care", "monthly");
        Expense e19 = new Expense(500, "s", "savings", "monthly");
        Expense e20 = new Expense(245.51, "t", "gift", "one-time");
        Expense e21 = new Expense(200, "u", "donation", "one-time");
        Expense e22 = new Expense(30, "v", "transportation", "monthly");
        Expense e23 = new Expense(59.95, "w", "education", "one-time");
        Expense e24 = new Expense(12.17, "x", "membership", "monthly");
        Expense e25 = new Expense(119.97, "y", "housing", "monthly");

        myList.addExpense(e1);
        myList.addExpense(e2);
        myList.addExpense(e3);
        myList.addExpense(e4);
        myList.addExpense(e5);
        myList.addExpense(e6);
        myList.addExpense(e7);
        myList.addExpense(e8);
        myList.addExpense(e9);
        myList.addExpense(e10);
        myList.addExpense(e11);
        myList.addExpense(e12);
        myList.addExpense(e13);
        myList.addExpense(e14);
        myList.addExpense(e15);
        myList.addExpense(e16);
        myList.addExpense(e17);
        myList.addExpense(e18);
        myList.addExpense(e19);
        myList.addExpense(e20);
        myList.addExpense(e21);
        myList.addExpense(e22);
        myList.addExpense(e23);
        myList.addExpense(e24);
        myList.addExpense(e25);

        double total = myList.getTotal();
        double foodTotal = myList.getCategoryTotal("food");
        double entertainmentTotal = myList.getCategoryTotal("entertainment");
        double personalCareTotal = myList.getCategoryTotal("personal care");
        double giftTotal = myList.getCategoryTotal("gift");
        double donationTotal = myList.getCategoryTotal("donation");
        double transportationTotal = myList.getCategoryTotal("transportation");
        double insuranceTotal = myList.getCategoryTotal("insurance");
        double medicalTotal = myList.getCategoryTotal("medical");
        double travelTotal = myList.getCategoryTotal("travel");
        double utilitiesTotal = myList.getCategoryTotal("utilities");
        double housingTotal = myList.getCategoryTotal("housing");
        double educationTotal = myList.getCategoryTotal("education");
        double membershipTotal = myList.getCategoryTotal("membership");
        double savingsTotal = myList.getCategoryTotal("savings");
        double otherTotal = myList.getCategoryTotal("other");

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(30, distribution.size());
        assertEquals("Food", distribution.get(0));
        assertEquals(Double.toString(foodTotal / total * 100), distribution.get(1));
        assertEquals("Entertainment", distribution.get(2));
        assertEquals(Double.toString(entertainmentTotal / total * 100), distribution.get(3));
        assertEquals("Personal Care", distribution.get(4));
        assertEquals(Double.toString(personalCareTotal / total * 100), distribution.get(5));
        assertEquals("Gift", distribution.get(6));
        assertEquals(Double.toString(giftTotal / total * 100), distribution.get(7));
        assertEquals("Donation", distribution.get(8));
        assertEquals(Double.toString(donationTotal / total * 100), distribution.get(9));
        assertEquals("Transportation", distribution.get(10));
        assertEquals(Double.toString(transportationTotal / total * 100), distribution.get(11));
        assertEquals("Insurance", distribution.get(12));
        assertEquals(Double.toString(insuranceTotal / total * 100), distribution.get(13));
        assertEquals("Medical", distribution.get(14));
        assertEquals(Double.toString(medicalTotal / total * 100), distribution.get(15));
        assertEquals("Travel", distribution.get(16));
        assertEquals(Double.toString(travelTotal / total * 100), distribution.get(17));
        assertEquals("Utilities", distribution.get(18));
        assertEquals(Double.toString(utilitiesTotal / total * 100), distribution.get(19));
        assertEquals("Housing", distribution.get(20));
        assertEquals(Double.toString(housingTotal / total * 100), distribution.get(21));
        assertEquals("Education", distribution.get(22));
        assertEquals(Double.toString(educationTotal / total * 100), distribution.get(23));
        assertEquals("Membership", distribution.get(24));
        assertEquals(Double.toString(membershipTotal / total * 100), distribution.get(25));
        assertEquals("Savings", distribution.get(26));
        assertEquals(Double.toString(savingsTotal / total * 100), distribution.get(27));
        assertEquals("Other", distribution.get(28));
        assertEquals(Double.toString(otherTotal / total * 100), distribution.get(29));
    }      

    @Test
    void testGetDistributionPercentagesEmpty() {
        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(15, distributionPercentages.size());
        assertEquals(0.0, distributionPercentages.get(0));
        assertEquals(0.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        assertEquals(0.0, distributionPercentages.get(4));
        assertEquals(0.0, distributionPercentages.get(5));
        assertEquals(0.0, distributionPercentages.get(6));
        assertEquals(0.0, distributionPercentages.get(7));
        assertEquals(0.0, distributionPercentages.get(8));
        assertEquals(0.0, distributionPercentages.get(9));
        assertEquals(0.0, distributionPercentages.get(10));
        assertEquals(0.0, distributionPercentages.get(11));
        assertEquals(0.0, distributionPercentages.get(12));
        assertEquals(0.0, distributionPercentages.get(13));
        assertEquals(0.0, distributionPercentages.get(14));
    }

    @Test
    void testGetDistributionPercentagesSingleExpense() {
        Expense e1 = new Expense(100.11, "a", "entertainment", "one-time");
        myList.addExpense(e1);

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(15, distributionPercentages.size());
        assertEquals(0.0, distributionPercentages.get(0));
        assertEquals(100.0 / 100.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        assertEquals(0.0, distributionPercentages.get(4));
        assertEquals(0.0, distributionPercentages.get(5));
        assertEquals(0.0, distributionPercentages.get(6));
        assertEquals(0.0, distributionPercentages.get(7));
        assertEquals(0.0, distributionPercentages.get(8));
        assertEquals(0.0, distributionPercentages.get(9));
        assertEquals(0.0, distributionPercentages.get(10));
        assertEquals(0.0, distributionPercentages.get(11));
        assertEquals(0.0, distributionPercentages.get(12));
        assertEquals(0.0, distributionPercentages.get(13));
        assertEquals(0.0, distributionPercentages.get(14));

        myList.modifyExpenseCategory(0, "food");
        distributionPercentages = myList.getDistributionPercentages();
        assertEquals(100.0 / 100.0, distributionPercentages.get(0));
        assertEquals(0.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        assertEquals(0.0, distributionPercentages.get(4));
        assertEquals(0.0, distributionPercentages.get(5));
        assertEquals(0.0, distributionPercentages.get(6));
        assertEquals(0.0, distributionPercentages.get(7));
        assertEquals(0.0, distributionPercentages.get(8));
        assertEquals(0.0, distributionPercentages.get(9));
        assertEquals(0.0, distributionPercentages.get(10));
        assertEquals(0.0, distributionPercentages.get(11));
        assertEquals(0.0, distributionPercentages.get(12));
        assertEquals(0.0, distributionPercentages.get(13));
        assertEquals(0.0, distributionPercentages.get(14));
    }

    @Test 
    void testGetDistributionPercentagesMultipleExpensesOfSameCategory() {
        Expense e1 = new Expense(100.11, "a", "entertainment", "one-time");
        Expense e2 = new Expense(11.25, "b", "entertainment", "monthly");
        myList.addExpense(e1);
        myList.addExpense(e2);

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(15, distributionPercentages.size());
        assertEquals(0.0, distributionPercentages.get(0));
        assertEquals(100.0 / 100.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        assertEquals(0.0, distributionPercentages.get(4));
        assertEquals(0.0, distributionPercentages.get(5));
        assertEquals(0.0, distributionPercentages.get(6));
        assertEquals(0.0, distributionPercentages.get(7));
        assertEquals(0.0, distributionPercentages.get(8));
        assertEquals(0.0, distributionPercentages.get(9));
        assertEquals(0.0, distributionPercentages.get(10));
        assertEquals(0.0, distributionPercentages.get(11));
        assertEquals(0.0, distributionPercentages.get(12));
        assertEquals(0.0, distributionPercentages.get(13));
        assertEquals(0.0, distributionPercentages.get(14));
    }

    @Test
    void testGetDistributionPercentagesOneExpenseInMultipleCategories() {
        Expense e1 = new Expense(123.75, "a", "entertainment", "one-time");
        Expense e2 = new Expense(26.25, "b", "food", "monthly");
        Expense e3 = new Expense(100, "c", "other", "one-time");
        myList.addExpense(e1);
        myList.addExpense(e2);
        myList.addExpense(e3);

        double total = myList.getTotal();

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(15, distributionPercentages.size());
        assertEquals(26.25 / total * 100 / 100.0, distributionPercentages.get(0));
        assertEquals(123.75 / total * 100 / 100.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        assertEquals(0.0, distributionPercentages.get(4));
        assertEquals(0.0, distributionPercentages.get(5));
        assertEquals(0.0, distributionPercentages.get(6));
        assertEquals(0.0, distributionPercentages.get(7));
        assertEquals(0.0, distributionPercentages.get(8));
        assertEquals(0.0, distributionPercentages.get(9));
        assertEquals(0.0, distributionPercentages.get(10));
        assertEquals(0.0, distributionPercentages.get(11));
        assertEquals(0.0, distributionPercentages.get(12));
        assertEquals(0.0, distributionPercentages.get(13));
        assertEquals(100 / total * 100 / 100.0, distributionPercentages.get(14));
    }

    @Test
    void testGetDistributionPercentagesMixedNumExpensesInAllCategories() {
        Expense e1 = new Expense(123.75, "a", "entertainment", "one-time");
        Expense e2 = new Expense(103.55, "b", "entertainment", "monthly");
        Expense e3 = new Expense(26.25, "c", "food", "monthly");
        Expense e4 = new Expense(21.50, "d", "food", "one-time");
        Expense e5 = new Expense(100, "e", "other", "one-time");
        Expense e6 = new Expense(20.44, "f", "utilities", "one-time");
        Expense e7 = new Expense(20.49, "g", "medical", "monthly");
        Expense e8 = new Expense(100, "h", "other", "one-time");
        Expense e9 = new Expense(13.55, "i", "entertainment", "monthly");
        Expense e10 = new Expense(12.99, "j", "entertainment", "monthly");
        Expense e11 = new Expense(109.11, "k", "food", "one-time");
        Expense e12 = new Expense(2.44, "l", "utilities", "one-time");
        Expense e13 = new Expense(200, "m", "medical", "one-time");
        Expense e14 = new Expense(319, "n", "travel", "one-time");
        Expense e15 = new Expense(10, "o", "travel", "monthly");
        Expense e16 = new Expense(10.99, "p", "insurance", "monthly");
        Expense e17 = new Expense(14.99, "q", "insurance", "monthly");
        Expense e18 = new Expense(5.99, "r", "personal care", "monthly");
        Expense e19 = new Expense(500, "s", "savings", "monthly");
        Expense e20 = new Expense(245.51, "t", "gift", "one-time");
        Expense e21 = new Expense(200, "u", "donation", "one-time");
        Expense e22 = new Expense(30, "v", "transportation", "monthly");
        Expense e23 = new Expense(59.95, "w", "education", "one-time");
        Expense e24 = new Expense(12.17, "x", "membership", "monthly");
        Expense e25 = new Expense(119.97, "y", "housing", "monthly");

        myList.addExpense(e1);
        myList.addExpense(e2);
        myList.addExpense(e3);
        myList.addExpense(e4);
        myList.addExpense(e5);
        myList.addExpense(e6);
        myList.addExpense(e7);
        myList.addExpense(e8);
        myList.addExpense(e9);
        myList.addExpense(e10);
        myList.addExpense(e11);
        myList.addExpense(e12);
        myList.addExpense(e13);
        myList.addExpense(e14);
        myList.addExpense(e15);
        myList.addExpense(e16);
        myList.addExpense(e17);
        myList.addExpense(e18);
        myList.addExpense(e19);
        myList.addExpense(e20);
        myList.addExpense(e21);
        myList.addExpense(e22);
        myList.addExpense(e23);
        myList.addExpense(e24);
        myList.addExpense(e25);

        double total = myList.getTotal();
        double foodTotal = myList.getCategoryTotal("food");
        double entertainmentTotal = myList.getCategoryTotal("entertainment");
        double personalCareTotal = myList.getCategoryTotal("personal care");
        double giftTotal = myList.getCategoryTotal("gift");
        double donationTotal = myList.getCategoryTotal("donation");
        double transportationTotal = myList.getCategoryTotal("transportation");
        double insuranceTotal = myList.getCategoryTotal("insurance");
        double medicalTotal = myList.getCategoryTotal("medical");
        double travelTotal = myList.getCategoryTotal("travel");
        double utilitiesTotal = myList.getCategoryTotal("utilities");
        double housingTotal = myList.getCategoryTotal("housing");
        double educationTotal = myList.getCategoryTotal("education");
        double membershipTotal = myList.getCategoryTotal("membership");
        double savingsTotal = myList.getCategoryTotal("savings");
        double otherTotal = myList.getCategoryTotal("other");

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(15, distributionPercentages.size());
        assertEquals(foodTotal / total * 100 / 100.0, distributionPercentages.get(0));
        assertEquals(entertainmentTotal / total * 100 / 100.0, distributionPercentages.get(1));
        assertEquals(personalCareTotal / total * 100 / 100.0, distributionPercentages.get(2));
        assertEquals(giftTotal / total * 100 / 100.0, distributionPercentages.get(3));
        assertEquals(donationTotal / total * 100 / 100.0, distributionPercentages.get(4));
        assertEquals(transportationTotal / total * 100 / 100.0, distributionPercentages.get(5));
        assertEquals(insuranceTotal / total * 100 / 100.0, distributionPercentages.get(6));
        assertEquals(medicalTotal / total * 100 / 100.0, distributionPercentages.get(7));
        assertEquals(travelTotal / total * 100 / 100.0, distributionPercentages.get(8));
        assertEquals(utilitiesTotal / total * 100 / 100.0, distributionPercentages.get(9));
        assertEquals(housingTotal / total * 100 / 100.0, distributionPercentages.get(10));
        assertEquals(educationTotal / total * 100 / 100.0, distributionPercentages.get(11));
        assertEquals(membershipTotal / total * 100 / 100.0, distributionPercentages.get(12));
        assertEquals(savingsTotal / total * 100 / 100.0, distributionPercentages.get(13));
        assertEquals(otherTotal / total * 100 / 100.0, distributionPercentages.get(14));
    }

    @Test 
    void testGetSize() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");

        assertEquals(0, myList.getSize());
        myList.addExpense(expenseA);
        assertEquals(1, myList.getSize());
        myList.addExpense(expenseB);
        assertEquals(2, myList.getSize());
        myList.removeExpense(1);
        assertEquals(1, myList.getSize());
        myList.removeExpense(0);
        assertEquals(0, myList.getSize());
    }

    @Test
    void testGetItem() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);

        assertEquals(expenseB, myList.getItem(1));
        assertEquals(expenseA, myList.getItem(0));
    }

    @Test
    void testGetExpenseDescriptions() {
        Expense expenseA = new Expense(100, "Expense A", "transportation", "one-time");
        Expense expenseB = new Expense(300, "Second Expense item", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);

        HashMap<String, Integer> descriptions = myList.getExpenseDescriptions();
        assertEquals(2, descriptions.size());
        assertEquals(0, descriptions.get("Expense A"));
        assertEquals(1, descriptions.get("Second Expense item"));
    }

    @Test
    void testGetExpenseDescriptionsNoItems() {
        HashMap<String, Integer> descriptions = myList.getExpenseDescriptions();
        assertEquals(0, descriptions.size());
    }

    @Test
    void testGetCategoryTotalNoItemsFound() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);

        assertEquals(0, myList.getCategoryTotal("food"));
    }

    @Test
    void testGetCategoryTotalMultipleItems() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        Expense expenseC = new Expense(200, "c", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(0, myList.getCategoryTotal("food"));
        assertEquals(100, myList.getCategoryTotal("transportation"));
        assertEquals(500, myList.getCategoryTotal("savings"));
    }

    @Test
    void testGetMonthlyExpensesTotalEmptyList() {
        assertEquals(0, myList.getMonthlyExpensesTotal());
    }

    @Test
    void testGetMonthlyExpensesTotalNoMonthlyExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "one-time");
        Expense expenseC = new Expense(200, "c", "savings", "one-time");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(0, myList.getMonthlyExpensesTotal());
    }

    @Test
    void testGetMonthlyExpensesTotalWithMonthlyExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "monthly");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        Expense expenseC = new Expense(200, "c", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(600, myList.getMonthlyExpensesTotal());
    }

    @Test
    void testGetMonthlyExpensesTotalMixedExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "monthly");
        Expense expenseB = new Expense(300, "b", "savings", "one-time");
        Expense expenseC = new Expense(200, "c", "savings", "monthly");
        Expense expenseD = new Expense(1000, "d", "education", "one-time");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);
        myList.addExpense(expenseD);

        assertEquals(300, myList.getMonthlyExpensesTotal());
    }

    @Test
    void testGetOneTimeExpensesTotalEmptyList() {
        assertEquals(0, myList.getOneTimeExpensesTotal());
    }

    @Test
    void testGetOneTimeExpensesTotalNoOneTimeExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "monthly");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        Expense expenseC = new Expense(200, "c", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(0, myList.getOneTimeExpensesTotal());
    }

    @Test
    void testGetOneTimeExpensesTotalWithOneTimeExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "one-time");
        Expense expenseC = new Expense(200, "c", "savings", "one-time");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(600, myList.getOneTimeExpensesTotal());
    }

    @Test
    void testGetOneTimeExpensesTotalMixedExpenses() {
        Expense expenseA = new Expense(100, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300, "b", "savings", "monthly");
        Expense expenseC = new Expense(200, "c", "savings", "one-time");
        Expense expenseD = new Expense(1000, "d", "education", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);
        myList.addExpense(expenseD);

        assertEquals(300, myList.getOneTimeExpensesTotal());
    }

    @Test
    void testGetTotalEmptyList() {
        assertEquals(0, myList.getTotal());
    }

    @Test
    void testGetTotalListWithItems() {
        Expense expenseA = new Expense(100.45, "a", "transportation", "one-time");
        Expense expenseB = new Expense(300.55, "b", "savings", "monthly");
        Expense expenseC = new Expense(200.12, "c", "savings", "monthly");
        myList.addExpense(expenseA);
        myList.addExpense(expenseB);
        myList.addExpense(expenseC);

        assertEquals(601.12, myList.getTotal());
    }

    @Test
    void testGetCategories() {
        String[] categories = Expense.getCategories();
        String[] categoriesList = {"food", "entertainment", "personal care", "gift", "donation", "transportation", 
                "insurance", "medical", "travel", "utilities", "housing", "education", "membership",
                "savings", "other"};
        for (int i = 0; i < categories.length; i++) {
            assertEquals(categories[i], categoriesList[i]);
        }
    }
}
