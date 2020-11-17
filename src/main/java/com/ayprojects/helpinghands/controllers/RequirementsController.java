package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.posts.PostsService;
import com.ayprojects.helpinghands.services.requirements.RequirementsService;

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

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/requirements")
public class RequirementsController {

    @Autowired
    RequirementsService requirementsService;

    @PostMapping(value="/addRequirements", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<DhRequirements>> addRequirements(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(value = "requirementBody") String requirementBody, @RequestPart(value="requirementImages",required = false) MultipartFile[] requirementImages, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(requirementsService.addRequirements(httpHeaders,authentication,requirementBody,requirementImages,version), HttpStatus.CREATED);
    }

    @RequestMapping(value="/deleteRequirement", method= RequestMethod.PUT)
    public ResponseEntity<Response<DhRequirements>> deleteRequirement(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String requirementId,@PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value="/updateRequirement", method= RequestMethod.POST)
    public ResponseEntity<Response<DhRequirements>> updateRequirement(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhRequirements dhRequirements, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value = "/getAllRequirements",method = RequestMethod.GET)
    ResponseEntity<Response<DhRequirements>> getAllRequirements(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam String searchValue, @PathVariable String version){
    return null;
    }

    @RequestMapping(value = "/getPaginatedRequirements",method = RequestMethod.GET)
    ResponseEntity<Response<DhRequirements>> getPaginatedRequirements(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam(defaultValue = "0") int page,@RequestParam (defaultValue = "7") int size, @PathVariable String version){
        return new ResponseEntity<>(requirementsService.getPaginatedRequirements(httpHeaders,authentication,page,size,version), HttpStatus.OK);
    }

}
