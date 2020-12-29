package com.ayprojects.helpinghands.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.rmi.CORBA.Util;

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
    public void initializeAmazon() {
        this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("Got null arguement for MultipartFile");
        }
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }


    public String getFileExtension(String fileName, String typeOfFile) {
        if (Utility.isFieldEmpty(fileName)){
            if(Utility.isFieldEmpty(typeOfFile)) {
                throw new NullPointerException("Got both filename and typeOfFile empty");
            }
            else{
                return typeOfFile;
            }
        }
        return FilenameUtils.getExtension(fileName);
    }

    public List<String> uplodImages(String imgUploadFolder, MultipartFile[] multipartImages, String imagePrefix) throws IOException {
        List<String> uploadedImageNames = new ArrayList<>();
        if (multipartImages == null || multipartImages.length == 0) return uploadedImageNames;

        for (MultipartFile multipartFile : multipartImages) {

            File file = convertMultipartFile(multipartFile);

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

    public AmazonS3 getS3Client() {
        return s3Client;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getRegion() {
        return region;
    }

}