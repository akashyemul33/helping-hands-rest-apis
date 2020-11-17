package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PostsRepository;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.tools.Validations;
import com.google.gson.Gson;

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

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PostsServiceImpl implements PostsService {

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhPosts> addPost(Authentication authentication, HttpHeaders httpHeaders, MultipartFile[] postImages, String postBody, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);

        //convert postBody json string to DhPost object
        DhPosts dhPosts = null;
        if (!Utility.isFieldEmpty(postBody)) dhPosts = new Gson().fromJson(postBody, DhPosts.class);

        if (dhPosts == null) {
            return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        boolean isPlaceIdMissing = false;
        List<String> missingFieldsList = Validations.findMissingFieldsForPosts(dhPosts);
        if (missingFieldsList.contains(AppConstants.PLACE_ID)) isPlaceIdMissing = true;
        if (missingFieldsList.size() > 0) {
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language);
            resMsg = resMsg + " , these fields are missing : " + missingFieldsList;
            return new Response<DhPosts>(false, 402, resMsg, new ArrayList<>(), 0);
        }

        String uinquePostId = Utility.getUUID();
        //store image first
        if (postImages != null) {
            String imgType = dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST) ? "B" : "P";
            String imgUploadFolder = imagesBaseFolder + "/" + dhPosts.getAddedBy() + "/posts/" + dhPosts.getPostType() + "/";
            String imgPrefix = imgType + "_PSTS_" + uinquePostId + "_";
            LOGGER.info("PostsServiceImpl->addPosts : imagesBaseFolder = " + imagesBaseFolder + " imgPrefix=" + imgPrefix);
            try {
                utility.uplodImages(imgUploadFolder, postImages, imgPrefix);
            } catch (IOException ioException) {
                return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG, language), new ArrayList<>());
            }
        }
        dhPosts.setPostId(uinquePostId);
        dhPosts = (DhPosts) utility.setCommonAttrs(dhPosts, AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPosts, AppConstants.COLLECTION_DH_POSTS);
        utility.addLog(authentication.getName(), "New [" + dhPosts.getPostType() + "] post has been added.");

        if (dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST) && !isPlaceIdMissing) {
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Query queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPosts.getPlaceId()));
            DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId, DhPlace.class);
            if (queriedDhPlace == null)
                throw new ServerSideException("Unable to add posts into places collection");
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
        PageRequest paging = PageRequest.of(page, size);
        Page<DhPosts> dhPostPages = postsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE, paging);
        List<DhPosts> dhPostsList = dhPostPages.getContent();
        return new Response<DhPosts>(true, 201, "Query successful", dhPostsList.size(), dhPostPages.getNumber(), dhPostPages.getTotalPages(), dhPostPages.getTotalElements(), dhPostsList);
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
        return new Response<>(true, 201, "Query successful", dhPostsList.size(), dhPostsPage.getNumber(), dhPostsPage.getTotalPages(), dhPostsPage.getTotalElements(), dhPostsList);
    }
}
