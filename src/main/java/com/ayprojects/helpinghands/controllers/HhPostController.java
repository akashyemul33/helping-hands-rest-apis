package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.HhPostUpdateEnums;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
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

@Api(value = "Helping Hands ad post API's", description = "CRUD for HH add post")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/hh-post")
public class HhPostController {

    @Autowired
    ApiOperations<DhHHPost> apiOperations;

    @PostMapping(value = "/addPost")
    public ResponseEntity<Response<DhHHPost>> addPost(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhHHPost dhHHPost, @PathVariable String version) throws ServerSideException {
        Response<DhHHPost> response = apiOperations.add(authentication, httpHeaders, dhHHPost, StrategyName.AddHHPostStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @PutMapping(value = "/updatePost")
    public ResponseEntity<Response<DhHHPost>> updatePost(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhHHPost dhHHPost, @RequestParam HhPostUpdateEnums hhPostUpdateEnums, @RequestParam String hhPostId, @RequestParam String otherUserId, @RequestParam String otherUserName, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_HH_POST_STEP_ENUM, hhPostUpdateEnums);
        params.put(AppConstants.KEY_HH_POST_ID, hhPostId);
        params.put(AppConstants.KEY_OTHER_USER_ID, otherUserId);
        params.put(AppConstants.KEY_HH_OTHER_USERNAME, otherUserName);
        Response<DhHHPost> response = apiOperations.update(authentication, httpHeaders, params, dhHHPost, StrategyName.UpdateHhPostStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * DhHhPost need below things:helped user details at 0th pos,helpedUserId in helpedUserIds list at 0th pos,postId,userId,userName
     *
     * @param httpHeaders
     * @param authentication
     * @param dhHHPost
     * @param helpedUserId
     * @param helpedUserName
     * @param version
     * @return
     * @throws ServerSideException
     */
    @PutMapping(value = "/markAsHelped")
    public ResponseEntity<Response<DhHHPost>> markAsHelped(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhHHPost dhHHPost,@PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_HH_POST_STEP_ENUM, HhPostUpdateEnums.MARK_HELPED);
        Response<DhHHPost> response = apiOperations.update(authentication, httpHeaders, params, dhHHPost, StrategyName.UpdateHhPostStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getPaginatedPosts")
    ResponseEntity<Response<DhHHPost>> getPaginatedPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam double lat, @RequestParam double lng, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        params.put(AppConstants.KEY_LAT, lat);
        params.put(AppConstants.KEY_LNG, lng);
        Response<DhHHPost> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhPostsStrategy, params, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getPostsWithUserId")
    ResponseEntity<Response<DhHHPost>> getPostsWithUserId(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String userId) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        Response<DhHHPost> response = apiOperations.get(authentication, httpHeaders, StrategyName.GetHhPostsStrategy, params, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

}
