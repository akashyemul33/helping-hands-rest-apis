package com.ayprojects.helpinghands.util.tools.calendar_operations;

import com.ayprojects.helpinghands.util.tools.CalendarOperations;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VerifyTimeFollowsCorrectFormatMethodTest {

    static CalendarOperations calendarOperations;

    @BeforeAll
    static void setup(){
        calendarOperations = new CalendarOperations();
    }

    @Test
    void givenEmptyTimeThenFalse(){
        assertFalse(calendarOperations.verifyTimeFollowsCorrectFormat(""));
        assertFalse(calendarOperations.verifyTimeFollowsCorrectFormat(null));
    }

    @Test
    void givenInvalidTimeThenFalse()
    {
        assertFalse(calendarOperations.verifyTimeFollowsCorrectFormat("12/21/2020 06:29:40"));
        assertFalse(calendarOperations.verifyTimeFollowsCorrectFormat("202231-01-0712 11:3127:4511"));

    }

    @Test
    void givenValidTimeThenTrue()
    {
        assertTrue(calendarOperations.verifyTimeFollowsCorrectFormat("2020-11-21 06:29:40"));

    }
}
