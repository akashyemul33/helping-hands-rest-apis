package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProductReportType;
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

@Api(value = "Products report type API's", description = "CRUD for products report types")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/productReportTypes")
public class ProductReportTypeController {

    @Autowired
    ApiOperations<DhProductReportType> apiOperations;

    @PostMapping(value = "/addProductReportType")
    public ResponseEntity<Response<DhProductReportType>> addProductReportType(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhProductReportType dhProductReportType, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhProductReportType, StrategyName.AddProductReportTypeStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getProductReportTypes")
    ResponseEntity<Response<DhProductReportType>> getProductReportTypes(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetProductReportTypeStrategy, null, version), HttpStatus.OK);
    }

}
