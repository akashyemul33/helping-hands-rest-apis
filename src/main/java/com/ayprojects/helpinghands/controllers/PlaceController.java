package com.ayprojects.helpinghands.controllers;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.PlaceStepEnums;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.api.enums.TypeOfData;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.swagger.annotations.Api;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

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
    ResponseEntity<Response<DhPlace>> getPlaceDetails(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestParam String placeId, @RequestParam String userId, @RequestParam String userName, @PathVariable String version) throws ServerSideException {
        HashMap<String, Object> params = new HashMap<>();
        params.put(AppConstants.KEY_PLACE_ID, placeId);
        params.put(AppConstants.KEY_USER_ID, userId);
        params.put(AppConstants.KEY_USER_NAME, userName);
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

    @PostMapping(value = "/getPlaceOverviewDetails")
    public ResponseEntity<Response<DhPlace>> getPlaceOverviewDetails(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhPlace dhPlace, @RequestParam double lat, @RequestParam double lng, @PathVariable String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);

        if (dhPlace == null) {
            return new ResponseEntity<>(new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0), HttpStatus.EXPECTATION_FAILED);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhPlace.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhPlace.getPlaceId()))
            missingFieldsList.add(AppConstants.PLACE_ID);
        PlaceAvailabilityDetails p = dhPlace.getPlaceAvailablityDetails();
        if (p == null)
            missingFieldsList.add(AppConstants.PLACE_AVAILABLITY_DETAILS);
        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new ResponseEntity<>(new Response<DhPlace>(false, 402, resMsg, new ArrayList<>(), 0), HttpStatus.EXPECTATION_FAILED);
        }

        boolean currentStatus = dhPlace.getCurrentStatus();
        String openCloseMsg = "";
        String distance = "";
        if (!currentStatus && !Utility.isFieldEmpty(dhPlace.getOfflineMsg())) {
            openCloseMsg = dhPlace.getOfflineMsg();
        } else {
            if (p.isProvide24into7()) {
                if (!p.getHaveNoLunchHours()) {
                    openCloseMsg = String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN), AppConstants.MID_DOT_SYMBOL, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_LUNCH_HOURS));
                } else
                    openCloseMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN);
                dhPlace.setPlaceOpen(true);
                dhPlace.setOpenCloseMsg(openCloseMsg);
            } else {
                Utility.calculatePlaceOpenCloseMsgWhenNot24into7(dhPlace, language);
            }
        }

        if (lat != 0 && lng != 0) {
            if (dhPlace.getPlaceAddress() != null) {
                double placeLat = dhPlace.getPlaceAddress().getLat();
                double placeLng = dhPlace.getPlaceAddress().getLng();
                distance = Utility.distance(lat, placeLat, lng, placeLng);
                dhPlace.setDistance(distance);
            }
        }

        return new ResponseEntity<>(new Response<DhPlace>(true, 200, "Query successful", Collections.singletonList(dhPlace)), HttpStatus.OK);
    }
}
