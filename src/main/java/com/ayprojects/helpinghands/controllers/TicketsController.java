package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhTicket;
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

@Api(value = "Place help tickets API's", description = "CRUD for tickets")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/tickets")
public class TicketsController {

    @Autowired
    ApiOperations<DhTicket> apiOperations;

    @PostMapping(value = "/addTicket")
    public ResponseEntity<Response<DhTicket>> addTicket(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhTicket dhTicket, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhTicket, StrategyName.AddProductStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getTicketsByUserId")
    ResponseEntity<Response<DhTicket>> getTicketsByUserId(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String userId, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetTicketStrategy, params, version), HttpStatus.OK);
    }
}
