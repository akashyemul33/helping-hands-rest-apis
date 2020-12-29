package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.AbstractResponseMessages;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractResponseMsgTest {

    static AbstractResponseMessages abstractResponseMessages;

    @BeforeAll
    static void setup() {
        abstractResponseMessages = new ResponseMsgInEnglish();
    }

    @Test
    void givenNullMapWhenGetResponseMsgFromAbstractRespMsgClassThenThrowException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                new ResponseMsgInEnglish().getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE,null);
            }
        });
    }
    
    @Test
    void givenEmptyKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(null,new ResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap("",new ResponseMsgInEnglish().getResponseMsgsMap());
            }
        });
    }

    @Test
    void givenNonExistingKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(AppConstants.PLACE_SUB_CATEGORY_ID,new ResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

        assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(AppConstants.PLACE_SUB_CATEGORY_NAME,new ResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

    }

    @Test
    void givenExistingKeyWhenGetResponseMsgFromEnglishThenAssociatedValue() {
        String userAlreadyExistsWithMobile = "User already exists with given mobile number !";
        String userAlreadyExistsWithEmail = "User already exists with given email !";
        assertEquals(userAlreadyExistsWithMobile, abstractResponseMessages.getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE,new ResponseMsgInEnglish().getResponseMsgsMap()));
        assertEquals(userAlreadyExistsWithEmail, abstractResponseMessages.getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL,new ResponseMsgInEnglish().getResponseMsgsMap()));
    }
}