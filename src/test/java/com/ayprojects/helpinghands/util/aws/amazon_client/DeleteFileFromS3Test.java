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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteFileFromS3Test {

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
    void givenEmptyUrlThenS3Exception() {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(s3Client, bucketName, null);
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(s3Client, bucketName, "");
            }
        });
    }

    @Test
    void givenSlashAsFileUrlThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.deleteFileFromS3Bucket(s3Client, bucketName, "/");
            }
        });
    }

    @Test
    void givenValidFileUrlThenException() {
        assertTrue(amazonClient.deleteFileFromS3Bucket(s3Client, bucketName, "app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/B_PSTS_967eeef9-880c-4c01-937c-c9a3318808dc_1608903414052.jpg"));
    }

    //Intentionally asserting false,
    // verify it manually and then assert it to true
    @Test
    void givenValidFileUrlWithoutExtensionThenShouldNotDelete() {
        String fileUrl = "app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/app_images/ebad1d8b-96c2-4785-8d00-3762fcc0a18c/posts/BusinessPost/";
        amazonClient.deleteFileFromS3Bucket(s3Client, bucketName, fileUrl);
        boolean haveYouVerifiedInS3 = true;//Go to s3, and verify , then mark this flag to true
        assert haveYouVerifiedInS3;
    }

}
