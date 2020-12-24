package com.ayprojects.helpinghands.services.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class AmazonClient {

    private AmazonS3 s3Client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        LOGGER.info("initializeAmazon->endPointUrl="+endpointUrl);
        LOGGER.info("initializeAmazon->bucketName="+bucketName);
        LOGGER.info("initializeAmazon->region="+region);

        this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

    public String uploadFile(MultipartFile multipartFile,String fileUrl) {

        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public List<String> uplodImages(String imgUploadFolder, MultipartFile[] multipartImages, String imagePrefix) throws IOException {
        List<String> uploadedImageNames = new ArrayList<>();
        if (multipartImages == null || multipartImages.length == 0) return uploadedImageNames;

        for (MultipartFile multipartFile : multipartImages) {

            File file = convertMultiPartToFile(multipartFile);

            //create s3 obj key
            String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String objKey = imgUploadFolder + imagePrefix + Calendar.getInstance().getTimeInMillis() + "." + ext;

            //upload file to s3 bucket
            uploadFileTos3bucket(objKey, file);

            //add uploaded file s3 url to list
            String fileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
            LOGGER.info("Utility->uplodImages : imgUploadFolderWithFile = " + fileUrl);
            uploadedImageNames.add(fileUrl);
            file.delete();
        }
        return uploadedImageNames;
    }

}