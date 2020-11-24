package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
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

import io.swagger.annotations.Api;

@Api(value = "Rating and Comments API's",description = "CRUD for Ratings and Comments")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/ratingAndComments")
public class RatingAndCommentsController {

    @Autowired
    RatingCommentsService ratingCommentsService;

    @PostMapping(value="/addRatingAndComments")
    public ResponseEntity<Response<DhRatingAndComments>> addRatingAndComments(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhRatingAndComments dhRatingComments, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(ratingCommentsService.addRatingAndComments(authentication,httpHeaders, dhRatingComments,version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getPaginatedRatingsAndComments")
    ResponseEntity<Response<DhRatingAndComments>> getPaginatedRatingsAndComments(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam (defaultValue = "7") int size, @RequestParam String contentId, @RequestParam String contentType, @PathVariable String version){
        return new ResponseEntity<>(ratingCommentsService.getPaginatedRatingAndComments(authentication,httpHeaders,contentId,contentType, AppConstants.STATUS_ACTIVE,page,size,version), HttpStatus.OK);
    }
}