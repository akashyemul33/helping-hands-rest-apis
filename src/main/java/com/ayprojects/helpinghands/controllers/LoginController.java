package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/login")
public class LoginController {

    @Autowired
    ApiOperations<LoginResponse> apiOperations;

    @GetMapping(value = "/getUserDetails")
    ResponseEntity<Response<LoginResponse>> getInitialDataOnLogin(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String fcmToken, @RequestParam String lastLogoutTime, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_AUTHENTICATION, authentication);
        params.put(AppConstants.KEY_NEW_FCM_TOKEN, fcmToken);
        params.put(AppConstants.KEY_LAST_LOGOUT_TIME, lastLogoutTime);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetLoginResponseStrategy, params, version), HttpStatus.OK);
    }
}
