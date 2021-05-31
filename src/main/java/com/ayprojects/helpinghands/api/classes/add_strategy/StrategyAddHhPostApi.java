package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddHhPostApi implements StrategyAddBehaviour<DhHHPost> {

    @Autowired
    CommonService commonService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhHHPost> add(String language, DhHHPost dhHHPost) throws ServerSideException {
        dhHHPost = setPostIdIfNotExists(dhHHPost);
        Response<DhHHPost> validationResponse = validateAddHhPost(language, dhHHPost);
        if (!validationResponse.getStatus())
            return validationResponse;

        //validating user against user ids intentionally, later in this code used this quried objects to update dhUser
        Query findPostAddedUserQuery = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(dhHHPost.getUserId()).andOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i")));
        findPostAddedUserQuery.fields().include(AppConstants.KEY_GENUINE_PERCENTAGE);
        findPostAddedUserQuery.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
        DhUser queriedPostAddedDhUser = mongoTemplate.findOne(findPostAddedUserQuery, DhUser.class);
        if (queriedPostAddedDhUser == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);

        dhHHPost = (DhHHPost) Utility.setCommonAttrs(dhHHPost, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhHHPost, AppConstants.COLLECTION_DH_HH_POST);

        //Update helped user details
        Update updatePostsCountOfUser = new Update();
        updatePostsCountOfUser.set(AppConstants.KEY_NUMBER_OF_HH_POSTS, queriedPostAddedDhUser.getNumberOfHHPosts() + 1);
        updatePostsCountOfUser.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        mongoTemplate.updateFirst(findPostAddedUserQuery, updatePostsCountOfUser, DhUser.class);
        return validationResponse;
    }

    @Override
    public Response<DhHHPost> add(String language, DhHHPost obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    public DhHHPost setPostIdIfNotExists(DhHHPost dhHHPost) {
        if (dhHHPost != null && Utility.isFieldEmpty(dhHHPost.getPostId())) {
            dhHHPost.setPostId(Utility.getUUID());
        }
        return dhHHPost;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddHHPostStrategy;
    }

    public Response<DhHHPost> validateAddHhPost(String language, DhHHPost dhHHPost) {
        if (dhHHPost == null)
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhHHPost.getUserId()))
            missingFieldsList.add(AppConstants.KEY_USER_ID);
        if (Utility.isFieldEmpty(dhHHPost.getPostId()))
            missingFieldsList.add(AppConstants.KEY_HH_POST_ID);
        if (Utility.isFieldEmpty(dhHHPost.getCategoryId()))
            missingFieldsList.add(AppConstants.KEY_HH_CATEGORY_ID);
        if (Utility.isFieldEmpty(dhHHPost.getPriorityId()))
            missingFieldsList.add(AppConstants.KEY_HH_PRIORITY_ID);
        if (Utility.isFieldEmpty(dhHHPost.getCategoryName()))
            missingFieldsList.add(AppConstants.KEY_HH_CATEGORY_NAME);
        if (Utility.isFieldEmpty(dhHHPost.getPriorityName()))
            missingFieldsList.add(AppConstants.KEY_HH_PRIORITY_NAME);

        if (dhHHPost.getAddress() == null) missingFieldsList.add(AppConstants.KEY_HH_ADDRESS);
        else {
            if (dhHHPost.getAddress().getLat() == 0)
                missingFieldsList.add(AppConstants.LATTITUDE);
            if (dhHHPost.getAddress().getLng() == 0)
                missingFieldsList.add(AppConstants.LONGITUDE);
            if (Utility.isFieldEmpty(dhHHPost.getAddress().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }

        if (missingFieldsList.size() > 0) {
            String resMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            LOGGER.info("missingfields=>" + resMsg);
            return new Response<DhHHPost>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        return new Response<DhHHPost>(true, 201,ResponseMsgFactory.getResponseMsg(language,AppConstants.RESPONSEMESSAGE_HH_ADD_POST_MSG),ResponseMsgFactory.getResponseMsg(language,AppConstants.RESPONSEMESSAGE_HH_ADD_POST_BODY), new ArrayList<>(), 0);
    }
}
