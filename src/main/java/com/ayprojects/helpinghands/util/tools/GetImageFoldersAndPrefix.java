package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.AppConstants;

public class GetImageFoldersAndPrefix {
    static final String imagesBaseFolder = "app_images";
    static final String userDir = "user";
    static final String userInitial = "USER_";
    static final String placeDir = "places";
    static final String postDir = "posts";
    static String postInitial = "PSTS";
    static final String placeInitial = "PLCS";
    static final String businessImgInitial = "B";
    static final String publicImgInitial = "P";
    static final String regexBusinessPlace = "^[Bb]usiness[\\s]*[Pp]lace$";
    static final String regexBusinessPost = "^[Bb]usiness[\\s]*[Pp]ost$";

    public static String getUserImgUploadKey(String uniqueUserID) {
        if (Utility.isFieldEmpty(uniqueUserID))
            throw new IllegalArgumentException("UniqueUserID must not be null !");
        return String.format("%s/%s/%s/%s%s_", imagesBaseFolder, uniqueUserID, userDir, userInitial, uniqueUserID);
    }

    public static String getPlaceImgUploadKey(String uniquePlaceID, String placeType) {
        if (Utility.isFieldEmpty(uniquePlaceID))
            throw new IllegalArgumentException("UniquePlaceID must not be null !");
        if (Utility.isFieldEmpty(placeType))
            throw new IllegalArgumentException("PlaceType must not be null !");

        String imgType = placeType.matches(regexBusinessPlace) ? businessImgInitial : publicImgInitial;
        String newPlaceType = placeType.matches(regexBusinessPlace) ? AppConstants.BUSINESS_PLACE : AppConstants.PUBLIC_PLACE;
        return String.format("%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, uniquePlaceID, placeDir, newPlaceType, imgType, placeInitial, uniquePlaceID);
    }

    public static String getPostImgUploadKey(String uniquePostId, String postType) {
        if (Utility.isFieldEmpty(uniquePostId))
            throw new IllegalArgumentException("UniquePostId must not be null !");
        if (Utility.isFieldEmpty(postType))
            throw new IllegalArgumentException("PostType must not be null !");

        String imgType = postType.matches(regexBusinessPost) ? businessImgInitial : publicImgInitial;
        String newPostType = postType.matches(regexBusinessPost) ? AppConstants.BUSINESS_POST : AppConstants.PUBLIC_POST;
        return String.format("%s/%s/%s/%s/%s_%s_%s_", imagesBaseFolder, uniquePostId, postDir, newPostType, imgType, postInitial, uniquePostId);
    }

}
