package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.place.PlaceService;
import com.ayprojects.helpinghands.services.posts.PostsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;

@Api(value = "Posts API's",description = "CRUD for posts")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/posts")
public class PostsController {

    @Autowired
    PostsService postsService;

    @PostMapping(value="/addPosts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhPosts>> addPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "postBody") String postBody, @RequestPart(value="postImages",required = false) MultipartFile[] postImages, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(postsService.addPost(authentication,httpHeaders,postImages,postBody,version), HttpStatus.CREATED);
    }

    @RequestMapping(value="/deletePlace", method= RequestMethod.PUT)
    public ResponseEntity<Response<DhPlace>> deletePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId,@PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value="/updatePosts", method= RequestMethod.POST)
    public ResponseEntity<Response<DhPosts>> updatePosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value = "/getPosts",method = RequestMethod.GET)
    ResponseEntity<Response<DhPosts>> getPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam String searchValue, @PathVariable String version){
    return null;
    }

    @RequestMapping(value = "/getPaginatedPosts",method = RequestMethod.GET)
    ResponseEntity<Response<DhPosts>> getPaginatedPosts(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam(defaultValue = "0") int page,@RequestParam (defaultValue = "7") int size, @PathVariable String version){
        return new ResponseEntity<>(postsService.getPaginatedPosts(authentication,httpHeaders,page,size,version), HttpStatus.OK);
    }

    @RequestMapping(value = "/getPaginatedPostsByPlaceId",method = RequestMethod.GET)
    ResponseEntity<Response<DhPosts>> getPaginatedPostsByPlaceId(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam(defaultValue = "0") int page,@RequestParam (defaultValue = "7") int size,@RequestParam String placeId, @PathVariable String version){
        return new ResponseEntity<>(postsService.getPaginatedPostsByPlaceId(authentication,httpHeaders,page,size,placeId,version), HttpStatus.OK);
    }

}
