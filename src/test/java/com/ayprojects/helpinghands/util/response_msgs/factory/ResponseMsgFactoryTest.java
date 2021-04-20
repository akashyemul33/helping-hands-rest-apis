package com.ayprojects.helpinghands.util.response_msgs.factory;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInHindi;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInMarathi;
import com.ayprojects.helpinghands.util.response_msgs.classes.NestedResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.classes.NestedResponseMsgInHindi;
import com.ayprojects.helpinghands.util.response_msgs.classes.NestedResponseMsgInMarathi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResponseMsgFactoryTest {

    static ResponseMsgFactory responseMsgFactory;

    @BeforeAll
    static void setup() {

        responseMsgFactory = new ResponseMsgFactory();
    }

    @Test
    void givenNullKeyThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgFactory.getResponseMsg(null, "");
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                responseMsgFactory.getResponseMsg(null, null);
            }
        });
    }

    @Test
    void givenEmptyLangButValidKeyThenValueFromEnglish() {
        ResponseMsgInEnglish responseMsgInEnglish = new NestedResponseMsgInEnglish();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobile = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmail = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobile, responseMsgFactory.getResponseMsg(null, keyMobile));
        assertEquals(userAlreadyExistsWithEmail, responseMsgFactory.getResponseMsg("", keyEmail));
    }

    @Test
    void givenInvalidLangWithValidKeyThenValueFromEnglish() {
        ResponseMsgInEnglish responseMsgInEnglish = new NestedResponseMsgInEnglish();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobile = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmail = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobile, responseMsgFactory.getResponseMsg("ka", keyMobile));
        assertEquals(userAlreadyExistsWithEmail, responseMsgFactory.getResponseMsg("adlkfj", keyEmail));
    }

    @Test
    void givenMarthiLangThenValueInMarathi() {
        ResponseMsgInMarathi responseMsgInMarathi = new NestedResponseMsgInMarathi();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobileInMarathi = responseMsgInMarathi.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInMarathi = responseMsgInMarathi.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInMarathi, responseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInMarathi, responseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, keyEmail));

    }

    @Test
    void givenHindiLangThenValueInHindi() {
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        ResponseMsgInHindi responseMsgInHindi = new NestedResponseMsgInHindi();
        String userAlreadyExistsWithMobileInHindi = responseMsgInHindi.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInHindi = responseMsgInHindi.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyEmail));

    }

    @Test
    void givenEnglishLangThenValueInEnglish() {
        ResponseMsgInEnglish responseMsgInEnglish = new NestedResponseMsgInEnglish();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobile = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmail = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobile, responseMsgFactory.getResponseMsg(AppConstants.LANG_ENGLISH, keyMobile));
        assertEquals(userAlreadyExistsWithEmail, responseMsgFactory.getResponseMsg(AppConstants.LANG_ENGLISH, keyEmail));

    }

}
