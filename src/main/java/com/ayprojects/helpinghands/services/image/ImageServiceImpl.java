package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.tools.Utility;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.rmi.CORBA.Util;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    Utility utility;

    @Autowired
    LogService logService;

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    String fileDivider = "/";
    String nameDivider = "_";

    @Override
    public Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile image, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RequirementsServiceImpl->addRequirements : language=" + language);

        if (image == null) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        String uniqueUserID = Utility.getUUID();
        String imgUploadFolder = imagesBaseFolder + fileDivider + uniqueUserID + AppConstants.USER_DIR;
        String imgPrefix = AppConstants.USER_INITIAL + uniqueUserID + nameDivider;

        if (Utility.isFieldEmpty(imagesBaseFolder) || Utility.isFieldEmpty(imgPrefix)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, language), new ArrayList<>());
        }

        LOGGER.info("ImageServiceImpl->uploadImage : imageUploadFolder=" + imagesBaseFolder + " imagePrefix = " + imgPrefix);

        try {
            String url = utility.uplodImages(imgUploadFolder, new MultipartFile[]{image}, imgPrefix).get(0);
            DhUser dhUser = new DhUser(uniqueUserID, url);
            utility.addLog(uniqueUserID, "User image has been added");
            return new Response<DhUser>(true, 201, "Image save successfully", Collections.singletonList(dhUser));
        } catch (IOException ioException) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG, language), new ArrayList<>());
        }
    }

    @Override
    public Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImages, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RequirementsServiceImpl->addRequirements : language=" + language);

        if (placeImages == null || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        String imgType = placeType.matches(AppConstants.REGEX_BUSINESS_PLACE) ? "B" : "P";
        String uinquePlaceId = Utility.getUUID();
        String imgUploadFolder = imagesBaseFolder + fileDivider + addedBy + AppConstants.PLACE_DIR + placeType + fileDivider;
        String imgPrefix = imgType + AppConstants.PLACE_INITIAL + uinquePlaceId + nameDivider;

        if (Utility.isFieldEmpty(imagesBaseFolder) || Utility.isFieldEmpty(imgPrefix)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, language), new ArrayList<>());
        }

        LOGGER.info("ImageServiceImpl->uploadPlaceImages : imageType=" + placeType + " imageUploadFolder=" + imagesBaseFolder + " imagePrefix = " + imgPrefix);
        try {
            List<String> placeImageUrls = utility.uplodImages(imgUploadFolder, placeImages, imgPrefix);
            DhPlace dhPlace = new DhPlace();
            dhPlace.setPlaceId(uinquePlaceId);
            dhPlace.setAddedBy(addedBy);
            dhPlace.setPlaceImages(placeImageUrls);
            dhPlace.setPlaceType(placeType);
            utility.addLog(addedBy, "Place images have been added");
            return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED, language), Collections.singletonList(dhPlace), 1);
        } catch (IOException ioException) {
            LOGGER.info("ImageServiceImpl->uploadPlaceImages : exception = " + ioException.getMessage());
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, language), new ArrayList<>());
        }
    }

    @Override
    public Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy, MultipartFile[] postImages, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("ImageServiceImpl->uploadPostImages : language=" + language);

        if (postImages == null || Utility.isFieldEmpty(postType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        String imgType = postType.matches(AppConstants.REGEX_BUSINESS_POST) ? "B" : "P";
        String uinquePostId = Utility.getUUID();
        String imgUploadFolder = imagesBaseFolder + fileDivider + addedBy + AppConstants.POST_DIR + postType + fileDivider;
        String imgPrefix = imgType + AppConstants.POST_INITIAL + uinquePostId + nameDivider;

        if (Utility.isFieldEmpty(imagesBaseFolder) || Utility.isFieldEmpty(imgPrefix)) {
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, language), new ArrayList<>());
        }

        LOGGER.info("ImageServiceImpl->uploadPostImages : imageType=" + postType + " imageUploadFolder=" + imagesBaseFolder + " imagePrefix = " + imgPrefix);
        try {
            List<String> postImageUrls = utility.uplodImages(imgUploadFolder, postImages, imgPrefix);
            DhPosts dhPosts = new DhPosts();
            dhPosts.setPostId(uinquePostId);
            dhPosts.setAddedBy(addedBy);
            dhPosts.setPostImages(postImageUrls);
            utility.addLog(addedBy, "Post images have been added");
            return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_POST_IMAGES_ADDED, language), new ArrayList<>(), 1);
        } catch (IOException ioException) {
            LOGGER.info("ImageServiceImpl->uploadPostImages : exception = " + ioException.getMessage());
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES, language), new ArrayList<>());
        }
    }

    @Override
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
            List<String> reqImageUrls = utility.uplodImages(imgUploadFolder, reqImages, imgPrefix);
            DhRequirements dhRequirements = new DhRequirements();
            dhRequirements.setRequirementId(uinqueReqId);
            dhRequirements.setAddedBy(addedBy);
            dhRequirements.setRequirementImages(reqImageUrls);
            utility.addLog(addedBy, "Requirement images have been added");
            return new Response<>(true, 201, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_REQ_IMAGES_ADDED, language), new ArrayList<>(), 1);
        } catch (IOException ioException) {
            LOGGER.info("ImageServiceImpl->uploadRequirementImages : exception = " + ioException.getMessage());
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, language), new ArrayList<>());
        }
    }
}
