package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.User;
import com.ayprojects.helpinghands.services.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/signUp")
    public ResponseEntity<Response<User>> signUp(@RequestHeader HttpHeaders httpHeaders, @RequestBody User userDetails, @PathVariable String version){
        return new ResponseEntity<>(userService.signUp(userDetails, httpHeaders), HttpStatus.CREATED);
    }

    @RequestMapping(value="/login")
    public String login(){
        return "Login";
    }

}
