package com.ayprojects.helpinghands.util.tools.get_imgfolder_prefix;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetPostImgsUploadKeyMethodTest {

    @Test
    void givenEmptyPostIdThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPostImgUploadKey(null, null,false);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPostImgUploadKey("", null,false);
            }
        });
    }

    @Test
    void givenEmptyPostTypeThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPostImgUploadKey("abc34", "",false);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPostImgUploadKey("abc34", null,false);
            }
        });
    }

    @Test
    void imgShouldBeUploadedAsPublicIfInvalidPostType() {
        String imgType = "P";
        String imagesBaseFolder = "app_images";
        String postDir = "posts";
        String postType = AppConstants.PUBLIC_POST;
        String postInitial = "PSTS";
        String postImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, postDir, postType, imgType, postInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPostImgUploadKey("abc123", "adsflkjsdf",false);
        LOGGER.info("postImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(postImgRegex));
    }

    @Test
    void givenBusinessPostThenValidString() {
        String imgType = "B";
        String imagesBaseFolder = "app_images";
        String postDir = "posts";
        String postType = AppConstants.BUSINESS_POST;
        String postInitial = "PSTS";
        String postImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, postDir, postType, imgType, postInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPostImgUploadKey(Utility.getUUID(), AppConstants.BUSINESS_POST,false);
        LOGGER.info("postImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(postImgRegex));

    }

    @Test
    void givenPublicPostThenValidString() {
        String imgType = "P";
        String imagesBaseFolder = "app_images";
        String postDir = "posts";
        String postType = AppConstants.PUBLIC_POST;
        String postInitial = "PSTS";
        String postImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, postDir, postType, imgType, postInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPostImgUploadKey(Utility.getUUID(), AppConstants.PUBLIC_POST,false);
        LOGGER.info("postImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(postImgRegex));

    }

}
