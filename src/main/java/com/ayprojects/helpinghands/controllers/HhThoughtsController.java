package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhThought;
import com.ayprojects.helpinghands.models.Response;

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

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "Helping Hands thoughts API's", description = "CRUD for HH thoughts")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/hh-thoughts")
public class HhThoughtsController {
    @Autowired
    ApiOperations<DhThought> apiOperations;

    @PostMapping(value = "/addThought")
    public ResponseEntity<Response<DhThought>> addThought(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhThought dhThought, @PathVariable String version) throws ServerSideException {
        Response<DhThought> response = apiOperations.add(authentication, httpHeaders, dhThought, StrategyName.AddHHThoughtStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getSingleThought")
    ResponseEntity<Response<DhThought>> getSingleThought(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String userId) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        params.put(AppConstants.API_TYPE, AppConstants.API_TYPE_SINGLE_THOUGHT);
        Response<DhThought> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhThoughtStrategy, params, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getAllThoughts")
    ResponseEntity<Response<DhThought>> getAllThoughts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String userId) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        Response<DhThought> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhThoughtStrategy, params, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
}
