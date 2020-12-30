package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetExtensionAfterValidationTest {
    private static AmazonClient amazonClient;

    @BeforeAll
    static void setup() {
        amazonClient = new AmazonClient();
    }

    @Test
    void givenEmptyExtensionThenPng() {
        assertEquals(AppConstants.FILETYPE_PNG, amazonClient.getExtensionAfterValidation(""));
        assertEquals(AppConstants.FILETYPE_PNG, amazonClient.getExtensionAfterValidation(null));
    }

    @Test
    void givenInvalidExtensionThenException() {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getExtensionAfterValidation("abc");
            }
        });
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getExtensionAfterValidation("pngg");
            }
        });
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getExtensionAfterValidation("ablk");
            }
        });
    }

    @Test
    void givenValidExtensionThenSpecifiedValue() {
        assertEquals("png", amazonClient.getExtensionAfterValidation("png"));
        assertEquals("png", amazonClient.getExtensionAfterValidation("PNG"));
        assertEquals("png", amazonClient.getExtensionAfterValidation("pNG"));

        assertEquals("jpg", amazonClient.getExtensionAfterValidation("jpg"));
        assertEquals("jpeg", amazonClient.getExtensionAfterValidation("jpeg"));
        assertEquals("svg", amazonClient.getExtensionAfterValidation("svg"));
    }
}
