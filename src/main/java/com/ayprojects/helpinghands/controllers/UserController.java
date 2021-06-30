package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/users")
public class UserController {

    @Autowired
    ApiOperations<DhUser> apiOperations;

    @PostMapping(value = "/updateUserSettings")
    public ResponseEntity<Response<DhUser>> updateUserSettings(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUser, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.update(null, httpHeaders, null, dhUser, StrategyName.UpdateUserStrategy, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<Response<DhUser>> addUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUser, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(null, httpHeaders, dhUser, StrategyName.AddUserStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUserByMobile")
    ResponseEntity<Response<DhUser>> getUserByMobile(@RequestHeader HttpHeaders httpHeaders, @RequestParam String mobileNumber, @RequestParam String countryCode, @RequestParam String fcmToken, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_MOBILE, mobileNumber);
        params.put(AppConstants.KEY_COUNTRY_CODE, countryCode);
        params.put(AppConstants.KEY_FCM_TOKEN, fcmToken);
        return new ResponseEntity<>(apiOperations.get(null, httpHeaders, StrategyName.GetUserStrategy, params, version), HttpStatus.OK);
    }
}
