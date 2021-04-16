package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProduct;
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
import java.util.List;

import io.swagger.annotations.Api;

@Api(value = "Products API's", description = "CRUD for products")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/products")
public class ProductController {

    @Autowired
    ApiOperations<DhProduct> apiOperations;

    @PostMapping(value = "/addProduct")
    public ResponseEntity<Response<DhProduct>> addProduct(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhProduct dhProduct, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhProduct, StrategyName.AddProductStrategy, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addProducts")
    public ResponseEntity<Response<DhProduct>> addProducts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody List<DhProduct> dhProducts, @PathVariable String version) throws ServerSideException {
        for (DhProduct dhProduct : dhProducts) {
            apiOperations.add(authentication, httpHeaders, dhProduct, StrategyName.AddProductStrategy, version);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/getProductsForSubCategory")
    ResponseEntity<Response<DhProduct>> getProductsForSubCategory(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String subPlaceCategoryId, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_SUB_PLACECATEGORY_ID, subPlaceCategoryId);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetProductStrategy, params, version), HttpStatus.OK);
    }

    @GetMapping(value = "/searchProducts")
    ResponseEntity<Response<DhProduct>> searchProducts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String searchKey, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PRODUCT_SEARCH, searchKey);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetProductStrategy, params, version), HttpStatus.OK);
    }

}
