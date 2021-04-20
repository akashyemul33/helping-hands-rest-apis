package com.ayprojects.helpinghands.util.headers;

import com.ayprojects.helpinghands.AppConstants;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetLangFromHeaderMethodTest {

    @Test
    void givenNullWhenGetLangFromHeaderThenEnglish() {
        assertEquals(AppConstants.LANG_ENGLISH, IHeaders.getLanguageFromHeader(null));
    }

    @Test
    void givenEmptyObjWhenGetLangFromHeaderThenEnglish() {
        assertEquals(AppConstants.LANG_ENGLISH, IHeaders.getLanguageFromHeader(new HttpHeaders()));
    }

    @Test
    void givenNotSetLanguageWhenGetLangFromHeaderThenEnglish() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("ABC", "adsblkj");
        assertEquals(AppConstants.LANG_ENGLISH, IHeaders.getLanguageFromHeader(httpHeaders));
    }

    @Test
    void givenValidObjWithLanguageSetWhenGetLangFromHeaderThenGivenLanguage() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_MARATHI);
        assertEquals(AppConstants.LANG_MARATHI, IHeaders.getLanguageFromHeader(httpHeaders));
    }

    @Test
    void givenLangNotInLangListWhenGetLangFromHeaderThenEnglish() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, "ka");
        assertEquals(AppConstants.LANG_ENGLISH, IHeaders.getLanguageFromHeader(httpHeaders));
    }

}
