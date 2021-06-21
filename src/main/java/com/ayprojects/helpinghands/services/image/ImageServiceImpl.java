package com.ayprojects.helpinghands.services.image;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.enums.SinglePlaceImageOperationsEnum;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.Contact;
import com.ayprojects.helpinghands.models.DhHHPost;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.ProductsWithPrices;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Thoughts;
import com.ayprojects.helpinghands.models.ThoughtsStatus;
import com.ayprojects.helpinghands.services.common_service.CommonService;
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
    CommonService commonService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (imageLow == null || imageLow.isEmpty() || imageHigh == null || imageHigh.isEmpty())
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

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
    public Response<Thoughts> uploadThoughtImage(HttpHeaders httpHeaders, MultipartFile imageLow, MultipartFile imageHigh, String addedBy, String thoughtStr, String userName, String userImg, boolean fromSystem, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (imageLow == null || imageLow.isEmpty() || imageHigh == null || imageHigh.isEmpty() || Utility.isFieldEmpty(thoughtStr) || Utility.isFieldEmpty(addedBy)) {
            LOGGER.info("uploadThoughtImage->returning as body is null");
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        String uniqueThoughtID = Utility.getUUID();
        String imgUploadKeyLow = GetImageFoldersAndPrefix.getThoughtImgUploadKeyLow(uniqueThoughtID, false);
        String imgUploadKeyHigh = GetImageFoldersAndPrefix.getThoughtImgUploadKeyLow(uniqueThoughtID, true);
        try {
            String finalKeyLow = amazonClient.uploadSingleImageToS3(imgUploadKeyLow, imageLow);
            String finalKeyHigh = amazonClient.uploadSingleImageToS3(imgUploadKeyHigh, imageHigh);
            Thoughts thoughts = new Thoughts();
            thoughts.setThoughtId(uniqueThoughtID);
            thoughts.setAddedBy(addedBy);
            thoughts.setFromSystem(fromSystem);
            thoughts.setThoughtImgPathLow(finalKeyLow);
            thoughts.setThoughtImgPathHigh(finalKeyHigh);
            thoughts.setThoughtStr(thoughtStr);
            thoughts.setUserImg(userImg);
            thoughts.setUserName(userName);
            thoughts = (Thoughts) Utility.setCommonAttrs(thoughts, ThoughtsStatus.PENDING.name());
            if (fromSystem)
                mongoTemplate.save(thoughts, AppConstants.COLLECTION_DH_SYSTEM_THOUGHTS);
            else mongoTemplate.save(thoughts, AppConstants.COLLECTION_DH_USER_THOUGHTS);
            logService.addLog(new DhLog(uniqueThoughtID, "New Thought has been added"));
            return new Response<Thoughts>(true, 201, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHT_ADDED_HEADING), ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_THOUGHT_ADDED_BODY), Collections.singletonList(thoughts));
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImagesLow, MultipartFile[] placeImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (placeImagesHigh == null || placeImagesHigh.length == 0 || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        if (!commonService.checkUserExistence(addedBy)) {
            return new Response<DhPlace>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }

        if (placeImagesHigh.length > AppConstants.PER_PLACE_MAX_IMAGES_LIMIT)
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_IMAGES_SIZE_GREATER_THAN_MAX), new ArrayList<>());

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
        if ((operationsEnum != SinglePlaceImageOperationsEnum.DELETE_PLACE_IMAGE && (placeImagesLow == null || placeImagesHigh == null)) || (Utility.isFieldEmpty(placeId) || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(addedBy) || existingImgUrlsHighList.size() != existingImgUrlsLowList.size())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        int posToInsert = existingImgUrlsHighList.size();
        LOGGER.info("singlePlaceImageOperations=posToInser=" + posToInsert);
        LOGGER.info("singlePlaceImageOperations=lowList= " + existingImgUrlsLowList + "... size=%s" + existingImgUrlsLowList.size());
        LOGGER.info("singlePlaceImageOperations=highList=" + existingImgUrlsHighList.toString() + "... size=%s" + existingImgUrlsHighList.size());
        switch (operationsEnum) {
            case DELETE_PLACE_IMAGE:
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsHighList.get(editOrRemovePos));
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsLowList.get(editOrRemovePos));
                existingImgUrlsHighList.remove(editOrRemovePos);
                existingImgUrlsLowList.remove(editOrRemovePos);
                break;
            case UPDATE_PLACE_IMAGE:
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsHighList.get(editOrRemovePos));
                amazonClient.deleteFileFromS3BucketUsingUrl(existingImgUrlsLowList.get(editOrRemovePos));
                existingImgUrlsHighList.remove(editOrRemovePos);
                existingImgUrlsLowList.remove(editOrRemovePos);
                posToInsert = editOrRemovePos;
            case INSERT_PLACE_IMAGE:
                String placeImgUploadKeyLow = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, placeId, placeType, false);
                String placeImgUploadKeyHigh = GetImageFoldersAndPrefix.getPlaceImgUploadKey(addedBy, placeId, placeType, true);
                try {
                    String lowUrl = amazonClient.uploadSingleImageToS3(placeImgUploadKeyLow, placeImagesLow);
                    String highUrl = amazonClient.uploadSingleImageToS3(placeImgUploadKeyHigh, placeImagesHigh);
                    existingImgUrlsHighList.add(posToInsert, highUrl);
                    existingImgUrlsLowList.add(posToInsert, lowUrl);

                } catch (Exception ioException) {
                    ioException.printStackTrace();
                    LOGGER.info("ImageServiceImpl->singlePlaceImageOperations : exception = " + ioException.getMessage());
                    String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES);
                    return new Response<>(false, 402, errorMsg, new ArrayList<>());
                }
                break;
        }

        DhPlace dhPlace = new DhPlace();
        dhPlace.setImageUrlsHigh(existingImgUrlsHighList);
        dhPlace.setImageUrlsLow(existingImgUrlsLowList);
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
    public Response<DhPromotions> uploadPromotionImagesVideosThumbnails(HttpHeaders httpHeaders, Authentication authentication, String placeName, String placeCategory, String promotionType, String placeId, String addedBy, String promotionTitle, String promotionDesc, String fullAddress, String fullName, String offerStartTime, String offerEndTime, boolean areDetailsSameAsRegistered, String mobile, String email, MultipartFile[] promotionImagesLow, MultipartFile[] promotionImagesHigh, MultipartFile[] promotionThumbnails, MultipartFile[] promotionVideoLowUrls, MultipartFile[] promotionVideoHighUrls, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        LOGGER.info("uploadPromotionImagesVideosThumbnails->imagesLow==null:" + (promotionImagesLow == null) + " imagesHigh==null:" + (promotionImagesHigh == null) + " videoLow==null:" + (promotionVideoLowUrls == null) + " videoHigh==null:" + (promotionVideoHighUrls == null) + " thumbnails==null:" + (promotionThumbnails == null));
        if (Utility.isFieldEmpty(promotionType) || Utility.isFieldEmpty(addedBy)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        if (!commonService.checkUserExistence(addedBy)) {
            return new Response<DhPromotions>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }

        if (promotionImagesHigh != null && promotionImagesHigh.length > AppConstants.PER_PROMOTION_MAX_IMAGES_LIMIT) {
            LOGGER.info("uploadPromotionImagesVideosThumbnails->promotionImagesHigh.length:" + promotionImagesHigh.length);
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_IMAGES_SIZE_GREATER_THAN_MAX), new ArrayList<>());
        }

        if (promotionVideoHighUrls != null && promotionVideoHighUrls.length > AppConstants.PER_PROMOTION_MAX_VIDEOS_LIMIT)
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_VIDEOS_SIZE_GREATER_THAN_MAX), new ArrayList<>());

        String uniquePromotionID = Utility.getUUID();
        String promotionImgUploadKeyLow = GetImageFoldersAndPrefix.getPromotionImgUploadKey(addedBy, uniquePromotionID, promotionType, false);
        String promotionImgUploadKeyHigh = GetImageFoldersAndPrefix.getPromotionImgUploadKey(addedBy, uniquePromotionID, promotionType, true);
        String promotionVideoUploadKeyLow = GetImageFoldersAndPrefix.getPromotionVideoUploadKey(addedBy, uniquePromotionID, promotionType, false);
        String promotionVideoUploadKeyHigh = GetImageFoldersAndPrefix.getPromotionVideoUploadKey(addedBy, uniquePromotionID, promotionType, true);
        String promotionVideoUploadKeyThumbnails = GetImageFoldersAndPrefix.getPromotionVideoUploadKey(addedBy, uniquePromotionID, promotionType, true);

        try {
            DhPromotions dhPromotions = new DhPromotions();
            dhPromotions.setPromotionId(uniquePromotionID);
            dhPromotions.setAddedBy(addedBy);
            if (promotionImagesLow != null && promotionImagesLow.length > 0) {
                List<String> promotionImageUrlsLow = amazonClient.uploadImagesToS3(promotionImgUploadKeyLow, promotionImagesLow, false);
                dhPromotions.setPromotionImagesLow(promotionImageUrlsLow);
            }

            if (promotionImagesHigh != null && promotionImagesHigh.length > 0) {
                List<String> promotionImageUrlsHigh = amazonClient.uploadImagesToS3(promotionImgUploadKeyHigh, promotionImagesHigh, false);
                dhPromotions.setPromotionImagesHigh(promotionImageUrlsHigh);
            }

            if (promotionVideoLowUrls != null && promotionVideoLowUrls.length > 0) {
                LOGGER.info("Uploading promotion video low,");
                List<String> promotionVideorlsLow = amazonClient.uploadImagesToS3(promotionVideoUploadKeyLow, promotionVideoLowUrls, true);
                dhPromotions.setVideoUrlsLow(promotionVideorlsLow);
            }

            if (promotionVideoHighUrls != null && promotionVideoHighUrls.length > 0) {
                LOGGER.info("Uploading promotion video high,");
                List<String> promotionVideoUrlsHigh = amazonClient.uploadImagesToS3(promotionVideoUploadKeyHigh, promotionVideoHighUrls, true);
                dhPromotions.setVideoUrlsHigh(promotionVideoUrlsHigh);
            }

           /* if (promotionThumbnails != null && promotionThumbnails.length > 0) {
                List<String> postVideoThumbnailUrlsHigh = amazonClient.uploadImagesToS3(promotionVideoUploadKeyThumbnails, promotionThumbnails, false);
                dhPromotions.setVideoThumbnails(postVideoThumbnailUrlsHigh);
            }*/
            if (!Utility.isFieldEmpty(placeId)) {
                dhPromotions.setPlaceId(placeId);
                dhPromotions.setPlaceName(placeName);
                dhPromotions.setPlaceCategory(placeCategory);
            }
            dhPromotions.setAddedBy(addedBy);
            dhPromotions.setAreDetailsSameAsRegistered(areDetailsSameAsRegistered);
            Contact contact = new Contact();
            contact.setMobile(mobile);
            contact.setEmail(email);
            dhPromotions.setContactDetails(contact);
            dhPromotions.setFullAddress(fullAddress);
            dhPromotions.setOfferEndTime(offerEndTime);
            dhPromotions.setOfferStartTime(offerStartTime);
            dhPromotions.setPromotionDesc(promotionDesc);
            dhPromotions.setPromotionId(uniquePromotionID);
            dhPromotions.setPromotionTitle(promotionTitle);
            dhPromotions.setPromotionType(promotionType);
            dhPromotions = (DhPromotions) Utility.setCommonAttrs(dhPromotions, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(dhPromotions, AppConstants.COLLECTION_DH_PROMOTIONS);

            logService.addLog(new DhLog(addedBy, "Promotion images/videos/thumnails have been added"));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PROMOTION_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(dhPromotions), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadPromotionImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PROMOTION_IMAGES);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<ProductsWithPrices> uploadProductImages(HttpHeaders httpHeaders, Authentication authentication, String uniqueProductId, String placeType, String placeId, String addedBy, List<String> existingProductLowList, List<String> existingProductHighList, List<String> deletePosList, MultipartFile[] productImagesLow, MultipartFile[] productImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (Utility.isFieldEmpty(addedBy) || Utility.isFieldEmpty(placeType) || Utility.isFieldEmpty(placeId)) {
            LOGGER.info("uploadProductImages->returning 402 missing body");
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        if (!commonService.checkUserExistence(addedBy)) {
            return new Response<ProductsWithPrices>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }

        if (productImagesHigh.length > AppConstants.PER_PRODUCT_MAX_IMAGES_LIMIT)
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_IMAGES_SIZE_GREATER_THAN_MAX), new ArrayList<>());

        String postImgUploadKeyLow = GetImageFoldersAndPrefix.getProductImgUploadKey(uniqueProductId, placeType, placeId, uniqueProductId, false);
        String postImgUploadKeyHigh = GetImageFoldersAndPrefix.getProductImgUploadKey(uniqueProductId, placeType, placeId, uniqueProductId, true);

        try {
            List<String> tempExistingProductHighList = new ArrayList<>(existingProductHighList);
            List<String> tempExistingProductLowList = new ArrayList<>(existingProductLowList);
            for (int i = 0; i < tempExistingProductHighList.size(); i++) {
                LOGGER.info("deleting img high: pos= " + i + " size=" + tempExistingProductHighList.size());
                for (int j = 0; j < deletePosList.size(); j++) {
                    int deletePos = Integer.parseInt(deletePosList.get(j));
                    if (deletePos == i) {
                        amazonClient.deleteFileFromS3BucketUsingUrl(tempExistingProductHighList.get(i));
                        amazonClient.deleteFileFromS3BucketUsingUrl(tempExistingProductLowList.get(i));
                        existingProductHighList.remove(tempExistingProductHighList.get(i));
                        existingProductLowList.remove(tempExistingProductLowList.get(i));
                        LOGGER.info("deleting pos=" + deletePos + " and i=" + i);
                    }
                }
            }

            ProductsWithPrices productsWithPrices = new ProductsWithPrices();
            if (productImagesHigh.length > 0) {
                LOGGER.info("Uploading images " + productImagesHigh.length);
                List<String> uploadedProductImgsListLow = amazonClient.uploadImagesToS3(postImgUploadKeyLow, productImagesLow);
                List<String> uploadedProductImgsListHigh = amazonClient.uploadImagesToS3(postImgUploadKeyHigh, productImagesHigh);
                productsWithPrices.setImgUrlsLow(uploadedProductImgsListLow);
                productsWithPrices.setImgUrlsHigh(uploadedProductImgsListHigh);
            } else {
                productsWithPrices.setImgUrlsLow(new ArrayList<>());
                productsWithPrices.setImgUrlsHigh(new ArrayList<>());
            }
            productsWithPrices.getImgUrlsHigh().addAll(existingProductHighList);
            productsWithPrices.getImgUrlsLow().addAll(existingProductLowList);
            logService.addLog(new DhLog(addedBy, "Product images have been added for placeId=" + placeId + " productId=%s" + uniqueProductId));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(productsWithPrices), 1);
        } catch (Exception ioException) {
            ioException.printStackTrace();
            LOGGER.info("ImageServiceImpl->uploadProductImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PRODUCT_IMAGES);
            return new Response<>(false, 402, errorMsg, new ArrayList<>());
        }
    }

    @Override
    public Response<DhHHPost> uploadHhPostImages(HttpHeaders httpHeaders, Authentication authentication, String addedBy, MultipartFile[] postImagesLow, MultipartFile[] postImagesHigh, String version) throws ServerSideException {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        LOGGER.info("uploadHhPostImages->postImagesHigh==null:" + (postImagesHigh == null) + " addedBy:" + addedBy);
        if (postImagesHigh == null || postImagesHigh.length == 0 || Utility.isFieldEmpty(addedBy))
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());

        LOGGER.info("uploadHhPostImages->passed 1st validation");
        if (!commonService.checkUserExistence(addedBy)) {
            return new Response<DhHHPost>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID), new ArrayList<>(), 0);
        }
        LOGGER.info("uploadHhPostImages->passed 2nd validation");
        if (postImagesHigh.length > AppConstants.HH_PER_POST_IMG_LIMIT)
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_IMAGES_SIZE_GREATER_THAN_MAX), new ArrayList<>());

        LOGGER.info("uploadHhPostImages->passed 3rd validation");
        String uniquePostId = Utility.getUUID();
        String postImgUploadKeyLow = GetImageFoldersAndPrefix.getHhPostImgUploadKey(addedBy, uniquePostId, false);
        String postImgUploadKeyHigh = GetImageFoldersAndPrefix.getHhPostImgUploadKey(addedBy, uniquePostId, true);

        try {
            List<String> postImageUrlsLow = amazonClient.uploadImagesToS3(postImgUploadKeyLow, postImagesLow);
            List<String> postImageUrlsHigh = amazonClient.uploadImagesToS3(postImgUploadKeyHigh, postImagesHigh);
            DhHHPost dhHHPost = new DhHHPost();
            dhHHPost.setPostId(uniquePostId);
            dhHHPost.setUserId(addedBy);
            dhHHPost.setImgUrlLow(postImageUrlsLow);
            dhHHPost.setImgUrlHigh(postImageUrlsHigh);
            logService.addLog(new DhLog(addedBy, "Helping Hands Post images have been added"));
            String successMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_HH_POST_IMAGES_ADDED);
            return new Response<>(true, 201, successMsg, Collections.singletonList(dhHHPost), 1);
        } catch (Exception ioException) {
            LOGGER.info("ImageServiceImpl->uploadPlaceImages : exception = " + ioException.getMessage());
            String errorMsg = ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_HH_POST_IMAGES);
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
