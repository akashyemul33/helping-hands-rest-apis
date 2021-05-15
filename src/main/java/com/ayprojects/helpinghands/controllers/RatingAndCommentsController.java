package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.rating_comments.RatingCommentsService;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import io.swagger.annotations.Api;

@Api(value = "Rating and Comments API's", description = "CRUD for Ratings and Comments")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/ratingAndComments")
public class RatingAndCommentsController {

    @Autowired
    ApiOperations<DhRatingAndComments> apiOperations;

    @PostMapping(value = "/addRatingAndComments")
    public ResponseEntity<Response<DhRatingAndComments>> addRatingAndComments(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhRatingAndComments dhRatingComments, @RequestParam("userId") String userId,@RequestParam("ContentName") String contentName,@PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.USER_ID, userId);
        params.put(AppConstants.CONTENT_NAME, contentName);
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhRatingComments, StrategyName.AddRatingStrategy,params, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getPaginatedRatingsAndComments")
    ResponseEntity<Response<DhRatingAndComments>> getPaginatedRatingsAndComments(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam String contentId, @RequestParam String contentType, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_CONTENT_ID, contentId);
        params.put(AppConstants.KEY_CONTENT_TYPE, contentType);
        params.put(AppConstants.STATUS, AppConstants.STATUS_ACTIVE);
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetRatingStrategy, params, version), HttpStatus.OK);
    }
}
