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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadSingleImageToS3Test {
    private static AmazonClient amazonClient;
    private static AmazonS3 s3Client;
    private static String bucketName;
    private static String endpointUrl;
    private static String region;

    //Dont forget to remove access and secret key while commiting the code
    @BeforeAll
    static void setup() {
        amazonClient = new AmazonClient();
        bucketName = "helping-hands-data";
        endpointUrl = "https://s3.ap-south-1.amazonaws.com";
        region = "ap-south-1";
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AppConstants.ACCESS_KEY, AppConstants.SECRET_KEY);
        s3Client = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

    @Test
    void givenEmptyS3ClientThenException() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(null, bucketName, endpointUrl, null, null);
            }
        });
    }

    @Test
    void givenEmptyBucketNameThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, null, endpointUrl, null, null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, null, endpointUrl, null, null);
            }
        });
    }

    @Test
    void givenEmptyEndpointUrlThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, null, null, null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, null, null, null);
            }
        });
    }

    @Test
    void givenEmptyImgUploadKeyThenException() throws IOException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, endpointUrl, multipartFile, null);
            }
        });

        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, endpointUrl, multipartFile, "");
            }
        });
    }

    @Test
    void givenEmptyImagesThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, endpointUrl, null, null);
            }
        });
    }

    @Test
    void singleImgShouldBeUploadedWithProperRetrunUrl() throws Exception {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        String imgUploadKey = "abc/temp_";
        String ext = "svg";
        String objKey = String.format("%s%s.%s",
                imgUploadKey, Calendar.getInstance().getTimeInMillis(), ext);
        String resultFileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
        LOGGER.info("resultFileUrl=" + resultFileUrl);
        String regexResultMatch = String.format("^%s/%s/%s.*.%s$",
                endpointUrl, bucketName, imgUploadKey, ext);
        String retruned = amazonClient.uploadSingleImageToS3(s3Client, bucketName, endpointUrl, multipartFile,imgUploadKey);
        LOGGER.info("returned=" + retruned);
        assertTrue(retruned.matches(regexResultMatch));
    }

    @Test
    void givenFileWithoutExtensionThenException() throws IOException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.txt", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        String imgUploadKey = "abc/temp_";
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadSingleImageToS3(s3Client, bucketName, endpointUrl, multipartFile,imgUploadKey);
            }
        });
    }
}
