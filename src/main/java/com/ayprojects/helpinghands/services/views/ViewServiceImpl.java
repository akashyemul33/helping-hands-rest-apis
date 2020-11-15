package com.ayprojects.helpinghands.services.views;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Utility utility;

    @Override
    public Response<DhViews> addViews(Authentication authentication, HttpHeaders httpHeaders, DhViews dhViews, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ViewServiceImpl->addViews : language=" + language+" contentId="+dhViews.getContentId()+" contentType="+dhViews.getContentType());

        if (dhViews == null) {
            return new Response<DhViews>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(dhViews.getAddedBy())) missingFieldsList.add("AddedBy");
        if (Utility.isFieldEmpty(dhViews.getContentType())) missingFieldsList.add("ContentType");
        if (Utility.isFieldEmpty(dhViews.getContentId())) missingFieldsList.add("ContentId");
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        dhViews.setViewId(Utility.getUUID());
        dhViews.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhViews.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhViews.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhViews.setStatus(AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhViews, AppConstants.COLLECTION_DH_VIEWS);
        utility.addLog(authentication.getName(), "New view for [" + dhViews.getContentType() + "] has been added by user [" + dhViews.getAddedBy() + "].");

        //here content can be place,requirement & post
        String contentIdToSearch = "";
        switch (dhViews.getContentType().toUpperCase()) {
            case AppConstants.PLACE:
                contentIdToSearch = AppConstants.PLACE_ID;
                Query queryFindPlaceWithId = new Query(Criteria.where(contentIdToSearch).is(dhViews.getContentId()));
                DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
                if(queriedDhPlace == null)throw new ServerSideException("Unable to add rating into places collection");
                Update updatePlace = new Update();
                updatePlace.set(AppConstants.NUMBER_OF_VIEWS, queriedDhPlace.getNumberOfViews() + 1);
                updatePlace.push(AppConstants.VIEW_IDS, dhViews.getViewId());
                if (queriedDhPlace.getTopViews() != null && queriedDhPlace.getTopViews().size() == 10) {
                    Update updatePopTopView = new Update();
                    updatePopTopView.pop(AppConstants.TOP_VIEWS, Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopView, DhPlace.class);
                }
                updatePlace.push(AppConstants.TOP_VIEWS, dhViews);
                updatePlace.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
                break;
            case AppConstants.POST:
                contentIdToSearch = AppConstants.POST_ID;
                Query queryFindPostsWithId = new Query(Criteria.where(contentIdToSearch).is(dhViews.getContentId()));
                DhPosts queriedDhPosts = mongoTemplate.findOne(queryFindPostsWithId, DhPosts.class);
                if (queriedDhPosts == null)
                    throw new ServerSideException("Unable to add view into posts collection");
                Update updatePost = new Update();
                updatePost.set(AppConstants.NUMBER_OF_VIEWS, queriedDhPosts.getNumberOfViews() + 1);
                updatePost.push(AppConstants.VIEW_IDS, dhViews.getViewId());
                if (queriedDhPosts.getTopViews() != null && queriedDhPosts.getTopViews().size() == 10) {
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_VIEWS, Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindPostsWithId, updatePopTopRating, DhPlace.class);
                }
                updatePost.push(AppConstants.TOP_VIEWS, dhViews);
                updatePost.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPostsWithId, updatePost, DhPosts.class);
                break;
            case AppConstants.REQUIREMENT:
                contentIdToSearch = AppConstants.REQUIREMENT_ID;
                break;
        }
        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, language), new ArrayList<>(), 1);
    }

    @Override
    public Response<DhViews> getPaginatedViews(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RatingCommentsServiceImpl->getPaginatedRatingAndComments : language=" + language);
        LOGGER.info("RatingCommentsServiceImpl->getPaginatedRatingAndComments : contentId=" + contentId + " contentType=" + contentType + " satus=" + status + " page=" + page + " size=" + size);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(contentId)) missingFieldsList.add("ContentId");
        if (Utility.isFieldEmpty(contentType)) missingFieldsList.add("ContentType");
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        Pageable pageable = PageRequest.of(page, size);
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.CONTENT_ID).regex(contentId, "i");
        criteria.and(AppConstants.CONTENT_TYPE).regex(contentType, "i");
        criteria.and(AppConstants.STATUS).regex(status, "i");
        Query queryGetRC = new Query(criteria).with(pageable);
        List<DhViews> dhViewsList = mongoTemplate.find(queryGetRC, DhViews.class);
        Page<DhViews> dhViewsPage = PageableExecutionUtils.getPage(
                dhViewsList,
                pageable,
                () -> mongoTemplate.count(queryGetRC, DhViews.class));
        return new Response<>(true, 201, "Query successful", dhViewsList.size(), dhViewsPage.getNumber(), dhViewsPage.getTotalPages(), dhViewsPage.getTotalElements(), dhViewsList);
    }
}
