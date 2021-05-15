package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;
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
public class StrategyAddViewsApi implements StrategyAddBehaviour<DhViews> {
    @Autowired
    UserDao userDao;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhViews> add(String language, DhViews dhViews) throws ServerSideException {
        dhViews.setViewId(Utility.getUUID());
        Response<DhViews> validationResponse = validateAddViews(language, dhViews);
        if (!validationResponse.getStatus())
            return validationResponse;

        dhViews = (DhViews) ApiOperations.setCommonAttrs(dhViews, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhViews, AppConstants.COLLECTION_DH_VIEWS);
        validationResponse.setLogActionMsg("New view for [" + dhViews.getContentType() + "] has been added by user [" + dhViews.getAddedBy() + "].");

        //here content can be place,requirement & post
        String contentIdToSearch = "";
        switch (dhViews.getContentType().toUpperCase()) {
            case AppConstants.PLACE:
                contentIdToSearch = AppConstants.PLACE_ID;
                Query queryFindPlaceWithId = new Query(Criteria.where(contentIdToSearch).is(dhViews.getContentId()));
                DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
                if (queriedDhPlace == null)
                    throw new ServerSideException("Unable to add rating into places collection, seems like no place found with given id");
                Update updatePlace = new Update();
                updatePlace.set(AppConstants.NUMBER_OF_VIEWS, queriedDhPlace.getNumberOfViews() + 1);
                updatePlace.push(AppConstants.VIEW_IDS, dhViews.getViewId());
                if (queriedDhPlace.getTopViews() != null && queriedDhPlace.getTopViews().size() == AppConstants.LIMIT_VIEWS_IN_PLACES) {
                    Update updatePopTopView = new Update();
                    updatePopTopView.pop(AppConstants.TOP_VIEWS, Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopView, DhPlace.class);
                }
                updatePlace.push(AppConstants.TOP_VIEWS, dhViews);
                updatePlace.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
                break;
            case AppConstants.POST:
                contentIdToSearch = AppConstants.POST_ID;
                Query queryFindPostsWithId = new Query(Criteria.where(contentIdToSearch).is(dhViews.getContentId()));
                DhPosts queriedDhPosts = mongoTemplate.findOne(queryFindPostsWithId, DhPosts.class);
                if (queriedDhPosts == null)
                    throw new ServerSideException("Unable to add view into posts collection, seems like no post found with given id");
                Update updatePost = new Update();
                updatePost.set(AppConstants.NUMBER_OF_VIEWS, queriedDhPosts.getNumberOfViews() + 1);
                updatePost.push(AppConstants.VIEW_IDS, dhViews.getViewId());
                if (queriedDhPosts.getTopViews() != null && queriedDhPosts.getTopViews().size() == AppConstants.LIMIT_VIEWS_IN_POSTS) {
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_VIEWS, Update.Position.FIRST);
                    mongoTemplate.updateFirst(queryFindPostsWithId, updatePopTopRating, DhPlace.class);
                }
                updatePost.push(AppConstants.TOP_VIEWS, dhViews);
                updatePost.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPostsWithId, updatePost, DhPosts.class);
                break;
            case AppConstants.REQUIREMENT:
                contentIdToSearch = AppConstants.REQUIREMENT_ID;
                Query queryFindRequirementWithId = new Query(Criteria.where(contentIdToSearch).is(dhViews.getContentId()));
                DhRequirements queriedDhRequirements = mongoTemplate.findOne(queryFindRequirementWithId, DhRequirements.class);
                if (queriedDhRequirements == null)
                    throw new ServerSideException("Unable to add view into Requirements collection, seems like no requirement found with given id");
                Update updateRequirement = new Update();
                updateRequirement.set(AppConstants.NUMBER_OF_VIEWS, queriedDhRequirements.getNumberOfViews() + 1);
                updateRequirement.push(AppConstants.VIEW_IDS, dhViews.getViewId());
                if (queriedDhRequirements.getTopViews() != null && queriedDhRequirements.getTopViews().size() == AppConstants.LIMIT_VIEWS_IN_REQUIREMENTS) {
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_VIEWS, Update.Position.FIRST);
                    mongoTemplate.updateFirst(queryFindRequirementWithId, updatePopTopRating, DhRequirements.class);
                }
                updateRequirement.push(AppConstants.TOP_VIEWS, dhViews);
                updateRequirement.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindRequirementWithId, updateRequirement, DhRequirements.class);
                break;
        }
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, language), new ArrayList<>(), 1);
    }

    @Override
    public Response<DhViews> add(String language, DhViews obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddViewsStrategy;
    }

    public Response<DhViews> validateAddViews(String language, DhViews dhViews) {
        if (dhViews == null)
            return new Response<DhViews>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhViews.getAddedBy()))
            missingFieldsList.add(AppConstants.ADDED_BY);
        if (Utility.isFieldEmpty(dhViews.getContentType()))
            missingFieldsList.add(AppConstants.CONTENT_TYPE);
        if (Utility.isFieldEmpty(dhViews.getContentId()))
            missingFieldsList.add(AppConstants.CONTENT_ID);

        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, language), new ArrayList<>(), 1);
    }


}
