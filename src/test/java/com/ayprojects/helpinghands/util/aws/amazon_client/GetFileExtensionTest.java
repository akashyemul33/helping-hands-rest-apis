package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GetFileExtensionTest {

    @Autowired
    private AmazonClient amazonClient;

    @Test
    void givenEmptyOriginalFileNameAndEmptyFileTypeThenException() {
        String fileType = "";
        String filename = "";
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getFileExtension(filename, fileType);
            }
        });

        String fileType2 = null;
        String filename2 = null;
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getFileExtension(filename2, fileType2);
            }
        });

    }

    @Test
    void givenOriginalFileNameAndEmptyFileTypeThenFileNameExtension() {
        String fileType = "";
        String filename = "abc.png";
        String result = "png";
        assertEquals(result, amazonClient.getFileExtension(filename, fileType));
    }

    @Test
    void givenEmptyOriginalFileNameAndNonEmptyFileTypeThenFileNameExtension() {
        String fileType = "png";
        String filename = null;
        assertEquals(fileType, amazonClient.getFileExtension(filename, fileType));
    }

    @Test
    void fileExtensionShouldBeOfImageOnlyForNow() {
        String[] validImgFormats = new String[]{"[pP][nN][gG]", "[jJ][pP][eE][gG]","[sS][vV][gG]", "[jJ][pP][gG]"};
        String extension = amazonClient.getFileExtension("abc.Png", null);
        LOGGER.info("extension=" + extension);
        boolean b = false;
        for (String s : validImgFormats) {
            if (extension.matches(s)) {
                b = true;
            }
        }

        assertTrue(b);
    }



    @Test
    void givenInvalidExtensionThenException(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getFileExtension("abc.Pnga", null);
            }
        });
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.getFileExtension("abc.text", null);
            }
        });
    }

    @Test
    void givenValidExtensionThenValue(){
        assertEquals("png",amazonClient.getFileExtension("abc.Png", null));
        assertEquals("png",amazonClient.getFileExtension("", AppConstants.FILETYPE_PNG));
        assertEquals("png",amazonClient.getFileExtension("ab", AppConstants.FILETYPE_PNG));
        assertEquals("jpeg",amazonClient.getFileExtension("ab.jpeg",null));
        assertEquals("jpeg",amazonClient.getFileExtension("ab.jpeg",null));
        assertEquals("svg",amazonClient.getFileExtension("ab.svg",AppConstants.FILETYPE_PNG));
    }

}
