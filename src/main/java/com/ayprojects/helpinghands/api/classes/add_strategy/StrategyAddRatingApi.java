package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Notifications;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.firebase.FirebaseSetup;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddRatingApi implements StrategyAddBehaviour<DhRatingAndComments> {
    @Autowired
    FirebaseSetup firebaseSetup;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Response<DhRatingAndComments> add(String language, DhRatingAndComments obj) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhRatingAndComments> add(String language, DhRatingAndComments obj, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_CONTENT_USER_ID)) {
            String contentUserId = (String) params.get(AppConstants.KEY_CONTENT_USER_ID);
            String contentName = (String) params.get(AppConstants.KEY_CONTENT_NAME);
            String userName = (String) params.get(AppConstants.KEY_USER_NAME);
            Response<DhRatingAndComments> validationResponse = validateAddRatingAndComments(language, obj, contentUserId);

            if (!validationResponse.getStatus())
                return validationResponse;
            mongoTemplate.save(obj, AppConstants.COLLECTION_DH_RATING_COMMENT);
            sendNotificationToContentOwner(language, obj.getContentType(), obj.getAddedBy(), contentUserId, contentName, userName);
            return validationResponse;
        }
        return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
    }

    private void sendNotificationToContentOwner(String lang, ContentType contentType, String addedBy, String contentUserId, String contentName, String userName) {
        Query query = new Query(Criteria.where(AppConstants.ADDED_BY).is(addedBy));
        query.fields().include(AppConstants.KEY_FCM_TOKEN);
        DhUser dhUser = mongoTemplate.findOne(query, DhUser.class);
        if (dhUser != null) {
            String fcmToken = dhUser.getFcmToken();
            LOGGER.info("sendNotificationToContentOwner:fcmToken->" + fcmToken);
            String body = userName + " has rated your " + contentType + " " + contentName;
            String title = ResponseMsgFactory.getResponseMsg(lang, AppConstants.RESPONSEMESSAGE_RATING_ADDED_TITLE);
            Message message = Message.builder()
                    .putData("title", title)
                    .putData("body", body)
                    .setToken(fcmToken)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
                Notifications notifications = new Notifications();
                notifications.setNotificationId(Utility.getUUID());
                notifications.setTitle(title);
                notifications.setBody(body);
                notifications.setRedirectionUrl(" ");
                notifications.setRedirectionContent(contentUserId);
                mongoTemplate.save(notifications, AppConstants.COLLECTION_NOTIFICATION);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        }

    }

    private Response<DhRatingAndComments> validateAddRatingAndComments(String language, DhRatingAndComments obj, String contentUserId) {
        if (obj == null) {
            return new Response<DhRatingAndComments>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(obj.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(obj.getContentId()))
            missingFieldsList.add(AppConstants.CONTENT_ID);
        if (Utility.isFieldEmpty(contentUserId))
            missingFieldsList.add(AppConstants.KEY_CONTENT_USER_ID);
        if (obj.getContentType() == null)
            missingFieldsList.add(AppConstants.CONTENT_TYPE);

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhRatingAndComments>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<DhRatingAndComments>(true, 201, "Validated", new ArrayList<>(), 0);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddRatingStrategy;
    }
}
