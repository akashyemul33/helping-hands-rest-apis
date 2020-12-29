package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.test_categories.FastTest;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInHindi;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInMarathi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseMsgTest {

    static ResponseMsgInEnglish responseMsgInEnglish;
    static ResponseMsgInHindi responseMsgInHindi;
    static ResponseMsgInMarathi responseMsgInMarathi;

    @BeforeAll
    static void setup() {
        responseMsgInEnglish = new ResponseMsgInEnglish();
        responseMsgInHindi = new ResponseMsgInHindi();
        responseMsgInMarathi = new ResponseMsgInMarathi();
    }

    @FastTest
    @Test
    void instanceVariablesShouldBeSameForAllRespMsgClasses() {

        if (responseMsgInEnglish.getResponseMsgsMap() != null && responseMsgInHindi.getResponseMsgsMap() != null && responseMsgInMarathi.getResponseMsgsMap() != null) {
            int responseMsgInEnglishMapSize = responseMsgInEnglish.getResponseMsgsMap().size();
            int responseMsgInHindiMapSize = responseMsgInHindi.getResponseMsgsMap().size();
            int responseMsgInMarathiMapSize = responseMsgInMarathi.getResponseMsgsMap().size();
            assertTrue((responseMsgInEnglishMapSize == responseMsgInHindiMapSize) && (responseMsgInEnglishMapSize == responseMsgInMarathiMapSize), "Map size of other response message classes is not matching with english response message class.");

            for (String key : responseMsgInEnglish.getResponseMsgsMap().keySet()) {
                assertTrue(responseMsgInHindi.getResponseMsgsMap().containsKey(key) && responseMsgInMarathi.getResponseMsgsMap().containsKey(key), String.format("Key %s not found in response msg classes other than english.", key));
            }
        }
    }

    @FastTest
    @Test
    void instanceVariablesShouldContainValidMsgForAllRespMsgClasses() {
        String[] invalidMessages = new String[]{"null", "[aA][bB][cC].*", "[xX][yY][zZ].*"};
        for (String value : responseMsgInEnglish.getResponseMsgsMap().values()) {
            assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

        for (String value : responseMsgInMarathi.getResponseMsgsMap().values()) {
            assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

        for (String value : responseMsgInHindi.getResponseMsgsMap().values()) {
            assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

    }
}
