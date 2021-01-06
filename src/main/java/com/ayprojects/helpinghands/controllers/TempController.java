package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
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

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for ")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/tempapis")
public class TempController {

    @Autowired
    ApiOperations<DhUser> apiOperations;

    @Autowired
    ApiOperations<DhPlace> placeApiOperations;

    @PostMapping(value = "/addUser")
    public ResponseEntity<Response<DhUser>> addUser(@RequestHeader HttpHeaders httpHeaders, @RequestBody DhUser dhUser, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(null, httpHeaders, null, StrategyName.AddUserStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getPaginatedPlaces")
    ResponseEntity<Response<DhPlace>> getPaginatedPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam double lat, @RequestParam double lng, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        params.put(AppConstants.KEY_LAT, lat);
        params.put(AppConstants.KEY_LNG, lng);
        return new ResponseEntity<>(placeApiOperations.get(authentication, httpHeaders, version, StrategyName.GetPlaceStrategy, params), HttpStatus.OK);
    }

    @GetMapping(value = "/getBusinessPlacesOfUserWhileAddingPost")
    ResponseEntity<Response<DhPlace>> getBusinessPlacesOfUserWhileAddingPost(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String userId) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        return new ResponseEntity<>(placeApiOperations.get(authentication, httpHeaders, version, StrategyName.GetPlaceStrategy, params), HttpStatus.OK);
    }

}
