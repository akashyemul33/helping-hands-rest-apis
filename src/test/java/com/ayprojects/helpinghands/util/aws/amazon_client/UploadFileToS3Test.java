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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UploadFileToS3Test {

    @Autowired
    private AmazonClient amazonClient;
    private final String bucketName = "helping-hands-data";
    private final String endpointUrl = "https://s3.ap-south-1.amazonaws.com";
    private final String region = "ap-south-1";
    
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
                amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(),null,"abc.png",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(),"","abc.png",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });
    }

    @Test
    void givenEmptyFileNameThenException(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(),bucketName,"",new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(),bucketName,null,new File("/home/ay/Desktop/delta_counts_2309.txt"));
            }
        });
    }

    @Test
    void givenEmptyFileThenException(){
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(),bucketName,"abc.xyz",null);
            }
        });
    }

    @Test
    void fileShouldBeUploadedWhenValidInput(){
        PutObjectResult result = amazonClient.uploadFileTos3bucket(amazonClient.getS3Client(), bucketName, "abc.xyz", new File("/home/ay/Desktop/delta_counts_2309.txt"));
        assertNotNull(result);
    }
}
