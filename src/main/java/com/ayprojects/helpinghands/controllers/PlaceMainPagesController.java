package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.PlaceMainPage;
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

@Api(value = "Place Main Pages API's", description = "CRUD for place main pages")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/placeMainPages")
public class PlaceMainPagesController {

    @Autowired
    ApiOperations<PlaceMainPage> apiOperations;

    @PostMapping(value = "/addPlaceMainPage")
    public ResponseEntity<Response<PlaceMainPage>> addPlaceMainPage(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody PlaceMainPage placeMainPage, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, placeMainPage, StrategyName.AddPlaceMainPageStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getPaginatedPlaceMainPages")
    ResponseEntity<Response<PlaceMainPage>> getPaginatedPlaceMainPages(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceMainPageStrategy, params, version), HttpStatus.OK);
    }

}
