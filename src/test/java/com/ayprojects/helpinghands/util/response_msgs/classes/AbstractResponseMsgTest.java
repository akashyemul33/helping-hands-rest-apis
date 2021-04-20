package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.AbstractResponseMessages;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractResponseMsgTest extends ResponseMsgInEnglish {

    static AbstractResponseMessages abstractResponseMessages;

    @BeforeAll
    static void setup() {
        abstractResponseMessages = new AbstractResponseMsgTest();
    }

    @Test
    void givenNullMapWhenGetResponseMsgFromAbstractRespMsgClassThenThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE,null);
            }
        });
    }
    
    @Test
    void givenEmptyKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(null,new NestedResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap("",new NestedResponseMsgInEnglish().getResponseMsgsMap());
            }
        });
    }

    @Test
    void givenNonExistingKeyWhenGetResponseMsgFromEnglishThenThrowException() {
        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(AppConstants.PLACE_SUB_CATEGORY_ID,new NestedResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

        Assertions.assertThrows(NoSuchElementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                abstractResponseMessages.getResponseMsgFromMap(AppConstants.PLACE_SUB_CATEGORY_NAME,new NestedResponseMsgInEnglish().getResponseMsgsMap());
            }
        });

    }

    @Test
    void givenExistingKeyWhenGetResponseMsgFromEnglishThenAssociatedValue() {
        String userAlreadyExistsWithMobile = "User already exists with given mobile number !";
        String userAlreadyExistsWithEmail = "User already exists with given email !";
        Assertions.assertEquals(userAlreadyExistsWithMobile, abstractResponseMessages.getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE,new NestedResponseMsgInEnglish().getResponseMsgsMap()));
        Assertions.assertEquals(userAlreadyExistsWithEmail, abstractResponseMessages.getResponseMsgFromMap(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL,new NestedResponseMsgInEnglish().getResponseMsgsMap()));
    }
}