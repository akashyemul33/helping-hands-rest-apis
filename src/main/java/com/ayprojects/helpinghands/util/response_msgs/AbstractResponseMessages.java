package com.ayprojects.helpinghands.util.response_msgs;

import com.ayprojects.helpinghands.util.tools.Utility;

import java.util.Map;
import java.util.NoSuchElementException;

public abstract class AbstractResponseMessages {

    abstract String getResponseMsg(String key);

    public String getResponseMsgFromMap(String key, Map<String, String> responseMsgsMap) {
        if (responseMsgsMap == null) {
            throw new IllegalArgumentException("Got null arguement for responseMsgMap !");
        }

        if (Utility.isFieldEmpty(key)) {
            throw new IllegalArgumentException("Key should not be null");
        }

        if (responseMsgsMap.containsKey(key)) {
            return responseMsgsMap.get(key);
        } else {
            throw new NoSuchElementException("Not found any value associated with key " + key);
        }
    }
}
