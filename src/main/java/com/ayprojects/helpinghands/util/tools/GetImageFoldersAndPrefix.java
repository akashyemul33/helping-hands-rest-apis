package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

public class GetImageFoldersAndPrefix {
    static final String imagesBaseFolder = "app_images";
    static final String userDir = "user";
    static final String userInitial = "USER_";
    static final String placeDir = "places";
    static final String postDir = "posts";
    static final String productDir = "products";
    static final String placeInitial = "PLCS";
    static final String businessImgInitial = "B";
    static final String publicImgInitial = "P";
    static final String productImgInitial = "PRD";
    static final String regexBusinessPlace = "^[Bb]usiness[\\s]*[Pp]lace$";
    static final String regexBusinessPost = "^[Bb]usiness[\\s]*[Pp]ost$";
    static String postInitial = "PSTS";

    public static String getUserImgUploadKeyLow(String uniqueUserID, boolean isHigh) {
        if (Utility.isFieldEmpty(uniqueUserID))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        String lowHighQualityFolder = isHigh ? "1" : "0";
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
        String lowHighQualityFolder = isHigh ? "1" : "0";
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, userId, placeDir, newPlaceType, uniquePlaceID, "place_images", lowHighQualityFolder, imgType, placeInitial, uniquePlaceID);
    }

    public static String getPostImgUploadKey(String userId, String uniquePostId, String postType, boolean isHigh) {
        if (Utility.isFieldEmpty(uniquePostId))
            throw new IllegalArgumentException("UniquePostId must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        if (Utility.isFieldEmpty(postType))
            throw new IllegalArgumentException("PostType must not be null !");

        String imgType = postType.matches(regexBusinessPost) ? businessImgInitial : publicImgInitial;
        String newPostType = postType.matches(regexBusinessPost) ? AppConstants.BUSINESS_PROMOTION : AppConstants.PUBLIC_PROMOTION;
        String lowHighQualityFolder = isHigh ? "1" : "0";
        return String.format("%s/%s/%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, userId, postDir, newPostType, uniquePostId, lowHighQualityFolder, imgType, postInitial, uniquePostId);
    }

    public static String getProductImgUploadKey(String userId, String placeType, String uniquePlaceId, String uniqueProductId, boolean isHigh) {
        if (Utility.isFieldEmpty(uniqueProductId))
            throw new IllegalArgumentException("UniqueProductId must not be null !");
        if (Utility.isFieldEmpty(userId))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        if (Utility.isFieldEmpty(uniquePlaceId))
            throw new IllegalArgumentException("UniquePlaceId must not be null !");

        String newPlaceType = placeType.matches(regexBusinessPlace) ? AppConstants.BUSINESS_PLACE : AppConstants.PUBLIC_PLACE;
        String lowHighQualityFolder = isHigh ? "1" : "0";

        return String.format("%s/%s/%s/%s/%s/%s/%s/%s/%s_%s_", imagesBaseFolder, userId, placeDir, newPlaceType, uniquePlaceId, productDir, uniqueProductId, lowHighQualityFolder, productImgInitial, uniqueProductId);
    }
}
