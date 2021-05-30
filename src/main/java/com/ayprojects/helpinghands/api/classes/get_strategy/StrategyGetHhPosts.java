package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        if (keySet.contains(AppConstants.KEY_PAGE)
                && keySet.contains(AppConstants.KEY_SIZE)
                && keySet.contains(AppConstants.KEY_LAT)
                && keySet.contains(AppConstants.KEY_LNG)
        ) {
            try {
                int page = (int) params.get(AppConstants.KEY_PAGE);
                int size = (int) params.get(AppConstants.KEY_SIZE);
                double lat = (double) params.get(AppConstants.KEY_LAT);
                double lng = (double) params.get(AppConstants.KEY_LNG);
                return getPaginatedPosts(language, page, size, lat, lng);
            } catch (Exception e) {
                throw new ServerSideException(ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG));
            }

        }
        throw new ServerSideException("No matching get method found in " + getStrategyName());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetHhPostsStrategy;
    }

    public Response<DhHHPost> getPaginatedPosts(String language, int page, int size, double lat, double lng) {
        Pageable pageable = PageRequest.of(page, size);

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhHHPost> dhHHPosts = mongoTemplate.find(query.with(pageable), DhHHPost.class);

        LOGGER.info("getPaginatedPosts=" + dhHHPosts.size());
        for (DhHHPost d : dhHHPosts) {
            Query queryFindUser = new Query(Criteria.where(AppConstants.KEY_USER_ID).is(d.getUserId()));
            queryFindUser.fields().include(AppConstants.FIRST_NAME);
            queryFindUser.fields().include(AppConstants.LAST_NAME);
            queryFindUser.fields().include(AppConstants.KEY_USER_PFORILE_LOW);
            queryFindUser.fields().include(AppConstants.KEY_USER_PFORILE_HIGH);
            queryFindUser.fields().include(AppConstants.KEY_GENUINE_PERCENTAGE);
            queryFindUser.fields().include(AppConstants.KEY_NUMBER_OF_HH_HELPS);
            queryFindUser.fields().include(AppConstants.KEY_NUMBER_OF_HH_POSTS);
            DhUser dhUser = mongoTemplate.findOne(queryFindUser, DhUser.class);
            if (dhUser != null) {
                d.setUserName(String.format(Locale.US, "%s %s", dhUser.getFirstName(), dhUser.getLastName()));
                d.setProfileImgLow(dhUser.getProfileImgLow());
                d.setProfileImgHigh(dhUser.getProfileImgHigh());
                d.setHhGenuinePercentage(dhUser.getHhGenuinePercentage());
                d.setNumberOfHHHelps(dhUser.getNumberOfHHHelps());
                d.setNumberOfHHPosts(dhUser.getNumberOfHHPosts());
            }
            if (lat != 0 && lng != 0) {
                //calculate distance of place from given lat lng
                if (d.getAddress() != null) {
                    double placeLat = d.getAddress().getLat();
                    double placeLng = d.getAddress().getLng();
                    d.setDistance(Utility.distance(lat, placeLat, lng, placeLng));
                }
            }
        }

        Page<DhHHPost> hhPosts = PageableExecutionUtils.getPage(
                dhHHPosts, pageable,
                () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), DhHHPost.class, AppConstants.COLLECTION_DH_HH_POST));
        LOGGER.info("getPaginatedPosts->hhPosts,total elements=" + hhPosts.getTotalElements());
        return new Response<DhHHPost>(true, 200, "Query successful", hhPosts.getNumberOfElements(), hhPosts.getNumber(), hhPosts.getTotalPages(), (long) hhPosts.getTotalElements(), hhPosts.getContent());
    }

}
