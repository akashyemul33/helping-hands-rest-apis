package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "Views API's", description = "CRUD for Views")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/views")
public class ViewController {

    @Autowired
    ApiOperations<DhViews> apiOperations;

    /*@PostMapping(value = "/addViews")
    public ResponseEntity<Response<DhViews>> addViews(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhViews dhViews, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(viewService.addViews(authentication, httpHeaders, dhViews, version), HttpStatus.CREATED);
    }*/

    @GetMapping(value = "/getPaginatedViews")
    ResponseEntity<Response<DhViews>> getPaginatedViews(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam String contentId, @RequestParam String contentType, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_CONTENT_ID, contentId);
        params.put(AppConstants.KEY_CONTENT_TYPE, contentType);
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        params.put(AppConstants.STATUS, AppConstants.STATUS_ACTIVE);
        Response<DhViews> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetViewsStrategy, params, version);
        if (response.getStatus()) return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

    }
}
