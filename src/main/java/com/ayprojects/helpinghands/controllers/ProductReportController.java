package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProductReport;
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

@Api(value = "Products report API's", description = "CRUD for products report")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/productReports")
public class ProductReportController {

    @Autowired
    ApiOperations<DhProductReport> apiOperations;

    @PostMapping(value = "/addProductReport")
    public ResponseEntity<Response<DhProductReport>> addProductReport(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhProductReport dhProductReport, @PathVariable String version) throws ServerSideException {
        Response<DhProductReport> response = apiOperations.add(authentication, httpHeaders, dhProductReport, StrategyName.AddProductReportStrategy, version);
        if (response.getStatus())
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getProductReports")
    ResponseEntity<Response<DhProductReport>> getProductReport(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        Response<DhProductReport> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetProductReportStrategy, null, version);
        if (response.getStatus())
            return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

}
