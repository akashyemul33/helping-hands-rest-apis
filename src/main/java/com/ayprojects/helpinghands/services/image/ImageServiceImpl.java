package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.enums.SinglePlaceImageOperationsEnum;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.aws.AmazonClient;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.CalendarOperations;
import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (imageLow == null || imageLow.isEmpty() || imageHigh.isEmpty()) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniqueUserID = Utility.getUUID();
        String imgUploadKeyLow = GetImageFoldersAndPrefix.getUserImgUploadKeyLow(uniqueUserID, false);
        String imgUploadKeyHigh = GetImageFoldersAndPrefix.getUserImgUploadKeyLow(uniqueUserID, true);
        try {
            String finalKeyLow = amazonClient.uploadSingleImageToS3(imgUploadKeyLow, imageLow);
            String finalKeyHigh = amazonClient.uploadSingleImageToS3(imgUploadKeyHigh, imageHigh);
            DhUser dhUser = new DhUser(uniqueUserID, finalKeyLow, finalKeyHigh);
            logService.addLog(new DhLog(uniqueUserID, "User image has been added"));
            return new Response<DhUser>(true, 201, "Image saved successfully", Collections.singletonList(dhUser));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImagesLow, MultipartFile[] placeImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (placeImagesLow == null || placeImagesLow.length == 0 || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniquePlaceID = Utility.getUUID();
        String placeImgUploadKeyLow = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, uniquePlaceID, placeType, false);
        String placeImgUploadKeyHigh = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, uniquePlaceID, placeType, true);

        try {
            List<String> placeImageUrlsLow = amazonClient.uploadImagesToS3(placeImgUploadKeyLow, placeImagesLow);
            List<String> placeImageUrlsHigh = amazonClient.uploadImagesToS3(placeImgUploadKeyHigh, placeImagesHigh);
            DhPlace dhPlace = new DhPlace();
            dhPlace.setPlaceId(uniquePlaceID);
            dhPlace.setAddedBy(addedBy);
            dhPlace.setImageUrlsLow(placeImageUrlsLow);
            dhPlace.setImageUrlsHigh(placeImageUrlsHigh);
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
    public Response<DhPlace> singlePlaceImageOperations(HttpHeaders httpHeaders, Authentication authentication, List<String> existingImgUrlsLowList, List<String> existingImgUrlsHighList, int editOrRemovePos, String placeId, String placeType, String addedBy, MultipartFile placeImagesLow, MultipartFile placeImagesHigh, SinglePlaceImageOperationsEnum operationsEnum, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if ((operationsEnum != SinglePlaceImageOperationsEnum.DELETE_PLACE_IMAGE && (placeImagesLow == null || placeImagesHigh == null)) || (Utility.isFieldEmpty(placeId) || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy))) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        int posToInsert = existingImgUrlsHighList.size();
        switch (operationsEnum) {
            case DELETE_PLACE_IMAGE:
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsHighList.get(editOrRemovePos));
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsLowList.get(editOrRemovePos));
                existingImgUrlsHighList.remove(editOrRemovePos);
                existingImgUrlsLowList.remove(editOrRemovePos);
                break;
            case UPDATE_PLACE_IMAGE:
                existingImgUrlsHighList.remove(editOrRemovePos);
                existingImgUrlsLowList.remove(editOrRemovePos);
                posToInsert = editOrRemovePos;
            case INSERT_PLACE_IMAGE:
                String placeImgUploadKeyLow = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, placeId, placeType, false);
                String placeImgUploadKeyHigh = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, placeId, placeType, true);
                try {
                    List<String> lowList = amazonClient.uploadImagesToS3(placeImgUploadKeyLow, new MultipartFile[]{placeImagesLow});
                    List<String> highList = amazonClient.uploadImagesToS3(placeImgUploadKeyHigh, new MultipartFile[]{placeImagesHigh});
                    if (lowList == null || highList == null || lowList.isEmpty() || highList.isEmpty()) {
                        String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES);
                        return new Response<>(false, 402, errorMsg, new ArrayList<>());
                    }

                    existingImgUrlsHighList.add(posToInsert, highList.get(0));
                    existingImgUrlsLowList.add(posToInsert, lowList.get(0));
                } catch (Exception ioException) {
                    LOGGER.info("ImageServiceImpl->singlePlaceImageOperations : exception = " + ioException.getMessage());
                    String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES);
                    return new Response<>(false, 402, errorMsg, new ArrayList<>());
                }
                break;
        }

        DhPlace dhPlace = new DhPlace();
        Update updatePlace = new Update();
        updatePlace.set(AppConstants.IMAGE_URL_LOW, existingImgUrlsLowList);
        updatePlace.set(AppConstants.IMAGE_URL_HIGH, existingImgUrlsHighList);
        updatePlace.set(AppConstants.MODIFIED_DATE_TIME, CalendarOperations.currentDateTimeInUTC());
        Query query = new Query(Criteria.where(AppConstants.PLACE_ID).is(placeId));
        mongoTemplate.updateFirst(query, updatePlace, DhPlace.class);
        logService.addLog(new DhLog(addedBy, "Place images have been updated of placeId:" + placeId));
        String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_UPDATED);
        return new Response<>(true, 201, successMsg, Collections.singletonList(dhPlace), 1);
    }

    @Override
    public Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy, MultipartFile[] postImagesLow, MultipartFile[] postImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (postImagesLow == null || postImagesLow.length == 0 || Utility.isFieldEmpty(postType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniquePostID = Utility.getUUID();
        String postImgUploadKeyLow = GetImageFoldersAndPrefix.getPostImgUploadKey(addedBy, uniquePostID, postType, false);
        String postImgUploadKeyHigh = GetImageFoldersAndPrefix.getPostImgUploadKey(addedBy, uniquePostID, postType, true);

        try {
            List<String> postImageUrlsLow = amazonClient.uploadImagesToS3(postImgUploadKeyLow, postImagesLow);
            List<String> postImageUrlsHigh = amazonClient.uploadImagesToS3(postImgUploadKeyHigh, postImagesHigh);
            DhPosts dhPosts = new DhPosts();
            dhPosts.setPostId(uniquePostID);
            dhPosts.setAddedBy(addedBy);
            dhPosts.setPostImagesLow(postImageUrlsLow);
            dhPosts.setPostImagesHigh(postImageUrlsHigh);
            logService.addLog(new DhLog(addedBy, "Post images have been added"));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(dhPosts), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadPostImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<ProductsWithPrices> uploadProductImages(HttpHeaders httpHeaders, Authentication authentication, String uniqueProductId, String placeType, String placeId, String addedBy, MultipartFile[] productImagesLow, MultipartFile[] productImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (productImagesLow == null || productImagesLow.length == 0 || Utility.isFieldEmpty(addedBy) || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(placeId)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String postImgUploadKeyLow = GetImageFoldersAndPrefix.getProductImgUploadKey(uniqueProductId, placeType, placeId, uniqueProductId, false);
        String postImgUploadKeyHigh = GetImageFoldersAndPrefix.getProductImgUploadKey(uniqueProductId, placeType, placeId, uniqueProductId, true);

        try {
            List<String> uploadedProductImgsListLow = amazonClient.uploadImagesToS3(postImgUploadKeyLow, productImagesLow);
            List<String> uploadedProductImgsListHigh = amazonClient.uploadImagesToS3(postImgUploadKeyHigh, productImagesHigh);
            ProductsWithPrices productsWithPrices = new ProductsWithPrices();
            productsWithPrices.setImgUrlsLow(uploadedProductImgsListLow);
            productsWithPrices.setImgUrlsHigh(uploadedProductImgsListHigh);
            logService.addLog(new DhLog(addedBy, "Product images have been added for placeId=" + placeId + " productId=%s" + uniqueProductId));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(productsWithPrices), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadProductImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PRODUCT_IMAGES);
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
