package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/signUp", method=RequestMethod.POST)
    public ResponseEntity<Response<DhUser>> signUp(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUserDetails, @PathVariable String version){
        return new ResponseEntity<>(userService.signUp(dhUserDetails, httpHeaders,version), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getUserDetails",method = RequestMethod.GET)
    ResponseEntity<Response<LoginResponse>> getInitialDataOnLogin(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version){
        return new ResponseEntity<>(userService.getUserDetails(httpHeaders,authentication,version), HttpStatus.OK);
    }

}
