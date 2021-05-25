package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhNotifications;
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

@Api(value = "Views API's", description = "CRUD for Views")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/notifications")
public class NotificationsController {

    @Autowired
    ApiOperations<DhNotifications> apiOperations;

    @GetMapping(value = "/getNotifications")
    ResponseEntity<Response<DhNotifications>> getNotifications(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String userId, @RequestParam ContentType contentType, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        params.put(AppConstants.KEY_CONTENT_TYPE, contentType);
        Response<DhNotifications> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetNotificationsStrategy, params, version);
        if (response.getStatus()) return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
}
