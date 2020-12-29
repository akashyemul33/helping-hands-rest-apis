package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetFileExtensionTest {

    private static AmazonClient amazonClient;

    @BeforeAll
    static void setup() {
        amazonClient = new AmazonClient();
    }


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
        String[] validImgFormats = new String[]{"[pP][nN][gG]", "[jJ][pP][gG][eE]", "[jJ][pP][gG]"};
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

    /*@Test
    void givenNullOriginalFileNameThenFileType(){
        String fileType = "png";
        String filename = "";
        assertEquals(fileType,amazonClient.getFileExtension(filename,fileType));
    }*/

}
