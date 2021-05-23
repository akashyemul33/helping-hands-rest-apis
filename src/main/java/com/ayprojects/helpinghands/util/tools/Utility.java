package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhNotifications;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LangValueObj;
import com.ayprojects.helpinghands.models.PlaceAvailabilityDetails;
import com.ayprojects.helpinghands.models.UserSettings;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.mongodb.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class Utility {

    @Autowired
    LogService logService;

    public static String getLanguageFromHeader(HttpHeaders headers) {
        String lang = "en";

        String[] languages = AppConstants.SUPPORTED_LANGUAGES;
        List<String> languageList = Arrays.asList(languages);
        if (headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE) != null) {
            lang = headers.get(AppConstants.LABEL_HEADER_APPLANGUAGE).get(0);
            if (isFieldEmpty(lang) || !languageList.contains(lang)) {
                lang = "en";
            }
        } else {
            lang = "en";
        }
        return lang;
    }

    public static boolean isFieldEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getResponseMessage(String msg, String language) {
        try {
            Field f = ResponseMessages.class.getField(language + "_" + msg);
            LOGGER.info("Utility->getResponseMessage()->f.getName() is " + f.getName());
            String s = (String) f.get(new ResponseMessages());
            LOGGER.info("Utility->getResponseMessage()->String got from REsponseMessages is " + s);
            return s;
        } catch (Exception ex) {
            LOGGER.info("Utility->getResponseMessage()->got exception=" + ex.getMessage());
            ex.getMessage();
        }
        return "";
    }

    public static UserSettings getGlobalUserSettings() {
        UserSettings userSettings = new UserSettings(AppConstants.TOTAL_ADD_PLACES_LIMIT, AppConstants.PER_DAY_ADD_PLACES_LIMIT, AppConstants.PER_DAY_ADD_POSTS_LIMIT, AppConstants.PER_PLACE_IMAGES_LIMIT, AppConstants.PER_POST_IMAGES_LIMIT, AppConstants.PER_PLACE_PRODUCTS_LIMIT, false, false, false, false, AppConstants.PER_PRODUCT_DEFAULT_IMAGES_LIMIT, true, true, AppConstants.PER_PRODUCT_MAX_IMAGES_LIMIT);
        userSettings.setNotificationsRequired(true);
        return userSettings;
    }

    public static List<String> checkForEmptyStrings(Map<String, String> validationStrings) {
        List<String> emptyStringsList = new ArrayList<>();
        if (validationStrings == null || validationStrings.size() == 0) return emptyStringsList;

        for (Map.Entry<String, String> entry : validationStrings.entrySet()) {
            if (Utility.isFieldEmpty(entry.getValue())) emptyStringsList.add(entry.getKey());
        }
        return emptyStringsList;
    }

    public static String distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2) {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        double result = c * r;
        if (result > 1) {
            return " " + roundTwoDecimals(result) + " KM away";
        } else {
            return "" + roundTwoDecimals(convertMetres(result)) + " m away";
        }
    }

    public static String[] calculatePlaceOpenCloseMsg(PlaceAvailabilityDetails p, String language) {
        if (p == null) return new String[]{""};
        if (p.isProvide24into7()) {
            return new String[]{ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN_24INTO7), AppConstants.OPEN};
        }
        Calendar now = Calendar.getInstance();
        String currentHourMin = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        LOGGER.info("calculatePlaceOpenCloseMsg->currentHourMin=" + currentHourMin);

        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATE_FORMAT_HOUR_MIN);
        try {
            Date currentTime = format.parse(currentHourMin);
            Date openingTime = format.parse(p.getPlaceOpeningTime());
            Date closingTime = format.parse(p.getPlaceClosingTime());
            Date lunchStartTime = null;
            Date lunchEndTime = null;
            if (!p.getHaveNoLunchHours()) {
                lunchStartTime = format.parse(p.getLunchStartTime());
                lunchEndTime = format.parse(p.getLunchEndTime());
            }

            //if before opening time
            if (currentTime.before(openingTime) || currentTime.after(closingTime)) {
                return new String[]{ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSED_OPENS_AT) + " " + p.getPlaceOpeningTime(), AppConstants.CLOSED};
            } else {

                if ((currentTime.getTime() - closingTime.getTime()) <= AppConstants.HOUR_IN_MILLIS) {
                    return new String[]{ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN_CLOSES_AT) + " " + p.getPlaceClosingTime(), AppConstants.OPEN};
                } else if (p.getHaveNoLunchHours()) {
                    return new String[]{ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_LUNCH_HOURS), AppConstants.OPEN};
                } else if (p.getHaveNoLunchHours() && currentTime.after(lunchStartTime) && currentTime.before(lunchEndTime)) {
                    return new String[]{ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_LUNCH_HOURS), AppConstants.OPEN};
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new String[]{""};
    }

    public static DhPlace calculatePlaceOpenCloseMsgWhenNot24into7(@NonNull DhPlace dhPlace, String language) {
        PlaceAvailabilityDetails p = dhPlace.getPlaceAvailablityDetails();
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        String currentHourMin = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE);
        LOGGER.info("calculatePlaceOpenCloseMsg->currentHourMin=" + currentHourMin);

        SimpleDateFormat format = new SimpleDateFormat(AppConstants.DATE_FORMAT_HOUR_MIN);
        format.setTimeZone(TimeZone.getTimeZone(AppConstants.UTC));
        try {
            Date currentTime = format.parse(currentHourMin);
            Date openingTime = format.parse(p.getPlaceOpeningTime());
            Date closingTime = format.parse(p.getPlaceClosingTime());
            Date lunchStartTime = null;
            Date lunchEndTime = null;
            if (!p.getHaveNoLunchHours()) {
                lunchStartTime = format.parse(p.getLunchStartTime());
                lunchEndTime = format.parse(p.getLunchEndTime());
            }

            if (currentTime.before(openingTime)) {
                LOGGER.info("openingTime->" + openingTime.getTime() + " :" + openingTime);
                LOGGER.info("closingTime->" + closingTime.getTime() + " :" + closingTime);
                LOGGER.info("currentime->" + currentTime.getTime() + " :" + currentTime);
                dhPlace.setPlaceOpen(false);
                if ((openingTime.getTime() - currentTime.getTime()) <= AppConstants.HALF_HOUR_IN_MILLIS) {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSED), AppConstants.MID_DOT_SYMBOL, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPENS_WITHIN_HALF_HOUR)));
                } else {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSED), AppConstants.MID_DOT_SYMBOL,
                            String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPENS_AT_TIME), CalendarOperations.convert24HoursFormatTo12Hours(p.getPlaceOpeningTime()))));
                }
                return dhPlace;
            } else if (currentTime.after(closingTime)) {
                dhPlace.setPlaceOpen(false);
                if (currentTime.getTime() - closingTime.getTime() <= AppConstants.HALF_HOUR_IN_MILLIS) {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSED), AppConstants.MID_DOT_SYMBOL, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_FEW_MINS_AGO)));
                } else {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSED), AppConstants.MID_DOT_SYMBOL,
                            String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPENS_AT_TIME), CalendarOperations.convert24HoursFormatTo12Hours(p.getPlaceOpeningTime()))));
                }
                return dhPlace;
            } else {
                dhPlace.setPlaceOpen(true);
                if (closingTime.getTime() - currentTime.getTime() <= AppConstants.HALF_HOUR_IN_MILLIS) {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN), AppConstants.MID_DOT_SYMBOL,
                            String.format(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_CLOSES_AT_TIME), CalendarOperations.convert24HoursFormatTo12Hours(p.getPlaceClosingTime()))));
                } else if (!p.getHaveNoLunchHours() && currentTime.getTime() >= lunchStartTime.getTime() && currentTime.getTime() <= lunchEndTime.getTime()) {
                    dhPlace.setOpenCloseMsg(String.format("%s %s %s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN), AppConstants.MID_DOT_SYMBOL,
                            ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_LUNCH_HOURS)));
                } else {
                    dhPlace.setOpenCloseMsg(String.format("%s", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_OPEN)));
                }
                return dhPlace;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dhPlace;
    }

    public static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.parseDouble(twoDForm.format(d));
    }

    public static double convertMetres(double km) {
        return km * 1000;
    }

    public static String getMainCategoryName(DhPlaceCategories mc, String language) {
        if (mc == null) return null;
        if (mc.getTranslations() == null) return mc.getDefaultName();

        for (LangValueObj l : mc.getTranslations()) {
            if (l.getLang().equalsIgnoreCase(language)) return l.getValue();
        }
        return mc.getDefaultName();
    }

    public static DhUser getUserDetailsFromId(String userId, MongoTemplate mongoTemplate, boolean firstNameOfUser, boolean lastNameOfUser, boolean userImage) {
        if (Utility.isFieldEmpty(userId))
            throw new NullPointerException("Cannot fetch user details with empty userId");
        if (mongoTemplate == null)
            throw new NullPointerException("Cannot fetch place details with null mongoTemplate object.");

        Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(userId));
        if (firstNameOfUser) query.fields().include(AppConstants.FIRST_NAME);
        if (lastNameOfUser) query.fields().include(AppConstants.LAST_NAME);
        if (userImage) query.fields().include(AppConstants.USER_PROFILE_IMG);
        return mongoTemplate.findOne(query, DhUser.class);
    }

    public static DhPlace getPlaceDetailsFromId(String placeId, MongoTemplate mongoTemplate, boolean placeName, boolean placeCategory) {
        if (Utility.isFieldEmpty(placeId))
            throw new NullPointerException("Cannot fetch place details with empty placeId");
        if (mongoTemplate == null)
            throw new NullPointerException("Cannot fetch place details with null mongoTemplate object.");

        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        if (placeName) query.fields().include(AppConstants.PLACE_NAME);
        if (placeCategory) query.fields().include(AppConstants.PLACE_SUB_CATEGORY_NAME);
        return mongoTemplate.findOne(query, DhPlace.class);
    }

    public static AllCommonUsedAttributes setCommonAttrs(AllCommonUsedAttributes obj, String status) {
        if (obj == null) obj = new AllCommonUsedAttributes();
        obj.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        obj.setCreatedDateTime(CalendarOperations.currentDateTimeInUTC());
        obj.setModifiedDateTime(CalendarOperations.currentDateTimeInUTC());
        obj.setStatus(status);
        return obj;
    }

    public static void sendNotification(String userId, MongoTemplate mongoTemplate, String title, String body, String redirectionContent, String redirectionUrl) {
        Query query = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(userId));
        query.fields().include(AppConstants.KEY_FCM_TOKEN);
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        if (dhUser != null) {
            String fcmToken = dhUser.getFcmToken();

            Message message = Message.builder()
                    .putData("title", title)
                    .putData("redirectionContent", redirectionContent)
                    .putData("redirectionUrl", redirectionUrl)
                    .putData("body", body)
                    .setToken(fcmToken)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
                Utility.insertNotification(userId, title, body, redirectionContent, redirectionUrl, mongoTemplate);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertNotification(String userId, String title, String body, String redirectionContent, String redirectionUrl, MongoTemplate mongoTemplate) {
        DhNotifications dhNotifications = new DhNotifications();
        dhNotifications.setNotificationId(Utility.getUUID());
        dhNotifications.setUserId(userId);
        dhNotifications.setTitle(title);
        dhNotifications.setBody(body);
        dhNotifications.setRedirectionUrl(redirectionUrl);
        dhNotifications.setRedirectionContent(redirectionContent);
        dhNotifications = (DhNotifications) Utility.setCommonAttrs(dhNotifications, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhNotifications, AppConstants.COLLECTION_DH_NOTIFICATIONS);
    }

    public void addLog(String username, String actionMsg) {
        if (logService == null || Utility.isFieldEmpty(username) || Utility.isFieldEmpty(actionMsg)) {
            return;
        }
        logService.addLog(new DhLog(username, actionMsg));
    }


}
