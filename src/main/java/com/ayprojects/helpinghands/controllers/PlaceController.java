package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.products.ProductsService;

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

@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/places")
public class PlaceController {

    @RequestMapping(value="/addPlace", method= RequestMethod.POST)
    public ResponseEntity<Response<DhPlace>> addPlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
    return null;
    }

    @RequestMapping(value="/deletePlace", method= RequestMethod.PUT)
    public ResponseEntity<Response<DhPlace>> deletePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId,@PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value="/updatePlace", method= RequestMethod.POST)
    public ResponseEntity<Response<DhPlace>> updatePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @RequestMapping(value = "/getPlaces",method = RequestMethod.GET)
    ResponseEntity<Response<DhProduct>> getPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication,@RequestParam String searchValue, @PathVariable String version){
    return null;
    }



}
