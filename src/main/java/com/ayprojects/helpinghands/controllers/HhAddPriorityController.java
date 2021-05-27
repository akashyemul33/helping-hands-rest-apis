package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPriorities;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "Helping Hands add priority API's", description = "CRUD for HH add priorities")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/hh-addpriority")
public class HhAddPriorityController {

    @Autowired
    ApiOperations<DhHHPriorities> apiOperations;

    @PostMapping(value = "/addPriority")
    public ResponseEntity<Response<DhHHPriorities>> addPriority(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhHHPriorities dhHHPriorities, @PathVariable String version) throws ServerSideException {
        Response<DhHHPriorities> response = apiOperations.add(authentication, httpHeaders, dhHHPriorities, StrategyName.AddHHPriorityStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getPriorities")
    ResponseEntity<Response<DhHHPriorities>> getPriorities(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        Response<DhHHPriorities> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhPriorityStrategy, null, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
}
