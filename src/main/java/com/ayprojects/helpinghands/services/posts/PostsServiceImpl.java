package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PostsRepository;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.tools.Validations;
import com.google.gson.Gson;

import org.slf4j.helpers.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.ayprojects.helpinghands.AppConstants.BUSINESS_POST;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    Utility utility;

    @Autowired
    CommonService commonService;

    @Override
    public Response<DhPosts> addPost(Authentication authentication, HttpHeaders httpHeaders, DhPosts dhPosts, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);

        String emptyBodyResMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
        if (dhPosts == null) {
            return new Response<DhPosts>(false, 402, emptyBodyResMsg, new ArrayList<>(), 0);
        }

        //check if postId present && postImages present
        if (Utility.isFieldEmpty(dhPosts.getPlaceId()) || dhPosts.getPostImages() == null || dhPosts.getPostImages().size() <= 0) {
            dhPosts.setPostId(Utility.getUUID());
        }

        List<String> missingFieldsList = Validations.findMissingFieldsForPosts(dhPosts);
        if (missingFieldsList.size() > 0) {
            emptyBodyResMsg = emptyBodyResMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhPosts>(false, 402, emptyBodyResMsg, new ArrayList<>(), 0);
        } else {
            if (!dhPosts.getPostType().equalsIgnoreCase(AppConstants.PUBLIC_POST) && !dhPosts.getPostType().equalsIgnoreCase(BUSINESS_POST)) {
                return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INVALID_POSTTYPE, language), new ArrayList<>(), 0);
            }
        }


        //check for user existence
        if (!commonService.checkUserExistence(dhPosts.getAddedBy())) {
            return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, language), new ArrayList<>(), 0);
        }

        //check whether place exists with given placeId
        DhPlace queriedDhPlace = null;
        Query queryFindPlaceWithId = null;
        boolean isBusinessPost = false;
        if (dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            isBusinessPost = true;
            queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPosts.getPlaceId()));
            queryFindPlaceWithId.addCriteria(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
            queryFindPlaceWithId.fields().include(AppConstants.TOP_POSTS);
            queryFindPlaceWithId.fields().include(AppConstants.NUMBER_OF_POSTS);
            queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
            if (queriedDhPlace == null) {
                return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, language), new ArrayList<>(), 0);
            }
        }

        //store posts
        dhPosts = (DhPosts) utility.setCommonAttrs(dhPosts, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPosts, AppConstants.COLLECTION_DH_POSTS);
        utility.addLog(authentication.getName(), "New [" + dhPosts.getPostType() + "] post has been added.");

        //update the place if post is businesspost
        if (isBusinessPost) {
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Update updatePlace = new Update();
            LOGGER.info("PostsServiceImpl->addPost : postIds block is null, pushing post id into postIds array");
            updatePlace.push(AppConstants.POST_IDS, dhPosts.getPostId());
            updatePlace.set(AppConstants.NUMBER_OF_POSTS, queriedDhPlace.getNumberOfPosts() + 1);
            if (queriedDhPlace.getTopPosts() != null && queriedDhPlace.getTopPosts().size() == AppConstants.LIMIT_POSTS_IN_PLACES) {
                Update updatePopTopPost = new Update();
                updatePopTopPost.pop(AppConstants.TOP_POSTS, Update.Position.FIRST);
                mongoTemplate.updateFirst(queryFindPlaceWithId, updatePopTopPost, DhPlace.class);
            }
            updatePlace.push(AppConstants.TOP_POSTS, dhPosts);
            updatePlace.set(AppConstants.MODIFIED_DATE_TIME, Utility.currentDateTimeInUTC());
            mongoTemplate.updateFirst(queryFindPlaceWithId, updatePlace, DhPlace.class);
        }

        return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED, language), new ArrayList<>(), 1);
    }

    @Override
    public Response<DhPosts> deletePost(Authentication authentication, HttpHeaders httpHeaders, String postId, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPosts> updatePost(Authentication authentication, HttpHeaders httpHeaders, DhPosts dhPosts, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPosts> getPosts(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version) {
        return null;
    }

    @Override
    public Response<DhPosts> getPaginatedPosts(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);

        PageRequest paging = PageRequest.of(page, size);
        Page<DhPosts> dhPostPages = postsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPosts> dhPostsList = dhPostPages.getContent();
        for (DhPosts d : dhPostsList) {

            if (!Utility.isFieldEmpty(d.getOfferStartTime()) && !Utility.isFieldEmpty(d.getOfferEndTime())) {
                String offerMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_OFFER_MSG, language);
                offerMsg = String.format("%s %s - %s", offerMsg, d.getOfferStartTime(), d.getOfferEndTime());
                d.setOfferMsg(offerMsg);
            }

            DhUser dhUser = Utility.getUserDetailsFromId(d.getAddedBy(), mongoTemplate, true, false, true);
            if (dhUser != null) {
                d.setUserName(dhUser.getFirstName());
                d.setUserImage(dhUser.getProfileImg());
            }

            if (Utility.isFieldEmpty(d.getPlaceId())) {
                DhPlace dhPlace = Utility.getPlaceDetailsFromId(d.getPlaceId(), mongoTemplate, true, true);
                if (dhPlace != null) {
                    d.setPlaceName(dhPlace.getPlaceName());
                    d.setPlaceCategory(dhPlace.getPlaceSubCategoryName());
                }
            }
        }

        return new Response<DhPosts>(true, 200, "Query successful", dhPostsList.size(), dhPostPages.getNumber(), dhPostPages.getTotalPages(), dhPostPages.getTotalElements(), dhPostsList);
    }

    @Override
    public Response<DhPosts> getPaginatedPostsByPlaceId(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String placeId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : language=" + language);
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : placeId=" + placeId + " page=" + page + " size=" + size);

        List<String> missingFieldsList = new ArrayList<>();
        if (Utility.isFieldEmpty(placeId)) missingFieldsList.add("PlaceId");
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        Pageable pageable = PageRequest.of(page, size);
        Criteria criteria = new Criteria();
        Pattern patternPostType = Pattern.compile(AppConstants.REGEX_BUSINESS_POST);
        criteria.and(AppConstants.POST_TYPE).regex(patternPostType);
        criteria.and(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i");
        criteria.and(AppConstants.PLACE_ID).is(placeId);
        Query queryGetPosts = new Query(criteria).with(pageable);
        List<DhPosts> dhPostsList = mongoTemplate.find(queryGetPosts, DhPosts.class);
        Page<DhPosts> dhPostsPage = PageableExecutionUtils.getPage(
                dhPostsList,
                pageable,
                () -> mongoTemplate.count(queryGetPosts, DhPosts.class));
        return new Response<>(true, 200, "Query successful", dhPostsList.size(), dhPostsPage.getNumber(), dhPostsPage.getTotalPages(), dhPostsPage.getTotalElements(), dhPostsList);
    }
}
