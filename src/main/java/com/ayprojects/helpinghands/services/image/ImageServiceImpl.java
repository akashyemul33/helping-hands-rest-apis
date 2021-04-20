package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.aws.AmazonClient;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    LogService logService;

    @Autowired
    AmazonClient amazonClient;

    @Override
    public Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile image, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (image == null || image.isEmpty()) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniqueUserID = Utility.getUUID();
        String imgUploadKey = GetImageFoldersAndPrefix.getUserImgUploadKey(uniqueUserID);
        try {
            String finalKey = amazonClient.uploadSingleImageToS3(imgUploadKey, image);
            DhUser dhUser = new DhUser(uniqueUserID, finalKey);
            logService.addLog(new DhLog(uniqueUserID, "User image has been added"));
            return new Response<DhUser>(true, 201, "Image saved successfully", Collections.singletonList(dhUser));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImages, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (placeImages == null || placeImages.length == 0 || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniquePlaceID = Utility.getUUID();
        String placeImgUploadKey = GetImageFoldersAndPrefix.getPlaceImgUploadKey(uniquePlaceID, placeType);

        try {
            List<String> placeImageUrls = amazonClient.uploadImagesToS3(placeImgUploadKey, placeImages);
            DhPlace dhPlace = new DhPlace();
            dhPlace.setPlaceId(uniquePlaceID);
            dhPlace.setAddedBy(addedBy);
            dhPlace.setPlaceImages(placeImageUrls);
            dhPlace.setPlaceType(placeType);
            logService.addLog(new DhLog(addedBy, "Place images have been added"));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(dhPlace), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadPlaceImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy, MultipartFile[] postImages, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (postImages == null || postImages.length == 0 || Utility.isFieldEmpty(postType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniquePostID = Utility.getUUID();
        String postImgUploadKey = GetImageFoldersAndPrefix.getPostImgUploadKey(uniquePostID, postType);

        try {
            List<String> postImageUrls = amazonClient.uploadImagesToS3(postImgUploadKey, postImages);
            DhPosts dhPosts = new DhPosts();
            dhPosts.setPostId(uniquePostID);
            dhPosts.setAddedBy(addedBy);
            dhPosts.setPostImages(postImageUrls);
            logService.addLog(new DhLog(addedBy, "Post images have been added"));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED);
            return new Response<>(true, 201,successMsg , Collections.singletonList(dhPosts), 1);
        }
        catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadPostImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }


    /*@Override
    public Response<DhRequirements> uploadRequirementImages(HttpHeaders httpHeaders, Authentication authentication, String reqType, String addedBy, MultipartFile[] reqImages, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ImageServiceImpl->uploadRequirementImages : language=" + language);

        if (reqImages == null || Utility.isFieldEmpty(reqType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        String imgType = reqType.matches(AppConstants.REGEX_BUSINESS_REQUIREMENT) ? "B" : "P";
        String uinqueReqId = Utility.getUUID();
        String imgUploadFolder = imagesBaseFolder + fileDivider + addedBy + AppConstants.REQUIREMENT_DIR + reqType + fileDivider;
        String imgPrefix = imgType + AppConstants.REQUIREMENT_INITIAL + uinqueReqId + nameDivider;

        if (Utility.isFieldEmpty(imagesBaseFolder) || Utility.isFieldEmpty(imgPrefix)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, language), new ArrayList<>());
        }

        LOGGER.info("ImageServiceImpl->uploadRequirementImages : imageType=" + reqType + " imageUploadFolder=" + imagesBaseFolder + " imagePrefix = " + imgPrefix);
        try {
            List<String> reqImageUrls = amazonClient.uploadImagesToS3(imgUploadFolder + imgPrefix, reqImages);
            DhRequirements dhRequirements = new DhRequirements();
            dhRequirements.setRequirementId(uinqueReqId);
            dhRequirements.setAddedBy(addedBy);
            dhRequirements.setRequirementImages(reqImageUrls);
            utility.addLog(addedBy, "Requirement images have been added");
            return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_REQ_IMAGES_ADDED, language), new ArrayList<>(), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadRequirementImages : exception = " + ioException.getMessage());
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, language), new ArrayList<>());
        }
    }
*/

}
