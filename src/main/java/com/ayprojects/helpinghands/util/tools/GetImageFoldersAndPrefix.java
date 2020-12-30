package com.ayprojects.helpinghands.util.tools;

import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

public class GetImageFoldersAndPrefix {
    static String imagesBaseFolder="app_images";
    static String userDir = "user";
    static String userInitial = "USER_";

    public static String getUserImgUploadKey(String uniqueUserID) {
        if(Utility.isFieldEmpty(uniqueUserID))
            throw new IllegalArgumentException("uniqueUserID must not be null !");
        return String.format("%s/%s/%s/%s%s/", imagesBaseFolder, uniqueUserID, userDir,userInitial,uniqueUserID);
    }

}
