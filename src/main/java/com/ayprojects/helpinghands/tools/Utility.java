package com.ayprojects.helpinghands.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class Utility {
    public static String getLanguageFromHeader(HttpHeaders headers) {
        String lang = null;

        String[] languages = AppConstants.LANGUAGES;
        List<String> languageList = Arrays.asList(languages);
        lang = "en";
        if (headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE)!=null) {
            lang = headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE).get(0);
            if (isFieldEmpty(lang) || !languageList.contains(lang)) {
                lang = "en";
            }
        } else {
            lang = "en";
        }
        return lang;
    }

    public static boolean isFieldEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static String currentDateTimeInUTC() {
        DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getResponseMessage(String msg, String language) {
        try {
            Field f = ResponseMessages.class.getField(language+"_"+msg);
            LOGGER.info("Utility->getResponseMessage()->f.getName() is "+f.getName());
            String s= (String) f.get(new ResponseMessages());
            LOGGER.info("Utility->getResponseMessage()->String got from REsponseMessages is "+s);
            return s;
        } catch (Exception ex) {
            LOGGER.info("Utility->getResponseMessage()->got exception="+ex.getMessage());
            ex.getMessage();

        }
        return "";
    }

}
