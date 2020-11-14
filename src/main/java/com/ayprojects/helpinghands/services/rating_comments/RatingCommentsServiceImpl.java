package com.ayprojects.helpinghands.services.rating_comments;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhRating_comments;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.RatingAndCommentsRepository;
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
public class RatingCommentsServiceImpl implements RatingCommentsService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RatingAndCommentsRepository ratingAndCommentsRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhRating_comments> addRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, DhRating_comments dhRatingComments, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RatingCommentsServiceImpl->addRatingAndComments : language=" + language);

        if (dhRatingComments == null) {
            return new Response<DhRating_comments>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(dhRatingComments.getAddedBy())) missingFieldsList.add("AddedBy");
        if(dhRatingComments.getRating()<=0) missingFieldsList.add("Rating");
        if(Utility.isFieldEmpty(dhRatingComments.getContentType())) missingFieldsList.add("ContentType");
        if(Utility.isFieldEmpty(dhRatingComments.getContentId())) missingFieldsList.add("ContentId");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        dhRatingComments.setReviewCommentId(Utility.getUUID());
        dhRatingComments.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhRatingComments.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhRatingComments.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhRatingComments.setStatus(AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhRatingComments,AppConstants.COLLECTION_DH_RATING_COMMENT);
        utility.addLog(authentication.getName(),"New Rating & comment for ["+ dhRatingComments.getContentType()+"] has been added by user ["+ dhRatingComments.getAddedBy()+"].");

        //here content can be place,requirement & post
        String contentIdToSearch="";
        switch (dhRatingComments.getContentType()){
            case "Place":
                contentIdToSearch = "placeId";
                Query queryFindContentWithId = new Query(Criteria.where(contentIdToSearch).is(dhRatingComments.getContentId()));
                DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindContentWithId,DhPlace.class);
                if(queriedDhPlace == null)throw new ServerSideException("Unable to add rating into places collection");
                Update updatePlace = new Update();
                updatePlace.set("numberOfRatings",queriedDhPlace.getNumberOfRatings()+1);
                double avgRating=0;
                if(queriedDhPlace.getAvgRating()<=0){
                    avgRating = dhRatingComments.getRating();
                }
                else{
                    avgRating = (queriedDhPlace.getAvgRating()+ dhRatingComments.getRating())/2;
                }
                updatePlace.set("avgRating",avgRating);
                updatePlace.push("ratingIds", dhRatingComments.getReviewCommentId());
                if(queriedDhPlace.getTopRatings()!=null && queriedDhPlace.getTopRatings().size()==5){
                    Update updatePopTopRating = new Update();
                    updatePopTopRating.pop("topRatings", Update.Position.LAST);
                    mongoTemplate.updateFirst(queryFindContentWithId,updatePopTopRating,DhPlace.class);
                }
                updatePlace.push("topRatings", dhRatingComments);
                updatePlace.set("modifiedDateTime",Utility.currentDateTimeInUTC());
                mongoTemplate.updateFirst(queryFindContentWithId,updatePlace, DhPlace.class);
                break;
            case "Post":
                contentIdToSearch = "postId";
                break;
            case "Requirement":
                contentIdToSearch = "requirementId";
                break;
        }
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED,language),new ArrayList<>(),1);
    }

    @Override
    public Response<DhRating_comments> getPaginatedRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version) {
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
        Query queryGetRC = new Query(Criteria.where("contentId").regex(contentId,"i")).with(pageable);
        queryGetRC.addCriteria(Criteria.where("contentType").regex(contentType,"i"));
        queryGetRC.addCriteria(Criteria.where("status").regex(status,"i"));
        List<DhRating_comments> dhRatingCommentsList = mongoTemplate.find(queryGetRC, DhRating_comments.class);
        Page<DhRating_comments> ratingAndCommentsPage = PageableExecutionUtils.getPage(
                dhRatingCommentsList,
                pageable,
                () -> mongoTemplate.count(queryGetRC, DhRating_comments.class));
        return new Response<>(true, 201, "Query successful", dhRatingCommentsList.size(), ratingAndCommentsPage.getNumber(), ratingAndCommentsPage.getTotalPages(), ratingAndCommentsPage.getTotalElements(), dhRatingCommentsList);
    }
}
