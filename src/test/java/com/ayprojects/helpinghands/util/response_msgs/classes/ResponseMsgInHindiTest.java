package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInHindi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResponseMsgInHindiTest {
    static ResponseMsgInHindi responseMsgInMarathi;

    @BeforeAll
    static void setup() {
        responseMsgInMarathi = new ResponseMsgInHindi();
    }

    @Test
    void givenEmptyKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInMarathi.getResponseMsg(null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInMarathi.getResponseMsg("");
            }
        });
    }

    @Test
    void givenNonExistingKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInMarathi.getResponseMsg(AppConstants.PLACE_SUB_CATEGORY_NAME);
            }
        });

        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInMarathi.getResponseMsg(AppConstants.PLACE_SUB_CATEGORY_ID);
            }
        });

    }

    @Test
    void givenExistingKeyWhenGetResponseMsgFromEnglishThenAssociatedValue() {
        String userAlreadyExistsWithMobile = "उपयोगकर्ता पहले से ही दिए गए मोबाइल नंबर के साथ मौजूद है !";
        String userAlreadyExistsWithEmail = "उपयोगकर्ता पहले से ही दिए गए ईमेल के साथ मौजूद है !";
        assertEquals(userAlreadyExistsWithMobile, responseMsgInMarathi.getResponseMsg(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE));
        assertEquals(userAlreadyExistsWithEmail, responseMsgInMarathi.getResponseMsg(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL));
    }
}


