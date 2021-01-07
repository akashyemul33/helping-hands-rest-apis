package com.ayprojects.helpinghands.services.common_service;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhNewUserSupport;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    //returns true if user exist
    @Override
    public boolean checkUserExistence(String userId) {
        if (Utility.isFieldEmpty(userId)) {
            return false;
        }

        Query query = new Query(Criteria.where(AppConstants.USER_ID).is(userId));
        query.fields().include(AppConstants.OBJECT_ID);//include only _id which is indexed, as here we only need existence of user
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        return dhUser != null;
    }

    @Override
    public DhUser getUserWithUserId(String userId) {
        if (Utility.isFieldEmpty(userId)) {
            return null;
        }

        Query query = new Query(Criteria.where(AppConstants.USER_ID).is(userId));
        return mongoTemplate.findOne(query, DhUser.class);
    }

    //returns true if place exist
    @Override
    public boolean checkPlaceExistence(String placeId) {
        if (Utility.isFieldEmpty(placeId)) {
            return false;
        }

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        query.fields().include(AppConstants.OBJECT_ID);//include only _id which is indexed, as here we only need existence of place
        DhPlace dhPlace = mongoTemplate.findOne(query, DhPlace.class);
        return dhPlace != null;
    }

    @Override
    public DhPlace getPlaceWithPlaceId(String placeId) {
        if (Utility.isFieldEmpty(placeId)) {
            return null;
        }

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        return mongoTemplate.findOne(query, DhPlace.class);
    }

    @Override
    public boolean updateUserWithSessionAndOtherDetails(String newFcmToken, String lastLogoutTime, DhUser dhUser) {
        try {
            Update userUpdate = getUpdateObjForUser(newFcmToken, lastLogoutTime, dhUser);
            Query queryFindUserWithId = new Query(Criteria.where(AppConstants.USER_ID).is(dhUser.getUserId()));
            mongoTemplate.updateFirst(queryFindUserWithId, userUpdate, DhUser.class);
            utility.addLog(dhUser.getMobileNumber(), "Replaced fcm token & updated session details.");
            return true;
        } catch (Exception e) {
            LOGGER.info("updateUserWithSessionAndOtherDetails->catch=>" + e.getMessage());
            return false;
        }
    }

    public Update getUpdateObjForUser(String newFcmToken, String lastLogoutTime, DhUser dhUser) {
        if (Utility.isFieldEmpty(newFcmToken) || dhUser == null || Utility.isFieldEmpty(dhUser.getUserId()))
            throw new IllegalArgumentException("FcmToken,DhUser & userId inside DhUser must not be empty !");

        CalendarOperations calendarOperations = new CalendarOperations();
        Update userUpdate = new Update();
        if (!Utility.isFieldEmpty(lastLogoutTime) && calendarOperations.verifyTimeFollowsCorrectFormat(lastLogoutTime)) {
            userUpdate.set(AppConstants.LAST_LOGIN_TIME, dhUser.getLogInTime());
            userUpdate.set(AppConstants.KEY_LAST_LOGOUT_TIME, lastLogoutTime);
        }
        userUpdate.set(AppConstants.TRIED_TO_LOGIN_TIME, calendarOperations.currentDateTimeInUTC());
        userUpdate.set(AppConstants.LOGIN_TIME, calendarOperations.currentDateTimeInUTC());

        String existingFcmToken = dhUser.getFcmToken();
        if (!newFcmToken.equals(existingFcmToken)) {
            userUpdate.set(AppConstants.KEY_FCM_TOKEN, newFcmToken);
        }

        userUpdate.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        return userUpdate;
    }


    @Override
    public boolean insertIntoNewUserSupport(String fcmToken, String mobileNumber, String countryCode) {
        if (Utility.isFieldEmpty(fcmToken) || Utility.isFieldEmpty(mobileNumber)) return false;
        Criteria criteria = Criteria.where(AppConstants.KEY_MOBILE).is(mobileNumber).
                andOperator(Criteria.where(AppConstants.KEY_FCM_TOKEN).is(fcmToken));
        Query queryFindByMobileOrFcm = new Query(criteria);
        DhNewUserSupport dhNewUserSupport = mongoTemplate.findOne(queryFindByMobileOrFcm, DhNewUserSupport.class);
        if (dhNewUserSupport == null) {
            DhNewUserSupport d = new DhNewUserSupport(fcmToken, mobileNumber, countryCode);
            d = (DhNewUserSupport) Utility.setCommonAttrs(d, AppConstants.STATUS_NOTREGISTERED);
            mongoTemplate.save(d);
        } else {
            CalendarOperations calendarOperations = new CalendarOperations();
            Update update = new Update();
            update.set(AppConstants.KEY_FCM_TOKEN, fcmToken);
            update.set(AppConstants.KEY_MOBILE, mobileNumber);
            update.set(AppConstants.KEY_COUNTRY_CODE, countryCode);
            update.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
            mongoTemplate.upsert(queryFindByMobileOrFcm, update, DhNewUserSupport.class);
        }

        return true;
    }

    @Override
    public boolean markRegisteredInNewUserSupport(DhUser dhUser) {
        if (dhUser == null || Utility.isFieldEmpty(dhUser.getUserId())) return false;
        CalendarOperations calendarOperations = new CalendarOperations();
        Update update = new Update();
        update.set(AppConstants.USER_ID, dhUser.getUserId());
        update.set(AppConstants.STATUS, AppConstants.STATUS_REGISTERED);
        update.set(AppConstants.MODIFIED_DATE_TIME, calendarOperations.currentDateTimeInUTC());
        Criteria criteria = Criteria.where(AppConstants.KEY_MOBILE).is(dhUser.getMobileNumber()).
                orOperator(Criteria.where(AppConstants.KEY_FCM_TOKEN).is(dhUser.getFcmToken()));
        Query queryFindByMobileOrFcm = new Query(criteria);
        mongoTemplate.updateMulti(queryFindByMobileOrFcm, update, DhNewUserSupport.class);
        return true;
    }
}
