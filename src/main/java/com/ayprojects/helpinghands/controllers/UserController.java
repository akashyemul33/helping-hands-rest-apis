package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;
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

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/addUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhUser>> addUser(@RequestHeader HttpHeaders httpHeaders, @RequestParam(value = "userBody") String userBody, @RequestPart(value="userImage",required = false) MultipartFile userImage, @PathVariable String version){
        return new ResponseEntity<>(userService.addUser(httpHeaders,userImage,userBody,version), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getUserDetails",method = RequestMethod.GET)
    ResponseEntity<Response<LoginResponse>> getInitialDataOnLogin(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version){
        return new ResponseEntity<>(userService.getUserDetails(httpHeaders,authentication,version), HttpStatus.OK);
    }

}
