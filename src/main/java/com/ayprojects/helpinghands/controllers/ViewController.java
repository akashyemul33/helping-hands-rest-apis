package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.views.ViewService;

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

import io.swagger.annotations.Api;

@Api(value = "Views API's",description = "CRUD for Views")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/views")
public class ViewController {

    @Autowired
    ViewService viewService;

    @RequestMapping(value="/addViews", method= RequestMethod.POST)
    public ResponseEntity<Response<DhViews>> addViews(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhViews dhViews, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(viewService.addViews(authentication,httpHeaders, dhViews,version), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getPaginatedViews",method = RequestMethod.GET)
    ResponseEntity<Response<DhViews>> getPaginatedViews(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam (defaultValue = "7") int size, @RequestParam String contentId, @RequestParam String contentType, @PathVariable String version){
        return new ResponseEntity<>(viewService.getPaginatedViews(authentication,httpHeaders,contentId,contentType, AppConstants.STATUS_ACTIVE,page,size,version), HttpStatus.OK);
    }
}
