package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "Posts API's",description = "CRUD for posts")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/posts")
public class PostsController {

    @Autowired
    ApiOperations<DhPromotions> apiOperations;

    @PostMapping(value="/addPosts")
    public ResponseEntity<Response<DhPromotions>> addPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPromotions dhPromotions, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication,httpHeaders, dhPromotions, StrategyName.AddPostStrategy,version), HttpStatus.CREATED);
    }

    @PutMapping(value="/deletePlace")
    public ResponseEntity<Response<DhPlace>> deletePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId,@PathVariable String version) throws ServerSideException {
        return null;
    }

    @PostMapping(value="/updatePosts")
    public ResponseEntity<Response<DhPromotions>> updatePosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @GetMapping(value = "/getPosts")
    ResponseEntity<Response<DhPromotions>> getPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String searchValue, @PathVariable String version){
    return null;
    }

    @GetMapping(value = "/getPaginatedPosts")
    ResponseEntity<Response<DhPromotions>> getPaginatedPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam (defaultValue = "7") int size, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PAGE,page);
        params.put(AppConstants.KEY_SIZE,size);
        return new ResponseEntity<>(apiOperations.get(authentication,httpHeaders,StrategyName.GetPostStrategy,params,version), HttpStatus.OK);
    }

    @GetMapping(value = "/getPaginatedPostsByPlaceId")
    ResponseEntity<Response<DhPromotions>> getPaginatedPostsByPlaceId(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam (defaultValue = "7") int size, @RequestParam String placeId, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params=new HashMap<>();
        params.put(AppConstants.KEY_PAGE,page);
        params.put(AppConstants.KEY_SIZE,size);
        params.put(AppConstants.KEY_PLACE_ID,placeId);
        return new ResponseEntity<>(apiOperations.get(authentication,httpHeaders,StrategyName.GetPostStrategy,params,version), HttpStatus.OK);
    }

}
