package com.ayprojects.helpinghands.util.response_msgs.factory;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInHindi;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInMarathi;

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
    void givenNullKeyWhenGetResponseMsgFromFactoryThenNull() {
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
    void givenEmptyLangButValidKeyWhenGetResponseMsgFromFactoryThenValueFromEnglish() {
        ResponseMsgInEnglish responseMsgInEnglish = new ResponseMsgInEnglish();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobile = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmail = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobile, responseMsgFactory.getResponseMsg(null, keyMobile));
        assertEquals(userAlreadyExistsWithEmail, responseMsgFactory.getResponseMsg("", keyEmail));
    }

    @Test
    void givenInvalidLangWithValidKeyWhenGetResponseMsgFromFactoryThenValueFromEnglish() {
        ResponseMsgInEnglish responseMsgInEnglish = new ResponseMsgInEnglish();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobile = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmail = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobile, responseMsgFactory.getResponseMsg("ka", keyMobile));
        assertEquals(userAlreadyExistsWithEmail, responseMsgFactory.getResponseMsg("adlkfj", keyEmail));
    }

    @Test
    void givenMarthiLangWhenGetResponseMsgFromFactoryThenValueInMarathi() {
        ResponseMsgInMarathi responseMsgInMarathi = new ResponseMsgInMarathi();
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        String userAlreadyExistsWithMobileInMarathi = responseMsgInMarathi.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInMarathi = responseMsgInMarathi.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInMarathi, responseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInMarathi, responseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, keyEmail));

        ResponseMsgInHindi responseMsgInHindi = new ResponseMsgInHindi();
        String userAlreadyExistsWithMobileInHindi = responseMsgInHindi.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInHindi = responseMsgInHindi.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyEmail));

    }

    @Test
    void givenHindiLangWhenGetResponseMsgFromFactoryThenValueInHindi() {
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        ResponseMsgInHindi responseMsgInHindi = new ResponseMsgInHindi();
        String userAlreadyExistsWithMobileInHindi = responseMsgInHindi.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInHindi = responseMsgInHindi.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInHindi, responseMsgFactory.getResponseMsg(AppConstants.LANG_HINDI, keyEmail));

    }

    @Test
    void givenEnglishLangWhenGetResponseMsgFromFactoryThenValueInEnglish() {
        String keyMobile = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
        String keyEmail = AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
        ResponseMsgInEnglish responseMsgInEnglish = new ResponseMsgInEnglish();
        String userAlreadyExistsWithMobileInEnglish = responseMsgInEnglish.getResponseMsg(keyMobile);
        String userAlreadyExistsWithEmailInEnglish = responseMsgInEnglish.getResponseMsg(keyEmail);
        assertEquals(userAlreadyExistsWithMobileInEnglish, responseMsgFactory.getResponseMsg(AppConstants.LANG_ENGLISH, keyMobile));
        assertEquals(userAlreadyExistsWithEmailInEnglish, responseMsgFactory.getResponseMsg(AppConstants.LANG_ENGLISH, keyEmail));

    }

}
