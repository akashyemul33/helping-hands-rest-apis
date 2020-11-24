package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@Api(value = "Login API's",description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/login")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping(value="/login")
    public ResponseEntity<Response<AccessTokenModel>> login(@RequestHeader HttpHeaders httpHeaders, @RequestBody AuthenticationRequest authenticationRequest, @PathVariable String version){
        return new ResponseEntity<>(userService.login(authenticationRequest, httpHeaders,version), HttpStatus.OK);
    }
}