package com.ayprojects.helpinghands.util.tools.get_imgfolder_prefix;

import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import javax.rmi.CORBA.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

public class GetImgFolderAndPrefixTest {

    @Test
    void givenEmptyUserIdThenException(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getUserImgUploadKey(null);
            }
        });


        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                GetImageFoldersAndPrefix.getUserImgUploadKey("");
            }
        });    }

    @Test
    void givenUserIdThenValidString(){
        String userDir = "user";
        String userInitial = "USER_";
        String imagesBaseFolder = "app_images";
        String userImgRegex = String.format("%s/.*/%s/%s.*/", imagesBaseFolder,userDir,userInitial);
        String imgUploadKey = GetImageFoldersAndPrefix.getUserImgUploadKey(Utility.getUUID());
        LOGGER.info("imgUploadKey="+imgUploadKey);
        assertTrue(imgUploadKey.matches(userImgRegex));
    }


}
