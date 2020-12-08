package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.place.PlaceService;
import com.ayprojects.helpinghands.services.products.ProductsService;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.Api;

@Api(value = "Place API's",description = "CRUD for places")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/places")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @PostMapping(value="/addPlace")
    public ResponseEntity<Response<DhPlace>> addPlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException{
            return new ResponseEntity<>(placeService.addPlace(authentication,httpHeaders,dhPlace,version), HttpStatus.CREATED);
    }

    @PutMapping(value="/deletePlace")
    public ResponseEntity<Response<DhPlace>> deletePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId,@PathVariable String version) throws ServerSideException {
        return null;
    }

    @PostMapping(value="/updatePlace")
    public ResponseEntity<Response<DhPlace>> updatePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @GetMapping(value = "/getPlaces")
    ResponseEntity<Response<DhProduct>> getPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam String searchValue, @PathVariable String version){
    return null;
    }

    @GetMapping(value = "/getPaginatedPlaces")
    ResponseEntity<Response<DhPlace>> getPaginatedPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam(defaultValue = "0") int page,@RequestParam (defaultValue = "7") int size,@RequestParam double lat,@RequestParam double lng, @PathVariable String version){
        return new ResponseEntity<>(placeService.getPaginatedPlaces(authentication,httpHeaders,page,size,version,lat,lng), HttpStatus.OK);
    }

}
