package com.ayprojects.helpinghands.services.common_service;

import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.mongodb.lang.NonNull;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public interface CommonService {
    boolean checkUserExistence(String  userId);
    DhUser getUserWithUserId(String  userId);
    boolean checkPlaceExistence(String  placeId);
    DhPlace getPlaceWithPlaceId(String  placeId);
    boolean updateUserWithSessionAndOtherDetails(String newFcmToken, String lastLogoutTime, DhUser dhUser);
    boolean insertIntoNewUserSupport(String fcmToken, String mobileNumber, String countryCode);
    boolean markRegisteredInNewUserSupport(DhUser dhUser);

}
