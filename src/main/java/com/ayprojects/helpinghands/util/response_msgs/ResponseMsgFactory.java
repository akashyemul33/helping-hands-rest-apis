package com.ayprojects.helpinghands.util.response_msgs;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.tools.Utility;

public class ResponseMsgFactory {

    public static String getResponseMsg(String lang, String responseMsgKey) {
        if (Utility.isFieldEmpty(responseMsgKey)) {
            throw new IllegalArgumentException("Got null responseMsgKey parameter");
        }
        if (Utility.isFieldEmpty(lang)) {
            lang = AppConstants.LANG_ENGLISH;
        }

        AbstractResponseMessages abstractResponseMessages;
        switch (lang) {
            case AppConstants.LANG_MARATHI:
                abstractResponseMessages = new ResponseMsgInMarathi();
                break;
            case AppConstants.LANG_HINDI:
                abstractResponseMessages = new ResponseMsgInHindi();
                break;
            case AppConstants.LANG_ENGLISH:
            default:
                abstractResponseMessages = new ResponseMsgInEnglish();
                break;
        }
        return abstractResponseMessages.getResponseMsg(responseMsgKey);
    }
}
