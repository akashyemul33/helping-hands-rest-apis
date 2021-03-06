package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UploadMultipleImagesToS3Test {
    @Autowired
    private AmazonClient amazonClient;
    private final String bucketName = "helping-hands-data";
    private final String endpointUrl = "https://s3.ap-south-1.amazonaws.com";
    private final String region = "ap-south-1";

    @Test
    void givenEmptyImagesThenException() throws IOException {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, null, null);
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, null, new MultipartFile[0]);
            }
        });

    }

    @Test
    void givenEmptyS3ClientThenException() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(null, bucketName, endpointUrl, null, null);
            }
        });
    }

    @Test
    void givenEmptyBucketNameThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), null, endpointUrl, null, null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), "", endpointUrl, null, null);
            }
        });
    }

    @Test
    void givenEmptyEndpointUrlThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, null, null, null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, "", null, null);
            }
        });
    }

    @Test
    void givenEmptyImgUploadKeyThenException() throws IOException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile};
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, null, multipartFiles);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, "", multipartFiles);
            }
        });
    }

    @Test
    void givenValidDetailsWithSingleImgThenSucceed() throws Exception {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile};
        String imgUploadFolder = "abc/temp_";
        String ext = "svg";
        String objKey = String.format("%s%s.%s",
                imgUploadFolder, Calendar.getInstance().getTimeInMillis(), ext);
        String resultFileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
        LOGGER.info("resultFileUrl=" + resultFileUrl);
        String regexResultMatch = String.format("^%s/%s/%s.*.%s$",
                endpointUrl, bucketName, imgUploadFolder, ext);
        List<String> retruned = amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, imgUploadFolder, multipartFiles);
        LOGGER.info("returned=" + retruned.get(0));
        assertTrue(retruned.get(0).matches(regexResultMatch));

    }

    @Test
    void givenValidDetailsWithMultipleImgsThenSucceed() throws Exception {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        InputStream inputStream2 = new FileInputStream("/home/ay/Desktop/sad_2.svg");
        InputStream inputStream3 = new FileInputStream("/home/ay/Desktop/sad_3.svg");
        MultipartFile multipartFile1 = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        MultipartFile multipartFile2 = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream2);
        MultipartFile multipartFile3 = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream3);
        MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile1, multipartFile2, multipartFile3};
        String imgUploadFolder = "abc/temp_";
        String ext = "svg";
        String objKey = String.format("%s%s.%s",
                imgUploadFolder, Calendar.getInstance().getTimeInMillis(), ext);
        String resultFileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
        LOGGER.info("resultFileUrl=" + resultFileUrl);
        String regexResultMatch = String.format("^%s/%s/%s.*.%s$",
                endpointUrl, bucketName, imgUploadFolder, ext);
        List<String> retruned = amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, imgUploadFolder, multipartFiles);
        for (String str : retruned) {
            LOGGER.info("returned=" + str);
            assertTrue(str.matches(regexResultMatch));
        }


    }
    /*@Test
    void singleImgShouldBeUploadedWithProperRetrunUrl() throws Exception {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        String imgUploadFolder = "abc";
        String imgPrefix = "temp_";
        String ext = "svg";
        String objKey = String.format("%s%s%s.%s",
                imgUploadFolder, imgPrefix, Calendar.getInstance().getTimeInMillis(), ext);
        String resultFileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
        LOGGER.info("resultFileUrl=" + resultFileUrl);
        String regexResultMatch = String.format("^%s/%s/%s%s.*.%s$",
                endpointUrl, bucketName, imgUploadFolder, imgPrefix, ext);
        String retruned = amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, imgUploadFolder, multipartFile, imgPrefix);
        LOGGER.info("returned=" + retruned);
        assertTrue(retruned.matches(regexResultMatch));
    }

    @Test
    void givenFileWithoutExtensionThenException() throws IOException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.txt", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        String imgUploadFolder = "abc";
        String imgPrefix = "temp_";
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadImagesToS3(amazonClient.getS3Client(), bucketName, endpointUrl, imgUploadFolder, multipartFile, imgPrefix);
            }
        });
    }*/
}
