package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResponseMsgInEnglishTest {
    static ResponseMsgInEnglish responseMsgInEnglish;

    @BeforeAll
    static void setup() {
        responseMsgInEnglish = new NestedResponseMsgInEnglish();
    }

    @Test
    void givenEmptyKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInEnglish.getResponseMsg(null);
            }
        });

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInEnglish.getResponseMsg("");
            }
        });
    }

    @Test
    void givenNonExistingKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInEnglish.getResponseMsg(AppConstants.PLACE_SUB_CATEGORY_NAME);
            }
        });

        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgInEnglish.getResponseMsg(AppConstants.PLACE_SUB_CATEGORY_ID);
            }
        });

    }

    @Test
    void givenExistingKeyWhenGetResponseMsgFromEnglishThenAssociatedValue() {
        String userAlreadyExistsWithMobile = "User already exists with given mobile number !";
        Assertions.assertEquals(userAlreadyExistsWithMobile, responseMsgInEnglish.getResponseMsg(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE));
    }
}


