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

        AbstractResponseMessages abstractResponseMessages = new ResponseMsgInEnglish();
        if(AppConstants.LANG_ENGLISH.equalsIgnoreCase(lang)){
            abstractResponseMessages = new ResponseMsgInEnglish();
        }
        else if(AppConstants.LANG_MARATHI.equalsIgnoreCase(lang)){
            abstractResponseMessages = new ResponseMsgInMarathi();
        }
        else if(AppConstants.LANG_HINDI.equalsIgnoreCase(lang)){
            abstractResponseMessages = new ResponseMsgInHindi();
        }

        return abstractResponseMessages.getResponseMsg(responseMsgKey);
    }
}
