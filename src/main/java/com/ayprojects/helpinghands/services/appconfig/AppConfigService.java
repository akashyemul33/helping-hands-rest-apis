package com.ayprojects.helpinghands.services.appconfig;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AppConfigService {
    Response<DhAppConfig> addAppConfig(Authentication authentication, HttpHeaders httpHeaders, DhAppConfig dhAppConfig, String version) throws ServerSideException;
    Response<DhAppConfig> getActiveAppConfig(HttpHeaders httpHeaders, Authentication authentication, String version);
}
