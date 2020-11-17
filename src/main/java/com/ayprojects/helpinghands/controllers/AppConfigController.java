package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
import com.ayprojects.helpinghands.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "App Configuration Controller",description = "CRUD Api's for app configurations")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/appconfig")
public class AppConfigController {

    @Autowired
    AppConfigService appConfigService;

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<Response<DhAppConfig>> addAppConfig(Authentication authentication,@RequestHeader HttpHeaders httpHeaders, @RequestBody DhAppConfig dhAppConfig, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(appConfigService.addAppConfig(authentication,httpHeaders,dhAppConfig,version), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAppConfig",method = RequestMethod.GET)
    ResponseEntity<Response<DhAppConfig>> getAppConfig(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(appConfigService.getActiveAppConfig(httpHeaders,authentication,version), HttpStatus.OK);
    }

}
