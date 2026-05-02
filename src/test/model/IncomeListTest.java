package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
class IncomeListTest {
    private IncomeList myList;

    @BeforeEach
    void runBefore() {
        myList = new IncomeList();
    }

    @Test
    void testConstructor() {
        assertEquals(0, myList.getSize());
    }

    @Test
    void testAddIncome() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");

        assertEquals(0, myList.getSize());
        myList.addIncome(incomeA);
        assertEquals(100, myList.getItem(0).getValue());
        assertEquals(1, myList.getSize());
        myList.addIncome(incomeB);
        assertEquals(300, myList.getItem(1).getValue());
        assertEquals(2, myList.getSize());
    }

    @Test
    void testRemoveIncome() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");
        Income incomeC = new Income(500, "c", "other", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(100, myList.getItem(0).getValue());
        assertEquals(3, myList.getSize());
        myList.removeIncome(0);
        assertEquals(300, myList.getItem(0).getValue());
        assertEquals(2, myList.getSize());
        myList.removeIncome(1);
        assertEquals(300, myList.getItem(0).getValue());
        assertEquals(1, myList.getSize());
        myList.removeIncome(0);
        assertEquals(0, myList.getSize());
    }

    @Test
    void testModifyIncomeValue() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        
        assertEquals(100, myList.getItem(0).getValue());
        myList.modifyIncomeValue(0, 10);
        assertEquals(10, myList.getItem(0).getValue());
        myList.modifyIncomeValue(0, 1000);
        assertEquals(1000, myList.getItem(0).getValue());
        assertEquals(300, myList.getItem(1).getValue());
        myList.modifyIncomeValue(1, 30);
        assertEquals(30, myList.getItem(1).getValue());
    }

    @Test
    void testModifyIncomeDescription() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        
        assertEquals("a", myList.getItem(0).getDescription());
        myList.modifyIncomeDescription(0, "a");
        assertEquals("a", myList.getItem(0).getDescription());
        myList.modifyIncomeDescription(0, "b");
        assertEquals("b", myList.getItem(0).getDescription());
        assertEquals("b", myList.getItem(1).getDescription());
        myList.modifyIncomeDescription(1, "c");
        assertEquals("c", myList.getItem(1).getDescription());
    }

    @Test
    void testModifyIncomeCategory() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        
        assertEquals("work", myList.getItem(0).getCategory());
        myList.modifyIncomeCategory(0, "government benefit");
        assertEquals("government benefit", myList.getItem(0).getCategory());
        myList.modifyIncomeCategory(0, "other");
        assertEquals("other", myList.getItem(0).getCategory());
        assertEquals("government benefit", myList.getItem(1).getCategory());
        myList.modifyIncomeCategory(1, "work");
        assertEquals("work", myList.getItem(1).getCategory());
    }

    @Test
    void testModifyIncomeFrequency() {
        Income incomeA = new Income(100, "a", "work", "one-time");
        Income incomeB = new Income(300, "b", "government benefit", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        
        assertEquals("one-time", myList.getItem(0).getFrequency());
        myList.modifyIncomeFrequency(0, "monthly");
        assertEquals("monthly", myList.getItem(0).getFrequency());
        myList.modifyIncomeFrequency(0, "one-time");
        assertEquals("one-time", myList.getItem(0).getFrequency());
        assertEquals("monthly", myList.getItem(1).getFrequency());
        myList.modifyIncomeFrequency(1, "one-time");
        assertEquals("one-time", myList.getItem(1).getFrequency());
    }

    @Test
    void testCalculateProvincialTaxNonTaxableIncome() {
        Income incomeA = new Income(100, "a", "gift", "one-time");
        Income incomeB = new Income(300, "b", "other", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);

        assertEquals(0, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxMixedIncome() {
        Income incomeA = new Income(1000, "a", "gift", "one-time");
        Income incomeB = new Income(300000, "b", "other", "one-time");
        Income incomeC = new Income(40000, "c", "work", "monthly");
        Income incomeD = new Income(20000, "d", "government benefit", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);
        myList.addIncome(incomeD);

        assertTrue(0.205*240171 + 0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myList.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome7thBracketBoundary() {
        Income myIncome = new Income(259829.01, "a", "government benefit", "one-time"); 
        myList.addIncome(myIncome);
        assertTrue(0.205*0.01 + 0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myList.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome6thBracketUpperBoundary() {
        Income myIncome = new Income(259829, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertTrue(0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myList.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome6thBracketLowerBoundary() {
        Income myIncome = new Income(186306.01, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertTrue(0.168*0.01 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myList.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome5thBracketUpperBoundary() {
        Income myIncome = new Income(186306, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertTrue(0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myList.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome5thBracketLowerBoundary() {
        Income myIncome = new Income(137407.01, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.147*0.01 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome4thBracketUpperBoundary() {
        Income myIncome = new Income(137407, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome4thBracketLowerBoundary() {
        Income myIncome = new Income(113158.01, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.1229*0.01 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome3rdBracketUpperBoundary() {
        Income myIncome = new Income(113158, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.105*14598 + 0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome3rdBracketLowerBoundary() {
        Income myIncome = new Income(98560.01, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.105*0.01 + 0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome2ndBracketUpperBoundary() {
        Income myIncome = new Income(98560, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.077*49281 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome2ndBracketLowerBoundary() {
        Income myIncome = new Income(49279.01, "a", "work", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.077*0.01 + 0.0506*49279, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome1stBracketBoundary() {
        Income myIncome = new Income(49279, "a", "work", "one-time");
        Income anotherIncome = new Income(100000, "b", "gift", "one-time");
        myList.addIncome(myIncome);
        myList.addIncome(anotherIncome);
        assertEquals(0.0506*49279, myList.calculateProvincialTax());
    }
    
    @Test
    void testCalculateFederalTaxNonTaxableIncome() {
        Income incomeA = new Income(100, "a", "gift", "one-time");
        Income incomeB = new Income(300, "b", "other", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);

        assertEquals(0, myList.calculateProvincialTax());
    }

    @Test
    void testCalculateFederalTaxMixedIncome() {
        Income incomeA = new Income(1000, "a", "gift", "one-time");
        Income incomeB = new Income(300000, "b", "other", "one-time");
        Income incomeC = new Income(20000, "c", "work", "monthly");
        Income incomeD = new Income(60000, "d", "government benefit", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);
        myList.addIncome(incomeD);

        assertTrue(0.33*46586 + 0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375 - myList.calculateFederalTax() <= 0.01);
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome5thBracketBoundary() {
        Income myIncome = new Income(253414.01, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.33*0.01 + 0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome4thBracketUpperBoundary() {
        Income myIncome = new Income(253414, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome4thBracketLowerBoundary() {
        Income myIncome = new Income(177882.01, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.29*0.01 + 0.26*63132 + 0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome3rdBracketUpperBoundary() {
        Income myIncome = new Income(177882, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.26*63132 + 0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome3rdBracketLowerBoundary() {
        Income myIncome = new Income(114750.01, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.26*0.01 + 0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome2ndBracketUpperBoundary() {
        Income myIncome = new Income(114750, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.205*57375 + 0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome2ndBracketLowerBoundary() {
        Income myIncome = new Income(57375.01, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertTrue((0.205*0.0100 + 0.145*57375) - myList.calculateFederalTax() <= 0.01);
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome1stBracketBoundary() {
        Income myIncome = new Income(57375, "a", "government benefit", "one-time");
        myList.addIncome(myIncome);
        assertEquals(0.145*57375, myList.calculateFederalTax());
    }

    @Test
    void testGetDistributionEmpty() {
        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(8, distribution.size());
        assertEquals("Work", distribution.get(0));
        assertEquals("0.0", distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals("0.0", distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
    }

    @Test
    void testGetDistributionSingleExpense() {
        Income i1 = new Income(100.11, "a", "government benefit", "one-time");
        myList.addIncome(i1);

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(8, distribution.size());
        assertEquals("Work", distribution.get(0));
        assertEquals("0.0", distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals("100.0", distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
        

        myList.modifyIncomeCategory(0, "work");
        distribution = myList.getDistribution();
        assertEquals("Work", distribution.get(0));
        assertEquals("100.0", distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals("0.0", distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
    }

    @Test 
    void testGetDistributionMultipleExpensesOfSameCategory() {
        Income i1 = new Income(100.11, "a", "work", "one-time");
        Income i2 = new Income(11.25, "b", "work", "monthly");
        myList.addIncome(i1);
        myList.addIncome(i2);

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(8, distribution.size());
        assertEquals("Work", distribution.get(0));
        assertEquals("100.0", distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals("0.0", distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals("0.0", distribution.get(7));
    }

    @Test
    void testGetDistributionOneIncomeInMultipleCategories() {
        Income i1 = new Income(123.75, "a", "government benefit", "one-time");
        Income i2 = new Income(26.25, "b", "work", "monthly");
        Income i3 = new Income(100, "c", "other", "one-time");
        myList.addIncome(i1);
        myList.addIncome(i2);
        myList.addIncome(i3);

        double total = myList.getTotal();

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(8, distribution.size());
        assertEquals("Work", distribution.get(0));
        assertEquals(Double.toString(26.25 / total * 100), distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals(Double.toString(123.75 / total * 100), distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals("0.0", distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals(Double.toString(100 / total * 100), distribution.get(7));
    }

    @Test
    void testGetDistributionMixedNumExpensesInAllCategories() {
        Income i1 = new Income(123.75, "a", "government benefit", "one-time");
        Income i2 = new Income(103.55, "b", "gift", "monthly");
        Income i3 = new Income(26.25, "c", "income", "monthly");
        Income i4 = new Income(21.50, "d", "work", "one-time");
        Income i5 = new Income(100, "e", "other", "one-time");
        Income i6 = new Income(20.44, "f", "work", "one-time");
        Income i7 = new Income(20.49, "g", "gift", "monthly");
        Income i8 = new Income(100, "h", "government benefit", "one-time");

        myList.addIncome(i1);
        myList.addIncome(i2);
        myList.addIncome(i3);
        myList.addIncome(i4);
        myList.addIncome(i5);
        myList.addIncome(i6);
        myList.addIncome(i7);
        myList.addIncome(i8);

        double total = myList.getTotal();
        double workTotal = myList.getCategoryTotal("work");
        double governmentBenefitTotal = myList.getCategoryTotal("government benefit");
        double giftTotal = myList.getCategoryTotal("gift");
        double otherTotal = myList.getCategoryTotal("other");

        ArrayList<String> distribution = myList.getDistribution();
        assertEquals(8, distribution.size());
        assertEquals("Work", distribution.get(0));
        assertEquals(Double.toString(workTotal / total * 100), distribution.get(1));
        assertEquals("Government Benefit", distribution.get(2));
        assertEquals(Double.toString(governmentBenefitTotal / total * 100), distribution.get(3));
        assertEquals("Gift", distribution.get(4));
        assertEquals(Double.toString(giftTotal / total * 100), distribution.get(5));
        assertEquals("Other", distribution.get(6));
        assertEquals(Double.toString(otherTotal / total * 100), distribution.get(7));
    }   

    @Test
    void testGetDistributionPercentagesEmpty() {
        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(4, distributionPercentages.size());
        assertEquals(0.0, distributionPercentages.get(0));
        assertEquals(0.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
    }

    @Test
    void testGetDistributionPercentagesSingleExpense() {
        Income i1 = new Income(100.11, "a", "government benefit", "one-time");
        myList.addIncome(i1);

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(4, distributionPercentages.size());
        assertEquals(0.0, distributionPercentages.get(0));
        assertEquals(100.0 / 100.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
        

        myList.modifyIncomeCategory(0, "work");
        distributionPercentages = myList.getDistributionPercentages();
        assertEquals(100.0 / 100.0, distributionPercentages.get(0));
        assertEquals(0.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
    }

    @Test 
    void testGetDistributionPercentagesMultipleExpensesOfSameCategory() {
        Income i1 = new Income(100.11, "a", "work", "one-time");
        Income i2 = new Income(11.25, "b", "work", "monthly");
        myList.addIncome(i1);
        myList.addIncome(i2);

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(4, distributionPercentages.size());
        assertEquals(100.0 / 100.0, distributionPercentages.get(0));
        assertEquals(0.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(0.0, distributionPercentages.get(3));
    }

    @Test
    void testGetDistributionPercentagesOneIncomeInMultipleCategories() {
        Income i1 = new Income(123.75, "a", "government benefit", "one-time");
        Income i2 = new Income(26.25, "b", "work", "monthly");
        Income i3 = new Income(100, "c", "other", "one-time");
        myList.addIncome(i1);
        myList.addIncome(i2);
        myList.addIncome(i3);

        double total = myList.getTotal();

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(4, distributionPercentages.size());
        assertEquals(26.25 / total * 100 / 100.0, distributionPercentages.get(0));
        assertEquals(123.75 / total * 100 / 100.0, distributionPercentages.get(1));
        assertEquals(0.0, distributionPercentages.get(2));
        assertEquals(100 / total * 100 / 100.0, distributionPercentages.get(3));
    }

    @Test
    void testGetDistributionPercentagesMixedNumExpensesInAllCategories() {
        Income i1 = new Income(123.75, "a", "government benefit", "one-time");
        Income i2 = new Income(103.55, "b", "gift", "monthly");
        Income i3 = new Income(26.25, "c", "income", "monthly");
        Income i4 = new Income(21.50, "d", "work", "one-time");
        Income i5 = new Income(100, "e", "other", "one-time");
        Income i6 = new Income(20.44, "f", "work", "one-time");
        Income i7 = new Income(20.49, "g", "gift", "monthly");
        Income i8 = new Income(100, "h", "government benefit", "one-time");

        myList.addIncome(i1);
        myList.addIncome(i2);
        myList.addIncome(i3);
        myList.addIncome(i4);
        myList.addIncome(i5);
        myList.addIncome(i6);
        myList.addIncome(i7);
        myList.addIncome(i8);

        double total = myList.getTotal();
        double workTotal = myList.getCategoryTotal("work");
        double governmentBenefitTotal = myList.getCategoryTotal("government benefit");
        double giftTotal = myList.getCategoryTotal("gift");
        double otherTotal = myList.getCategoryTotal("other");

        ArrayList<Double> distributionPercentages = myList.getDistributionPercentages();
        assertEquals(4, distributionPercentages.size());
        assertEquals(workTotal / total * 100 / 100.0, distributionPercentages.get(0));
        assertEquals(governmentBenefitTotal / total * 100 / 100.0, distributionPercentages.get(1));
        assertEquals(giftTotal / total * 100 / 100.0, distributionPercentages.get(2));
        assertEquals(otherTotal / total * 100 / 100.0, distributionPercentages.get(3));
    }   
    
    @Test 
    void testGetIncomeDescriptions() {
        Income incomeA = new Income(100, "Item A", "government benefit", "one-time");
        Income incomeB = new Income(300, "Income Item b", "work", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);

        HashMap<String, Integer> descriptions = myList.getIncomeDescriptions();
        assertEquals(2, descriptions.size());
        assertEquals(0, descriptions.get("Item A"));
        assertEquals(1, descriptions.get("Income Item b"));
    }

    @Test
    void testGetIncomeDescriptionsNoItems() {
        HashMap<String, Integer> descriptions = myList.getIncomeDescriptions();
        assertEquals(0, descriptions.size());
    }

    @Test
    void testGetCategoryTotalNoItemsFound() {
        Income incomeA = new Income(100, "a", "government benefit", "one-time");
        Income incomeB = new Income(300, "b", "work", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);

        assertEquals(0, myList.getCategoryTotal("gift"));
    }

    @Test
    void testGetCategoryTotalMultipleItems() {
        Income incomeA = new Income(100, "a", "government benefit", "one-time");
        Income incomeB = new Income(300, "b", "work", "monthly");
        Income incomeC = new Income(200, "c", "work", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(0, myList.getCategoryTotal("gift"));
        assertEquals(100, myList.getCategoryTotal("government benefit"));
        assertEquals(500, myList.getCategoryTotal("work"));
    }

    @Test
    void testGetMonthlyIncomeTotalEmptyList() {
        assertEquals(0, myList.getMonthlyIncomeTotal());
    }

    @Test
    void testGetMonthlyIncomeTotalNoMonthlyIncome() {
        Income incomeA = new Income(100, "a", "gift", "one-time");
        Income incomeB = new Income(300, "b", "work", "one-time");
        Income incomeC = new Income(200, "c", "work", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(0, myList.getMonthlyIncomeTotal());
    }

    @Test
    void testGetMonthlyIncomeTotalWithMonthlyIncome() {
        Income incomeA = new Income(100, "a", "gift", "monthly");
        Income incomeB = new Income(300, "b", "work", "monthly");
        Income incomeC = new Income(200, "c", "work", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(600, myList.getMonthlyIncomeTotal());
    }

    @Test
    void testGetMonthlyIncomeTotalMixedIncome() {
        Income incomeA = new Income(100, "a", "gift", "monthly");
        Income incomeB = new Income(300, "b", "work", "one-time");
        Income incomeC = new Income(200, "c", "work", "monthly");
        Income incomeD = new Income(1000, "d", "government benefit", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);
        myList.addIncome(incomeD);

        assertEquals(300, myList.getMonthlyIncomeTotal());
    }

    @Test
    void testGetOneTimeIncomeTotalEmptyList() {
        assertEquals(0, myList.getOneTimeIncomeTotal());
    }

    @Test
    void testGetOneTimeIncomeTotalNoOneTimeIncome() {
        Income incomeA = new Income(100, "a", "government benefit", "monthly");
        Income incomeB = new Income(300, "b", "gift", "monthly");
        Income incomeC = new Income(200, "c", "gift", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(0, myList.getOneTimeIncomeTotal());
    }

    @Test
    void testGetOneTimeIncomeTotalWithOneTimeIncome() {
        Income incomeA = new Income(100, "a", "government benefit", "one-time");
        Income incomeB = new Income(300, "b", "gift", "one-time");
        Income incomeC = new Income(200, "c", "gift", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);

        assertEquals(600, myList.getOneTimeIncomeTotal());
    }

    @Test
    void testGetOneTimeIncomeTotalMixedIncome() {
        Income incomeA = new Income(100, "a", "government benefit", "one-time");
        Income incomeB = new Income(300, "b", "gift", "monthly");
        Income incomeC = new Income(200, "c", "gift", "one-time");
        Income incomeD = new Income(1000, "d", "other", "monthly");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);
        myList.addIncome(incomeD);

        assertEquals(300, myList.getOneTimeIncomeTotal());
    }

    @Test
    void testGetTotalEmptyIncomeList() {
        assertEquals(0, myList.getTotal());
    }

    @Test
    void testGetTotalNonEmptyIncomeList() {
        Income incomeA = new Income(1000, "a", "gift", "one-time");
        Income incomeB = new Income(30.57, "b", "other", "one-time");
        Income incomeC = new Income(0.10, "c", "work", "monthly");
        Income incomeD = new Income(5.11, "d", "government benefit", "one-time");
        myList.addIncome(incomeA);
        myList.addIncome(incomeB);
        myList.addIncome(incomeC);
        myList.addIncome(incomeD);

        assertEquals(1000 + 30.57 + 0.10 + 5.11, myList.getTotal());
    }

    @Test
    void testGetCategories() {
        String[] categories = Income.getCategories();
        String[] categoriesList = {"work", "government benefit", "gift", "other"};
        for (int i = 0; i < categories.length; i++) {
            assertEquals(categories[i], categoriesList[i]);
        }
    }
}
