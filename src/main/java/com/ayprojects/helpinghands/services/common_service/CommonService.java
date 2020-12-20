package com.ayprojects.helpinghands.services.common_service;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    boolean checkUserExistence(String  userId);
    DhUser getUserWithUserId(String  userId);
    boolean checkPlaceExistence(String  placeId);
    DhPlace getPlaceWithPlaceId(String  placeId);

}
