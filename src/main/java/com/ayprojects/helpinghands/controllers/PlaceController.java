package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.PlaceStepEnums;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.api.enums.TypeOfData;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
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

@Api(value = "Place API's", description = "CRUD for places")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/places")
public class PlaceController {

    @Autowired
    ApiOperations<DhPlace> apiOperations;

    @PostMapping(value = "/addPlace")
    public ResponseEntity<Response<DhPlace>> addPlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhPlace, StrategyName.AddPlaceStrategy, version), HttpStatus.CREATED);
    }

    @PutMapping(value = "/deletePlace")
    public ResponseEntity<Response<DhPlace>> deletePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId, @PathVariable String version) throws ServerSideException {
        return null;
    }

    @PutMapping(value = "/updatePlace")
    public ResponseEntity<Response<DhPlace>> updatePlace(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @RequestParam PlaceStepEnums placeStepEnum, @RequestParam int productPos, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PLACE_STEP_ENUM, placeStepEnum);
        params.put(AppConstants.PRODUCT_POS, productPos);
        Response<DhPlace> response = apiOperations.update(authentication, httpHeaders, params, dhPlace, StrategyName.UpdatePlaceStrategy, version);
        if (response.getStatus()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping(value = "/getPlaces")
    ResponseEntity<Response<DhPlace>> getPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String searchValue, @PathVariable String version) {
        return null;
    }

    @GetMapping(value = "/getPlaceDetails")
    ResponseEntity<Response<DhPlace>> getPlaceDetails(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PLACE_ID, placeId);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceStrategy, params, version), HttpStatus.OK);
    }

    @GetMapping(value = "/getPaginatedPlaces")
    ResponseEntity<Response<DhPlace>> getPaginatedPlaces(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size, @RequestParam double lat, @RequestParam double lng, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PAGE, page);
        params.put(AppConstants.KEY_SIZE, size);
        params.put(AppConstants.KEY_LAT, lat);
        params.put(AppConstants.KEY_LNG, lng);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceStrategy, params, version), HttpStatus.OK);
    }

    @GetMapping(value = "/getPlaceWithUserId")
    ResponseEntity<Response<DhPlace>> getPlaceWithUserId(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version, @RequestParam String userId, @RequestParam TypeOfData typeOfData) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_USER_ID, userId);
        params.put(AppConstants.KEY_TYPE_OF_DATA, typeOfData);
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetPlaceStrategy, params, version), HttpStatus.OK);
    }
}
