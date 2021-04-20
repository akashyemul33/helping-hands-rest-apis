package com.ayprojects.helpinghands.util.tools.get_imgfolder_prefix;

import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class GetUserImgUploadKeyMethodTest {

    @Test
    void givenEmptyUserIdThenException(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getUserImgUploadKeyLow(null,false);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getUserImgUploadKeyLow("",false);
            }
        });    }

    @Test
    void givenUserIdThenValidString(){
        String userDir = "user";
        String userInitial = "USER_";
        String imagesBaseFolder = "app_images";
        String userImgRegex = String.format("%s/.*/%s/%s.*/", imagesBaseFolder,userDir,userInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getUserImgUploadKeyLow(Utility.getUUID(),false);
        LOGGER.info("imgUploadKey="+imgUploadKey);
        assertTrue(imgUploadKey.matches(userImgRegex));
    }


}
