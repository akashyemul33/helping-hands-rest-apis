package com.ayprojects.helpinghands.services.rating_comments;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.RatingAndCommentsRepository;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

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
public class RatingCommentsServiceImpl implements RatingCommentsService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RatingAndCommentsRepository ratingAndCommentsRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhRatingAndComments> addRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, DhRatingAndComments dhRatingComments, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RatingCommentsServiceImpl->addRatingAndComments : language=" + language);

        if (dhRatingComments == null) {
            return new Response<DhRatingAndComments>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = Validations.findMissingFieldsForRatings(dhRatingComments);
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        dhRatingComments.setReviewCommentId(Utility.getUUID());
        dhRatingComments = (DhRatingAndComments) utility.setCommonAttrs(dhRatingComments,AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhRatingComments,AppConstants.COLLECTION_DH_RATING_COMMENT);
        utility.addLog(authentication.getName(),"New Rating & comment for ["+ dhRatingComments.getContentType()+"] has been added by user ["+ dhRatingComments.getAddedBy()+"].");

        //here content can be place,requirement & post
        String contentIdToSearch="";
        switch (dhRatingComments.getContentType().name().toUpperCase()){
            case AppConstants.PLACE:
                contentIdToSearch = AppConstants.PLACE_ID;
                Query queryFindPlaceWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId,DhPlace.class);
                if(queriedDhPlace == null)throw new ServerSideException("Unable to add rating into places collection, seems like no place found with given id");
                Update updatePlace = new Update();
                updatePlace.set(AppConstants.NUMBER_OF_RATINGS,queriedDhPlace.getNumberOfRatings()+1);
                double avgPlaceRating=0;
                if(queriedDhPlace.getAvgRating()<=0){
                    avgPlaceRating = dhRatingComments.getRating();
                }
                else{
                    avgPlaceRating = (queriedDhPlace.getAvgRating()+ dhRatingComments.getRating())/2;
                }
                updatePlace.set(AppConstants.AVG_RATING,avgPlaceRating);
                updatePlace.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                if(queriedDhPlace.getTopRatings()!=null && queriedDhPlace.getTopRatings().size()==AppConstants.LIMIT_RATINGS_IN_PLACES){
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.FIRST);
                    mongoTemplate.updateFirst(queryFindPlaceWithId,updatePopTopRating,DhPlace.class);
                }
                updatePlace.push(AppConstants.TOP_RATINGS, dhRatingComments);
                updatePlace.set(AppConstants.MODIFIED_DATE_TIME,CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPlaceWithId,updatePlace, DhPlace.class);
                break;
            case AppConstants.PROMOTION:
                contentIdToSearch = AppConstants.PROMOTION_ID;
                Query queryFindPostWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhPromotions queriedDhPost = mongoTemplate.findOne(queryFindPostWithId, DhPromotions.class);
                if(queriedDhPost == null)throw new ServerSideException("Unable to add rating into posts collection, seems like no post found with given id");
                Update updatePost = new Update();
//                updatePost.set(AppConstants.NUMBER_OF_RATINGS,queriedDhPost.getNumberOfRatings()+1);
                double avgPostsRating=0;
                /*if(queriedDhPost.getAvgRating()<=0){
                    avgPostsRating = dhRatingComments.getRating();
                }
                else{
                    avgPostsRating = (queriedDhPost.getAvgRating()+ dhRatingComments.getRating())/2;
                }*/
                updatePost.set(AppConstants.AVG_RATING,avgPostsRating);
                updatePost.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                /*if(queriedDhPost.getTopRatings()!=null && queriedDhPost.getTopRatings().size()==AppConstants.LIMIT_RATINGS_IN_PROMOTIONS){
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindPostWithId,updatePopTopRating, DhPromotions.class);
                }*/
                updatePost.push(AppConstants.TOP_RATINGS, dhRatingComments);
                updatePost.set(AppConstants.MODIFIED_DATE_TIME,CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindPostWithId,updatePost, DhPromotions.class);
                break;
            case AppConstants.REQUIREMENT:
                contentIdToSearch = AppConstants.REQUIREMENT_ID;
                Query queryFindRequirementWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhRequirements queriedDhRequirement = mongoTemplate.findOne(queryFindRequirementWithId, DhRequirements.class);
                if(queriedDhRequirement == null)throw new ServerSideException("Unable to add rating into requirements collection, seems like no requirement found with given id");
                Update updateRequirement = new Update();
                updateRequirement.set(AppConstants.NUMBER_OF_RATINGS,queriedDhRequirement.getNumberOfRatings()+1);
                double avgRequirementRating=0;
                if(queriedDhRequirement.getAvgRating()<=0){
                    avgRequirementRating = dhRatingComments.getRating();
                }
                else{
                    avgRequirementRating = (queriedDhRequirement.getAvgRating()+ dhRatingComments.getRating())/2;
                }
                updateRequirement.set(AppConstants.AVG_RATING,avgRequirementRating);
                updateRequirement.push(AppConstants.RATINGS_IDS, dhRatingComments.getReviewCommentId());
                if(queriedDhRequirement.getTopRatings()!=null && queriedDhRequirement.getTopRatings().size()==AppConstants.LIMIT_RATINGS_IN_REQUIREMENTS){
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop(AppConstants.TOP_RATINGS, Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindRequirementWithId,updatePopTopRating,DhRequirements.class);
                }
                updateRequirement.push(AppConstants.TOP_RATINGS, dhRatingComments);
                updateRequirement.set(AppConstants.MODIFIED_DATE_TIME,CalendarOperations.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindRequirementWithId,updateRequirement, DhRequirements.class);
                break;
        }
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED,language),new ArrayList<>(),1);
    }

    @Override
    public Response<DhRatingAndComments> getPaginatedRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RatingCommentsServiceImpl->getPaginatedRatingAndComments : language=" + language);
        LOGGER.info("RatingCommentsServiceImpl->getPaginatedRatingAndComments : contentId=" + contentId+" contentType="+contentType+" satus="+status+" page="+page+" size="+size);

        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(contentId)) missingFieldsList.add("ContentId");
        if(Utility.isFieldEmpty(contentType)) missingFieldsList.add("ContentType");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        Pageable pageable = PageRequest.of(page,size);
        Criteria criteria = new Criteria();
        criteria.and(AppConstants.CONTENT_ID).regex(contentId,"i");
        criteria.and(AppConstants.CONTENT_TYPE).regex(contentType,"i");
        criteria.and(AppConstants.STATUS).regex(status,"i");
        Query queryGetRC = new Query(criteria).with(pageable);
        List<DhRatingAndComments> dhRatingCommentsList = mongoTemplate.find(queryGetRC, DhRatingAndComments.class);
        Page<DhRatingAndComments> ratingAndCommentsPage = PageableExecutionUtils.getPage(
                dhRatingCommentsList,
                pageable,
                () -> mongoTemplate.count(queryGetRC, DhRatingAndComments.class));
        return new Response<>(true, 201, "Query successful", dhRatingCommentsList.size(), ratingAndCommentsPage.getNumber(), ratingAndCommentsPage.getTotalPages(), ratingAndCommentsPage.getTotalElements(), dhRatingCommentsList);
    }
}
