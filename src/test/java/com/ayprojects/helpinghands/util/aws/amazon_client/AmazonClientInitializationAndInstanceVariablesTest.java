package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AmazonClientInitializationAndInstanceVariablesTest {

    @Autowired
    private AmazonClient amazonClient;

    @Test
    void allInstanceVariablesShouldBeRetrievedCorrectly() {
        String bucketName = "helping-hands-data";
        String region = "ap-south-1";
        String endpointUrl = "https://s3.ap-south-1.amazonaws.com";
        assertEquals(bucketName, amazonClient.getBucketName());
        assertEquals(region, amazonClient.getRegion());
        assertEquals(endpointUrl, amazonClient.getEndpointUrl());
    }


    @Test
    void s3ClientShouldBeInitialized() {
        amazonClient.initializeAmazon();
        assertNotNull(amazonClient.getS3Client());
    }

}
