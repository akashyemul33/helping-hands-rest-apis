package com.ayprojects.helpinghands.util.headers;

import com.ayprojects.helpinghands.models.Header;

import org.springframework.http.HttpHeaders;

public interface IHeaders {
    String getLanguageFromHeader(HttpHeaders httpHeaders);
}
