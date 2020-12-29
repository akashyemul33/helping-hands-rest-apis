package com.ayprojects.helpinghands.util.headers;

import com.ayprojects.helpinghands.AppConstants;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

public class Headers implements IHeaders {


    @Override
    public String getLanguageFromHeader(HttpHeaders httpHeaders) {
        if (httpHeaders == null || httpHeaders.isEmpty()) {
            return AppConstants.LANG_ENGLISH;
        }

        List<String> supportedLanguages = Arrays.asList(AppConstants.SUPPORTED_LANGUAGES);
        String langInHeader = AppConstants.LABEL_HEADER_APPLANGUAGE;
        if (httpHeaders.containsKey(langInHeader)) {
            String langValue = httpHeaders.get(langInHeader).get(0);
            if (supportedLanguages.contains(langValue)) {
                return langValue;
            }
            return AppConstants.LANG_ENGLISH;
        }
        return AppConstants.LANG_ENGLISH;
    }
}
