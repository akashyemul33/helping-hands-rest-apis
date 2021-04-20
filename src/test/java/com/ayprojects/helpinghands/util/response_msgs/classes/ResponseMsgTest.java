package com.ayprojects.helpinghands.util.response_msgs.classes;

import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInEnglish;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInHindi;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgInMarathi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ResponseMsgTest {

    static ResponseMsgInEnglish responseMsgInEnglish;
    static ResponseMsgInHindi responseMsgInHindi;
    static ResponseMsgInMarathi responseMsgInMarathi;

    @BeforeAll
    static void setup() {
        responseMsgInEnglish = new NestedResponseMsgInEnglish();
        responseMsgInHindi = new NestedResponseMsgInHindi();
        responseMsgInMarathi = new NestedResponseMsgInMarathi();
    }

    @Test
    void instanceVariablesShouldBeSameForAllRespMsgClasses() {

        if (responseMsgInEnglish.getResponseMsgsMap() != null && responseMsgInHindi.getResponseMsgsMap() != null && responseMsgInMarathi.getResponseMsgsMap() != null) {
            int responseMsgInEnglishMapSize = responseMsgInEnglish.getResponseMsgsMap().size();
            int responseMsgInHindiMapSize = responseMsgInHindi.getResponseMsgsMap().size();
            int responseMsgInMarathiMapSize = responseMsgInMarathi.getResponseMsgsMap().size();
            Assertions.assertTrue((responseMsgInEnglishMapSize == responseMsgInHindiMapSize) && (responseMsgInEnglishMapSize == responseMsgInMarathiMapSize), "Map size of other response message classes is not matching with english response message class.");

            for (String key : responseMsgInEnglish.getResponseMsgsMap().keySet()) {
                Assertions.assertTrue(responseMsgInHindi.getResponseMsgsMap().containsKey(key) && responseMsgInMarathi.getResponseMsgsMap().containsKey(key), String.format("Key %s not found in response msg classes other than english.", key));
            }
        }
    }

    @Test
    void instanceVariablesShouldContainValidMsgForAllRespMsgClasses() {
        String[] invalidMessages = new String[]{"null", "[aA][bB][cC].*", "[xX][yY][zZ].*"};
        for (String value : responseMsgInEnglish.getResponseMsgsMap().values()) {
            Assertions.assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                Assertions.assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

        for (String value : responseMsgInMarathi.getResponseMsgsMap().values()) {
            Assertions.assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                Assertions.assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

        for (String value : responseMsgInHindi.getResponseMsgsMap().values()) {
            Assertions.assertFalse(value == null || value.isEmpty(), "Got empty value");
            for (String invalidContentStr : invalidMessages) {
                Assertions.assertFalse(value.matches(invalidContentStr), String.format("Got value \"%s\" which is found in our invalid contents. Please check this in ResponseMsgInEnglish class", value));
            }
        }

    }
}
