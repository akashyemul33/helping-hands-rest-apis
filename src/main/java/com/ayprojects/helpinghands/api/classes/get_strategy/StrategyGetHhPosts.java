package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.ContentType;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhComments;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhHhHelpedUsers;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyGetHhPosts implements StrategyGetBehaviour<DhHHPost> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhHHPost> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        int page = (int) params.get(AppConstants.KEY_PAGE);
        int size = (int) params.get(AppConstants.KEY_SIZE);
        if (keySet.contains(AppConstants.KEY_USER_ID)) {
            String userId = (String) params.get(AppConstants.KEY_USER_ID);
            return getPaginatedPostsByUserId(language, page, size, userId);
        } else if (
                keySet.contains(AppConstants.KEY_LAT)
                        && keySet.contains(AppConstants.KEY_LNG)
        ) {
            double lat = (double) params.get(AppConstants.KEY_LAT);
            double lng = (double) params.get(AppConstants.KEY_LNG);
            return getPaginatedPosts(language, page, size, lat, lng);
        }
        throw new ServerSideException("No matching get method found in " + getStrategyName());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhPostsStrategy;
    }

    public Response<DhHHPost> getPaginatedPostsByUserId(String language, int page, int size, String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where(AppConstants.KEY_USER_ID).is(userId);
        criteria.orOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_HELPED, "i"));
        criteria.orOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(criteria);
        return returnPaginatedPosts(0, 0, query, page, size);
    }

    public Response<DhHHPost> getPaginatedPosts(String language, int page, int size, double lat, double lng) {

        Query query = new Query();
        Criteria criteria = Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_HELPED, "i");
        criteria.orOperator(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(criteria);
        return returnPaginatedPosts(lat, lng, query, page, size);
    }

    private Response<DhHHPost> returnPaginatedPosts(double lat, double lng, Query query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<DhHHPost> dhHHPosts = mongoTemplate.find(query.with(pageable), DhHHPost.class);
        query.with(Sort.by(Sort.Direction.ASC, AppConstants.CREATED_DATETIME));
        for (DhHHPost d : dhHHPosts) {
            Query queryFindUser = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(d.getUserId()));
            queryFindUser.fields().include(AppConstants.FIRST_NAME);
            queryFindUser.fields().include(AppConstants.LAST_NAME);
            queryFindUser.fields().include(AppConstants.KEY_USER_PFORILE_LOW);
            queryFindUser.fields().include(AppConstants.KEY_USER_PFORILE_HIGH);
            queryFindUser.fields().include(AppConstants.KEY_GENUINE_PERCENTAGE);
            queryFindUser.fields().include(AppConstants.KEY_NUMBER_OF_HH_HELPS);
            queryFindUser.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
            if (d.isPostCommentsOnOff()) {
                queryFindUser.fields().include(AppConstants.KEY_USER_SETTINGS);
                queryFindUser.fields().include(AppConstants.KEY_USER_SETTINGS_ENABLED);
            }
            DhUser dhUser = mongoTemplate.findOne(queryFindUser, DhUser.class);
            if (dhUser != null) {
                d.setUserName(String.format(Locale.US, "%s %s", dhUser.getFirstName(), dhUser.getLastName()));
                d.setProfileImgLow(dhUser.getProfileImgLow());
                d.setProfileImgHigh(dhUser.getProfileImgHigh());
                d.setHhGenuinePercentage(dhUser.getHhGenuinePercentage());
                d.setNumberOfHHHelps(dhUser.getNumberOfHHHelps());
                d.setNumberOfHHPosts(dhUser.getNumberOfHHPosts());

                if (dhUser.getUserSettingEnabled() && dhUser.getUserSettings() != null)
                    d.setPostCommentsOnOff(dhUser.getUserSettings().isHhPostCommentsOnOff());
            }
            if (lat != 0 && lng != 0) {
                //calculate distance of place from given lat lng
                if (d.getAddress() != null) {
                    double placeLat = d.getAddress().getLat();
                    double placeLng = d.getAddress().getLng();
                    d.setDistance(Utility.distance(lat, placeLat, lng, placeLng));
                }
            }

            Query queryToFindHelpedUserDetails = new Query();
            queryToFindHelpedUserDetails.addCriteria(Criteria.where(AppConstants.KEY_HH_POST_ID).is(d.getPostId()));
            queryToFindHelpedUserDetails.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            List<DhHhHelpedUsers> helpedUsersList = mongoTemplate.find(queryToFindHelpedUserDetails, DhHhHelpedUsers.class);
            d.setHelpedUsers(helpedUsersList);

            Query queryToFindComments = new Query();
            queryToFindComments.addCriteria(Criteria.where(AppConstants.KEY_CONTENT_ID).is(d.getPostId()));
            queryToFindComments.addCriteria(Criteria.where(AppConstants.KEY_CONTENT_TYPE).is(ContentType.CONTENT_ADD_HH_POST_COMMENT));
            queryToFindComments.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            List<DhComments> commentsList = mongoTemplate.find(queryToFindComments, DhComments.class);
            d.setDhComments(commentsList);
        }

        Page<DhHHPost> hhPosts = PageableExecutionUtils.getPage(
                dhHHPosts, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), DhHHPost.class, AppConstants.COLLECTION_DH_HH_POST));
        LOGGER.info("getPaginatedPosts->hhPosts,total elements=" + hhPosts.getTotalElements());
        return new Response<DhHHPost>(true, 200, "Query successful", hhPosts.getNumberOfElements(), hhPosts.getNumber(), hhPosts.getTotalPages(), (long) hhPosts.getTotalElements(), hhPosts.getContent());
    }

}
