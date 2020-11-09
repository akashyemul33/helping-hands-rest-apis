package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.placecategories.PlaceCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/categories")
public class PlaceCategoryController {

    @Autowired
    PlaceCategoryService placeCategoryService;

    @RequestMapping(value="/addPlaceCategory", method= RequestMethod.POST)
    public ResponseEntity<Response<DhPlaceCategories>> addPlaceCategory(Authentication authentication,@RequestHeader HttpHeaders httpHeaders, @RequestBody DhPlaceCategories dhPlaceCategories, @PathVariable String version){
        return new ResponseEntity<>(placeCategoryService.addPlaceMainCategory(authentication,httpHeaders,dhPlaceCategories,version), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{mainPlaceCategoryId}/addPlaceSubCategory", method= RequestMethod.POST)
    public ResponseEntity<Response<PlaceSubCategories>> addPlaceSubCategory(Authentication authentication, @RequestHeader HttpHeaders httpHeaders, @RequestBody PlaceSubCategories placeSubCategory, @PathVariable String mainPlaceCategoryId, @PathVariable String version){
        return new ResponseEntity<>(placeCategoryService.addPlaceSubCategory(authentication,httpHeaders,placeSubCategory,mainPlaceCategoryId,version), HttpStatus.CREATED);
    }

    /*returns all the maincategories with given status
    by default the status will be active*/
    @RequestMapping(value = "/getAllPlaceCategoriesWithStatus/{status}",method = RequestMethod.GET)
    ResponseEntity<Response<List<DhPlaceCategories>>> getAllActivePlaceCategories(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String status,@PathVariable String version){
        return new ResponseEntity<>(placeCategoryService.findAllByStatus(authentication,httpHeaders, status,version), HttpStatus.OK);
    }

}
