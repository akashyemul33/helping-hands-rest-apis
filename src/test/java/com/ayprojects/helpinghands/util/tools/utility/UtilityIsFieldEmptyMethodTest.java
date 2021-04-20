package com.ayprojects.helpinghands.util.tools.utility;

import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mapstruct.MappingConstants.NULL;

public class UtilityIsFieldEmptyMethodTest {

    @Test
    void givenNullWhenUtilityIsFieldEmptyThenTrue(){
        Assertions.assertTrue(Utility.isFieldEmpty(null));
    }

    @Test
    void givenEmptyStringWhenUtilityIsFieldEmptyThenTrue(){
        assertTrue(Utility.isFieldEmpty(""));
    }

    @Test
    void givenSomeStringWhenUtilityIsFieldEmptyThenFalse(){
        assertFalse(Utility.isFieldEmpty("abc"));
        assertFalse(Utility.isFieldEmpty("%"));
        assertFalse(Utility.isFieldEmpty("1"));
        assertFalse(Utility.isFieldEmpty("null"));
        assertFalse(Utility.isFieldEmpty("NULL"));
        assertFalse(Utility.isFieldEmpty(NULL));

    }
}
