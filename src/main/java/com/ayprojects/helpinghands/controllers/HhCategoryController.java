package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHCategory;
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

@Api(value = "Helping Hands ad category API's", description = "CRUD for HH add categories")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/hh-category")
public class HhCategoryController {

    @Autowired
    ApiOperations<DhHHCategory> apiOperations;

    @PostMapping(value = "/addCategory")
    public ResponseEntity<Response<DhHHCategory>> addCategory(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhHHCategory dhHHCategory, @PathVariable String version) throws ServerSideException {
        Response<DhHHCategory> response = apiOperations.add(authentication, httpHeaders, dhHHCategory, StrategyName.AddHHCategoryStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getCategories")
    ResponseEntity<Response<DhHHCategory>> getCategories(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        Response<DhHHCategory> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhCategoryStrategy, null, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }
}
