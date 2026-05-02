package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
class IncomeTest {
    private Income myIncome;

    @Test
    void testConstructor() {
        myIncome = new Income(1000, "Amazon", "work", "monthly");
        assertEquals(1000, myIncome.getValue());
        assertEquals("Amazon", myIncome.getDescription());
        assertEquals("work", myIncome.getCategory());
        assertEquals("monthly", myIncome.getFrequency());
    }

    @Test
    void testCalculateProvincialTaxNonTaxableIncome() {
        myIncome = new Income(1000, "Sold my TV", "other", "one-time");
        assertEquals(0, myIncome.calculateProvincialTax());
        myIncome = new Income(25, "allowance", "gift", "monthly");
        assertEquals(0, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome() {
        myIncome = new Income(500000, "a", "work", "one-time");
        assertTrue(0.205*240171 + 0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome7thBracketBoundary() {
        myIncome = new Income(259829.01, "a", "government", "one-time"); 
        assertTrue(0.205*0.01 + 0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome6thBracketUpperBoundary() {
        myIncome = new Income(259829, "a", "work", "one-time");
        assertTrue(0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome6thBracketLowerBoundary() {
        myIncome = new Income(186306.01, "a", "work", "one-time");
        assertTrue(0.168*0.01 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome5thBracketUpperBoundary() {
        myIncome = new Income(186306, "a", "work", "one-time");
        assertTrue(0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test
    void testCalculateProvincialTaxeTaxableOneTimeIncome5thBracketLowerBoundary() {
        myIncome = new Income(137407.01, "a", "work", "one-time");
        assertEquals(0.147*0.01 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome4thBracketUpperBoundary() {
        myIncome = new Income(137407, "a", "work", "one-time");
        assertEquals(0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome4thBracketLowerBoundary() {
        myIncome = new Income(113158.01, "a", "work", "one-time");
        assertEquals(0.1229*0.01 + 0.105*14598 + 0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome3rdBracketUpperBoundary() {
        myIncome = new Income(113158, "a", "work", "one-time");
        assertEquals(0.105*14598 + 0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome3rdBracketLowerBoundary() {
        myIncome = new Income(98560.01, "a", "work", "one-time");
        assertEquals(0.105*0.01 + 0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome2ndBracketUpperBoundary() {
        myIncome = new Income(98560, "a", "work", "one-time");
        assertEquals(0.077*49281 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome2ndBracketLowerBoundary() {
        myIncome = new Income(49279.01, "a", "work", "one-time");
        assertEquals(0.077*0.01 + 0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableOneTimeIncome1stBracketBoundary() {
        myIncome = new Income(49279, "a", "work", "one-time");
        assertEquals(0.0506*49279, myIncome.calculateProvincialTax());
    }

    @Test
    void testCalculateProvincialTaxTaxableMonthlyIncome() {
        myIncome = new Income(42000, "a", "work", "monthly");
        assertTrue(0.205*244171 + 0.168*73523 + 0.147*48899 + 0.1229*24249 + 0.105*14598 + 0.077*49281 + 0.0506*49279 - myIncome.calculateProvincialTax() <= 0.01);
    }

    @Test 
    void testCalculateFederalTaxNonTaxableIncome() {
        myIncome = new Income(2000, "Sold my TV", "other", "one-time");
        assertEquals(0, myIncome.calculateFederalTax());
        myIncome = new Income(40, "my allowance! :)", "gift", "monthly");
        assertEquals(0, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome() {
        myIncome = new Income(300000, "a", "government", "one-time");
        assertTrue(0.33*46586 + 0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375 - myIncome.calculateFederalTax() <= 0.01);
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome5thBracketBoundary() {
        myIncome = new Income(253414.01, "a", "government", "one-time");
        assertEquals(0.33*0.01 + 0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome4thBracketUpperBoundary() {
        myIncome = new Income(253414, "a", "government", "one-time");
        assertEquals(0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome4thBracketLowerBoundary() {
        myIncome = new Income(177882.01, "a", "government", "one-time");
        assertEquals(0.29*0.01 + 0.26*63132 + 0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome3rdBracketUpperBoundary() {
        myIncome = new Income(177882, "a", "government", "one-time");
        assertEquals(0.26*63132 + 0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome3rdBracketLowerBoundary() {
        myIncome = new Income(114750.01, "a", "government", "one-time");
        assertEquals(0.26*0.01 + 0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome2ndBracketUpperBoundary() {
        myIncome = new Income(114750, "a", "government", "one-time");
        assertEquals(0.205*57375 + 0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome2ndBracketLowerBoundary() {
        myIncome = new Income(57375.01, "a", "government", "one-time");
        assertTrue((0.205*0.0100 + 0.145*57375) - myIncome.calculateFederalTax() <= 0.01);
    }

    @Test
    void testCalculateFederalTaxTaxableOneTimeIncome1stBracketBoundary() {
        myIncome = new Income(57375, "a", "government", "one-time");
        assertEquals(0.145*57375, myIncome.calculateFederalTax());
    }

    @Test
    void testCalculateFederalTaxTaxableMonthlyIncome() {
        myIncome = new Income(25000, "a", "work", "monthly");
        assertTrue(0.33*46586 + 0.29*75532 + 0.26*63132 + 0.205*57375 + 0.145*57375 - myIncome.calculateFederalTax() <= 0.01);
    }
}
