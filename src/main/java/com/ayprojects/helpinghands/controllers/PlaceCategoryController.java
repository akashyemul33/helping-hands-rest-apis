package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
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

@Api(value = "Place category API's", description = "CRUD for place categories")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/categories")
public class PlaceCategoryController {

    @Autowired
    ApiOperations apiOperations;

    @PostMapping(value = "/addPlaceCategory")
    public ResponseEntity<Response<DhPlaceCategories>> addPlaceCategory(Authentication authentication, @RequestHeader HttpHeaders httpHeaders, @RequestBody DhPlaceCategories dhPlaceCategories, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhPlaceCategories, StrategyName.AddPlaceMainCategoryStrategy, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addPlaceSubCategory")
    public ResponseEntity<Response<PlaceSubCategories>> addPlaceSubCategory(Authentication authentication, @RequestHeader HttpHeaders httpHeaders, @RequestBody PlaceSubCategories placeSubCategory, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, placeSubCategory, StrategyName.AddPlaceSubCategoryStrategy, version), HttpStatus.CREATED);
    }

    @PostMapping(value = "/addPlaceSubCategories")
    public ResponseEntity<Response<PlaceSubCategories>> addPlaceSubCategories(Authentication authentication, @RequestHeader HttpHeaders httpHeaders, @RequestBody List<PlaceSubCategories> placeSubCategories, @PathVariable String version) throws ServerSideException {
        for (PlaceSubCategories p : placeSubCategories) {
            apiOperations.add(authentication, httpHeaders, p, StrategyName.AddPlaceSubCategoryStrategy, version);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*returns all the maincategories with given status
    by default the status will be active*/
    @GetMapping(value = "/getAllActivePlaceCategories")
    ResponseEntity<Response<DhPlaceCategories>> getAllActivePlaceCategories(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceCategoriesStrategy, new HashMap<>(), version), HttpStatus.OK);
    }

    @GetMapping(value = "/getAllActivePlaceCategoriesByType")
    ResponseEntity<Response<DhPlaceCategories>> getAllActivePlaceCategoriesByType(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String typeOfPlaceCategory) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_TYPE_OF_PLACECATEGORY, typeOfPlaceCategory);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceCategoriesStrategy, params, version), HttpStatus.OK);
    }

}
