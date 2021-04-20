package com.ayprojects.helpinghands.services.views;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface ViewService {
    Response<DhViews> addViews(Authentication authentication, HttpHeaders httpHeaders, DhViews dhViews, String version) throws ServerSideException;
    Response<DhViews> getPaginatedViews(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version);
}
