package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import org.junit.jupiter.api.BeforeEach;

@ExcludeFromJacocoGeneratedReport
class MoneyItemTest {
    private MoneyItem sampleMI;

    @BeforeEach
    void runBefore() {
        sampleMI = new MoneyItem(10.99, "A box of spinach", "Food", "one-time");
    }

    @Test
    void testConstructor() {
        assertEquals(10.99, sampleMI.getValue());
        assertEquals("A box of spinach", sampleMI.getDescription());
        assertEquals("Food", sampleMI.getCategory());
        assertEquals("one-time", sampleMI.getFrequency());
    }

    @Test
    void testGetFrequencies() {
        String[] frequencies = MoneyItem.getFrequencies();
        String[] frequenciesList = {"monthly", "one-time"};
        for (int i = 0; i < frequencies.length; i++) {
            assertEquals(frequencies[i], frequenciesList[i]);
        }
    }
}
