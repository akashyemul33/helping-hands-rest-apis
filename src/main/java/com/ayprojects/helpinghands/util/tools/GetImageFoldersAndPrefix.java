package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

public class GetImageFoldersAndPrefix {
    static final String imagesBaseFolder = "app_images";
    static final String thoughtsFolder = "thoughts";
    static final String userDir = "user";
    static final String userInitial = "USER_";
    static final String placeDir = "places";
    static final String hhPostDir = "hhposts";
    static final String promotionDir = "promotions";
    static final String productDir = "products";
    static final String placeInitial = "PLCS";
    static final String hhPostsInitial = "HHPSTS";
    static final String businessImgInitial = "B";
    static final String publicImgInitial = "P";
    static final String productImgInitial = "PRD";
    static final String regexBusinessPlace = "^[Bb]usiness[\\s]*[Pp]lace$";
    static final String regexBusinessPromotion = "^[Bb]usiness[\\s]*[Pp]romotion";
    private static final String videosBaseFolder = "app_videos";
    static String promotionInitial = "PRMTNS";

    public static String getThoughtImgUploadKeyLow(String uniqueThoughtId, boolean isHigh) {
        if (Utility.isFieldEmpty(uniqueThoughtId))
            throw new IllegalArgumentException("UniqueThoughtId must not be null !");
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        String date = CalendarOperations.currentDateInUTCWithoutDelimeter();
        return String.format("%s/%s/%s/%s/%s_", imagesBaseFolder, thoughtsFolder, lowHighQualityFolder, date, uniqueThoughtId);
    }

    public static String getUserImgUploadKeyLow(String uniqueUserID, boolean isHigh) {
        if (Utility.isFieldEmpty(uniqueUserID))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        return String.format("%s/%s/%s/%s/%s%s_", imagesBaseFolder, uniqueUserID, userDir, lowHighQualityFolder, userInitial, uniqueUserID);
    }

    public static String getPlaceImgUploadKey(String userId, String uniquePlaceID, String placeType, boolean isHigh) {
        if (Utility.isFieldEmpty(uniquePlaceID))
            throw new IllegalArgumentException("UniquePlaceID must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        if (Utility.isFieldEmpty(placeType))
            throw new IllegalArgumentException("PlaceType must not be null !");

        String imgType = placeType.matches(regexBusinessPlace) ? businessImgInitial : publicImgInitial;
        String newPlaceType = placeType.matches(regexBusinessPlace) ? AppConstants.BUSINESS_PLACE : AppConstants.PUBLIC_PLACE;
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, userId, placeDir, newPlaceType, uniquePlaceID, "place_images", lowHighQualityFolder, imgType, placeInitial, uniquePlaceID);
    }

    public static String getHhPostImgUploadKey(String userId, String uniquePostID, boolean isHigh) {
        if (Utility.isFieldEmpty(uniquePostID))
            throw new IllegalArgumentException("HH UniquePostID must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("HH UniqueUserID must not be null !");

        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        return String.format("%s/%s/%s/%s/%s/%s/%s_%s_", imagesBaseFolder, userId, hhPostDir, uniquePostID, "hh_post_images", lowHighQualityFolder, hhPostsInitial, uniquePostID);
    }

    public static String getPromotionImgUploadKey(String userId, String uniquePromotioId, String promotionType, boolean isHigh) {
        if (Utility.isFieldEmpty(uniquePromotioId))
            throw new IllegalArgumentException("uniquePromotioId must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("uniquePromotioId must not be null !");
        if (Utility.isFieldEmpty(promotionType))
            throw new IllegalArgumentException("PromotionType must not be null !");

        String imgType = promotionType.matches(regexBusinessPromotion) ? businessImgInitial : publicImgInitial;
        String newPromotionType = promotionType.matches(regexBusinessPromotion) ? AppConstants.BUSINESS_PROMOTION : AppConstants.PUBLIC_PROMOTION;
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, userId, promotionDir, newPromotionType, uniquePromotioId, "images", lowHighQualityFolder, imgType, promotionInitial, uniquePromotioId);
    }

    public static String getPromotionVideoUploadKey(String userId, String uniquePromotioId, String promotionType, boolean isHigh) {
        if (Utility.isFieldEmpty(uniquePromotioId))
            throw new IllegalArgumentException("uniquePromotioId must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("uniquePromotioId must not be null !");
        if (Utility.isFieldEmpty(promotionType))
            throw new IllegalArgumentException("PromotionType must not be null !");

        String imgType = promotionType.matches(regexBusinessPromotion) ? businessImgInitial : publicImgInitial;
        String newPromotionType = promotionType.matches(regexBusinessPromotion) ? AppConstants.BUSINESS_PROMOTION : AppConstants.PUBLIC_PROMOTION;
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s_%s_%s_", videosBaseFolder, userId, promotionDir, newPromotionType, uniquePromotioId, "videos", lowHighQualityFolder, imgType, promotionInitial, uniquePromotioId);
    }

    public static String getProductImgUploadKey(String userId, String placeType, String uniquePlaceId, String uniqueProductId, boolean isHigh) {
        if (Utility.isFieldEmpty(uniqueProductId))
            throw new IllegalArgumentException("UniqueProductId must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        if (Utility.isFieldEmpty(uniquePlaceId))
            throw new IllegalArgumentException("UniquePlaceId must not be null !");

        String newPlaceType = placeType.matches(regexBusinessPlace) ? AppConstants.BUSINESS_PLACE : AppConstants.PUBLIC_PLACE;
        String lowHighQualityFolder = isHigh ? AppConstants.DIR_FOR_HIGH : AppConstants.DIR_FOR_LOW;

        return String.format("%s/%s/%s/%s/%s/%s/%s/%s/%s_%s_", imagesBaseFolder, userId, placeDir, newPlaceType, uniquePlaceId, productDir, uniqueProductId, lowHighQualityFolder, productImgInitial, uniqueProductId);
    }
}
