package com.ayprojects.helpinghands.util.tools.get_imgfolder_prefix;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetPlaceImgsUploadKeyMethodTest {

    @Test
    void givenEmptyPlaceIdThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPlaceImgUploadKey(null, null,false);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPlaceImgUploadKey("", null,false);
            }
        });
    }

    @Test
    void givenEmptyPlaceTypeThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPlaceImgUploadKey("abc34", "",false);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getPlaceImgUploadKey("abc34", null,false);
            }
        });
    }

    @Test
    void imgShouldBeUploadedAsPublicIfInvalidPlaceType() {
        String imgType = "P";
        String imagesBaseFolder = "app_images";
        String placeDir = "places";
        String placeType = AppConstants.PUBLIC_PLACE;
        String placeInitial = "PLCS";
        String placeImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, placeDir, placeType, imgType, placeInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPlaceImgUploadKey("abc123", "adsflkjsdf",false);
        LOGGER.info("placeImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(placeImgRegex));
    }

    @Test
    void givenBusinessPlaceThenValidString() {
        String imgType = "B";
        String imagesBaseFolder = "app_images";
        String placeDir = "places";
        String placeType = AppConstants.BUSINESS_PLACE;
        String placeInitial = "PLCS";
        String placeImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, placeDir, placeType, imgType, placeInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPlaceImgUploadKey(Utility.getUUID(), AppConstants.BUSINESS_PLACE,false);
        LOGGER.info("placeImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(placeImgRegex));

    }

    @Test
    void givenPublicPlaceThenValidString() {
        String imgType = "P";
        String imagesBaseFolder = "app_images";
        String placeDir = "places";
        String placeType = AppConstants.PUBLIC_PLACE;
        String placeInitial = "PLCS";
        String placeImgRegex = String.format("%s/.*/%s/%s/%s_%s_.*_", imagesBaseFolder, placeDir, placeType, imgType, placeInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getPlaceImgUploadKey(Utility.getUUID(), AppConstants.PUBLIC_PLACE,false);
        LOGGER.info("placeImgUploadKey=" + imgUploadKey);
        assertTrue(imgUploadKey.matches(placeImgRegex));

    }

}
