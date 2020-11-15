package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PostsRepository;
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
import java.util.regex.Pattern;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PostsServiceImpl implements PostsService
{
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhPosts> addPost(Authentication authentication, HttpHeaders httpHeaders, DhPosts dhPosts, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        boolean isPlaceIdMissing = false;
        LOGGER.info("PostsServiceImpl->addPost : language=" + language);
        if (dhPosts == null) {
            return new Response<DhPosts>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }
        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(dhPosts.getAddedBy())) missingFieldsList.add("AddedBy");
        if(Utility.isFieldEmpty(dhPosts.getPostType())) missingFieldsList.add("PostType");
        else if(dhPosts.getPostType().equalsIgnoreCase(AppConstants.BUSINESS_POST)) {
            if(Utility.isFieldEmpty(dhPosts.getPlaceId())){
                missingFieldsList.add("PlaceId");
                isPlaceIdMissing = true;
            }
        }
        if(dhPosts.getAddressDetails()==null)missingFieldsList.add("AddressBlock");
        else {
            if(dhPosts.getAddressDetails().getLat()==0)
                missingFieldsList.add("Lattitude");
            if(dhPosts.getAddressDetails().getLng()==0)
                missingFieldsList.add("Longitude");
            if(Utility.isFieldEmpty(dhPosts.getAddressDetails().getFullAddress())) {
                missingFieldsList.add("Full Address");
            }
        }
        if(dhPosts.getContactDetails()==null)missingFieldsList.add("ContactBlock");
        else{
            if(Utility.isFieldEmpty(dhPosts.getContactDetails().getMobile())){
                missingFieldsList.add("MobileNumber");
            }
            if(Utility.isFieldEmpty(dhPosts.getContactDetails().getEmail())){
                missingFieldsList.add("Email");
            }
        }
        if(Utility.isFieldEmpty(dhPosts.getPostTitle())) missingFieldsList.add("PostTitle");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<DhPosts>(false,402,resMsg,new ArrayList<>(),0);
        }
        dhPosts.setPostId(Utility.getUUID());
        dhPosts.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhPosts.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhPosts.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhPosts.setStatus(AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPosts,AppConstants.COLLECTION_DH_POSTS);
        utility.addLog(authentication.getName(),"New ["+dhPosts.getPostType()+"] post has been added.");

        if(dhPosts.getPostType().equalsIgnoreCase(AppConstants.BUSINESS_POST) && !isPlaceIdMissing){
            LOGGER.info("PostsServiceImpl->addPost : It's business post");
            Query queryFindPlaceWithId = new Query(Criteria.where(AppConstants.PLACE_ID).is(dhPosts.getPlaceId()));
            DhPlace queriedDhPlace = mongoTemplate.findOne(queryFindPlaceWithId,DhPlace.class);
            if(queriedDhPlace == null)throw new ServerSideException("Unable to add posts into places collection");
            Update updatePlace = new Update();
                LOGGER.info("PostsServiceImpl->addPost : postIds block is null, pushing post id into postIds array");
                updatePlace.push(AppConstants.POST_IDS,dhPosts.getPostId());
            mongoTemplate.updateFirst(queryFindPlaceWithId,updatePlace,DhPlace.class);
        }

        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED,language),new ArrayList<>(),1);
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
        PageRequest paging = PageRequest.of(page,size);
        Page<DhPosts> dhPostPages = postsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE,paging);
        List<DhPosts> dhPostsList = dhPostPages.getContent();
        return new Response<DhPosts>(true,201,"Query successful",dhPostsList.size(),dhPostPages.getNumber(),dhPostPages.getTotalPages(),dhPostPages.getTotalElements(),dhPostsList);
    }

    @Override
    public Response<DhPosts> getPaginatedPostsByPlaceId(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String placeId, String version) {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : language=" + language);
        LOGGER.info("PostsServiceImpl->getPaginatedPostsByPlaceId : placeId=" + placeId+" page="+page+" size="+size);

        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(placeId)) missingFieldsList.add("PlaceId");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<>(false, 402, resMsg, new ArrayList<>(), 0);
        }
        Pageable pageable = PageRequest.of(page,size);
        Criteria criteria = new Criteria();
        Pattern patternPostType = Pattern.compile("^[Bb]usiness[\\s]*[Pp]ost$");
        criteria.and(AppConstants.POST_TYPE).regex(patternPostType);
        criteria.and(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE,"i");
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
