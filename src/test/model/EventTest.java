package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// **Referenced from the AlarmSystem demo application provided by CPSC 210 course instructors
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git**
public class EventTest {
    private Event testEvent;
    private Date testDate;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        testEvent = new Event("Income added to budget"); // (1)
        testDate = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Income added to budget", testEvent.getDescription());
        assertEquals("" + testDate, "" + testEvent.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(testDate.toString() + "\n" + "Income added to budget", testEvent.toString());
    }
}
