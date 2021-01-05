package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.classes.AddUserApi;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
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

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/tempusers")
public class TempUserController {

    @Autowired
    UserService userService;

    @Autowired
    ApiOperations<DhUser> apiOperations;

    @PostMapping(value = "/addUser")
    public ResponseEntity<Response<DhUser>> addUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUser, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(null, httpHeaders, dhUser, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserDetails")
    ResponseEntity<Response<LoginResponse>> getInitialDataOnLogin(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String fcmToken, @RequestParam String lastLogoutTime, @PathVariable String version) {
        return new ResponseEntity<>(userService.getUserDetails(httpHeaders, authentication, fcmToken, lastLogoutTime, version), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByMobile")
    ResponseEntity<Response<DhUser>> getUserByMobile(@RequestHeader HttpHeaders httpHeaders, @RequestParam String mobileNumber, @RequestParam String countryCode, @RequestParam String fcmToken, @PathVariable String version) {
        return new ResponseEntity<>(userService.getUserByMobile(httpHeaders, mobileNumber, countryCode, fcmToken, version), HttpStatus.OK);
    }
}
