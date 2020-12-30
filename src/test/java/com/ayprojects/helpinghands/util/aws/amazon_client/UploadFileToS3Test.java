package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UploadFileToS3Test {

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
    void givenEmptyS3ClientThenException(){
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(null,bucketName,"abc.png",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });
    }

    @Test
    void givenEmptyBucketNameThenException(){
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(s3Client,null,"abc.png",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(s3Client,"","abc.png",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });
    }

    @Test
    void givenEmptyFileNameThenException(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(s3Client,bucketName,"",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(s3Client,bucketName,null,new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });
    }

    @Test
    void givenEmptyFileThenException(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(s3Client,bucketName,"abc.xyz",null);
            }
        });
    }

    @Test
    void fileShouldBeUploadedWhenValidInput(){
        PutObjectResult result = amazonClient.uploadFileTos3bucket(s3Client, bucketName, "abc.xyz", new File("/home/ay/Desktop/delta_counts_2309.txt"));
        assertNotNull(result);
    }
}
