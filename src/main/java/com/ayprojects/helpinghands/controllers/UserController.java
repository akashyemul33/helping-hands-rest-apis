package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.api.classes.AddUserApi;
import com.ayprojects.helpinghands.api.classes.ApiOperations;
import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.user.UserService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<Response<AccessTokenModel>> login(@RequestHeader HttpHeaders httpHeaders, @RequestBody AuthenticationRequest authenticationRequest, @PathVariable String version) {
        return new ResponseEntity<>(userService.login(authenticationRequest, httpHeaders, version), HttpStatus.OK);
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<Response<DhUser>> addUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUser, @PathVariable String version) {
        ApiOperations<DhUser> a = new ApiOperations<>();
        a.setAddBehaviour(new AddUserApi(httpHeaders, dhUser, version));
        return new ResponseEntity<>(a.add(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserDetails")
    ResponseEntity<Response<LoginResponse>> getInitialDataOnLogin(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) {
        return new ResponseEntity<>(userService.getUserDetails(httpHeaders, authentication, version), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByMobile")
    ResponseEntity<Response<DhUser>> getUserByMobile(@RequestHeader HttpHeaders httpHeaders, @RequestParam String mobileNumber, @RequestParam String countryCode, @PathVariable String version) {
        return new ResponseEntity<>(userService.getUserByMobile(httpHeaders, mobileNumber, countryCode, version), HttpStatus.OK);
    }
}
