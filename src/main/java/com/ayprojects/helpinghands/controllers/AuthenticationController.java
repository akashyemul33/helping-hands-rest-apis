package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}")
public class AuthenticationController {


    @Autowired
    UserService userService;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<Response<AccessTokenModel>> login(@RequestHeader HttpHeaders httpHeaders, @RequestBody AuthenticationRequest authenticationRequest, @PathVariable String version){
        return new ResponseEntity<>(userService.login(authenticationRequest, httpHeaders), HttpStatus.OK);
    }
}
