package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "App Configuration API's",description = "CRUD Api's for app configurations")
@RestController
@ResponseStatus
@RequestMapping("/api/{version}/appconfig")
public class AppConfigController {

    @Autowired
    ApiOperations<DhAppConfig> apiOperations;

    @PostMapping(value="/add")
    public ResponseEntity<Response<DhAppConfig>> addAppConfig(Authentication authentication,@RequestHeader HttpHeaders httpHeaders, @RequestBody DhAppConfig dhAppConfig, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication,httpHeaders,dhAppConfig,StrategyName.GetAppConfigStrategy,version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAppConfig")
    ResponseEntity<Response<DhAppConfig>> getAppConfig(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.get(authentication,httpHeaders,StrategyName.GetAppConfigStrategy,new HashMap<>(),version), HttpStatus.OK);
    }

}
