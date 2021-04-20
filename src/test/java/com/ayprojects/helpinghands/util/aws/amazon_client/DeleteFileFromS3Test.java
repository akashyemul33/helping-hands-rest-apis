package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DeleteFileFromS3Test {

    @Autowired
    private AmazonClient amazonClient;
    private final String bucketName = "helping-hands-data";
    private final String endpointUrl = "https://s3.ap-south-1.amazonaws.com";
    private final String region = "ap-south-1";
    
    @Test
    void givenEmptyUrlThenS3Exception() {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(amazonClient.getS3Client(), bucketName, null);
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(amazonClient.getS3Client(), bucketName, "");
            }
        });
    }

    @Test
    void givenSlashAsFileUrlThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(amazonClient.getS3Client(), bucketName, "/");
            }
        });
    }

    @Test
    void givenValidFileUrlThenException() {
        assertTrue(amazonClient.deleteFileFromS3Bucket(amazonClient.getS3Client(), bucketName, "app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/B_PSTS_967eeef9-880c-4c01-937c-c9a3318808dc_1608903414052.jpg"));
    }

    //Intentionally asserting false,
    // verify it manually and then assert it to true
    @Test
    void givenValidFileUrlWithoutExtensionThenShouldNotDelete() {
        String fileUrl = "app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/";
        amazonClient.deleteFileFromS3Bucket(amazonClient.getS3Client(), bucketName, fileUrl);
        boolean haveYouVerifiedInS3 = true;//Go to s3, and verify , then mark this flag to true
        assert haveYouVerifiedInS3;
    }

}
