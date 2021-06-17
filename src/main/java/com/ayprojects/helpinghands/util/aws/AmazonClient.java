package com.ayprojects.helpinghands.util.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.AmazonS3URI;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.ayprojects.helpinghands.AppConstants;
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
        this.s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build(); //use this one

        /*//TODO remove these lines unnecessary for production
        try {
            Temp.getAwsKeys();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("initializeAmazon->exception="+ioException.getMessage());
        }
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(AppConstants.ACCESS_KEY, AppConstants.SECRET_KEY);
        s3Client = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

         */
    }

    public PutObjectResult uploadFileTos3bucket(AmazonS3 amazonS3, String bucketName, String fileName, File file) {
        if (amazonS3 == null)
            throw new NullPointerException("Got null AmazonS3 object as arguement !");
        return amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private PutObjectResult uploadFileTos3bucket(String fileName, File file) {
        return uploadFileTos3bucket(s3Client, bucketName, fileName, file);
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

    public boolean deleteFileFromS3BucketUsingUrl(String url) {
        return deleteFileFromS3BucketUsingUrl(s3Client, url);
    }

    private boolean deleteFileFromS3BucketUsingUrl(AmazonS3 amazonS3, String url) {
        if (amazonS3 == null)
            throw new NullPointerException("Got null AmazonS3 object as arguement !");
        if (Utility.isFieldEmpty(url) || url.equals("/"))
            throw new IllegalArgumentException("Invalid fileUrl !");
        AmazonS3URI s3uri = new AmazonS3URI(url);
        s3Client.deleteObject(s3uri.getBucket(), s3uri.getKey());
        return true;
    }

    public boolean deleteFileFromS3Bucket(AmazonS3 amazonS3, String bucketName, String key) {
        if (amazonS3 == null)
            throw new NullPointerException("Got null AmazonS3 object as arguement !");
        if (Utility.isFieldEmpty(key) || key.equals("/"))
            throw new IllegalArgumentException("Invalid key !");
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        return true;
    }

    public boolean deleteFileFromS3Bucket(String fileUrl) {
        return deleteFileFromS3Bucket(s3Client, bucketName, fileUrl);
    }


    public String getFileExtension(String fileName, String typeOfFile) {
        String extension = "";
        if (Utility.isFieldEmpty(fileName)) {
            if (Utility.isFieldEmpty(typeOfFile)) {
                throw new NullPointerException("Got both filename and typeOfFile empty");
            } else {
                extension = typeOfFile;
            }
        } else extension = FilenameUtils.getExtension(fileName);
        return getExtensionAfterValidation(extension);
    }

    public String getExtensionAfterValidation(String extension) {
        String[] validImgFormats = new String[]{"[pP][nN][gG]", "[jJ][pP][eE][gG]", "[jJ][pP][gG]", "[sS][vV][gG]", "[wW][eE][bB][pP]","[mM][pP]4"};
        if (Utility.isFieldEmpty(extension)) return AppConstants.FILETYPE_WEBP;

        for (String s : validImgFormats) {
            if (extension.matches(s)) {
                return extension.toLowerCase();
            }
        }
        throw new IllegalArgumentException("Invalid image file extension !");
    }

    public String uploadSingleImageToS3(String imgUploadKey, MultipartFile multipartFile) throws Exception {
        return uploadSingleImageToS3(s3Client, bucketName, endpointUrl, multipartFile, imgUploadKey);
    }

    public String uploadSingleImageToS3(AmazonS3 amazonS3, String bucketName, String endpointUrl, MultipartFile multipartFile, String imgUploadKey) throws Exception {
        return uploadSingleImageToS3(amazonS3,bucketName,endpointUrl,multipartFile,imgUploadKey,false);
    }

    public String uploadSingleImageToS3(AmazonS3 amazonS3, String bucketName, String endpointUrl, MultipartFile multipartFile, String imgUploadKey,boolean isVideo) throws Exception {
        if (amazonS3 == null)
            throw new NullPointerException("Got null AmazonS3 object as arguement !");
        if (Utility.isFieldEmpty(bucketName)) {
            throw new IllegalArgumentException("Empty bucketname !");
        }
        if (multipartFile == null) {
            throw new IllegalArgumentException("Got empty images as arguement !");
        }
        if (Utility.isFieldEmpty(endpointUrl)) {
            throw new IllegalArgumentException("Empty endpointurl !");
        }
        if (Utility.isFieldEmpty(imgUploadKey)) {
            throw new IllegalArgumentException("Empty imgUploadKey !");
        }

        File convertMultipartFile = convertMultipartFile(multipartFile);
        String ext = getFileExtension(multipartFile.getOriginalFilename(),isVideo?AppConstants.FILETYPE_MP4: AppConstants.FILETYPE_WEBP);
        String objKey = imgUploadKey + Calendar.getInstance().getTimeInMillis() + "." + ext;
        //upload file to s3 bucket
        uploadFileTos3bucket(amazonS3, bucketName, objKey, convertMultipartFile);
        //add uploaded file s3 url to list
        String fileUrl = endpointUrl + "/" + bucketName + "/" + objKey;
        LOGGER.info("Utility->uplodImages : imgUploadFolderWithFile = " + fileUrl);
        boolean b = convertMultipartFile.delete();
        if (!b) {
            LOGGER.error("Attention: Unable to delete file " + convertMultipartFile.getAbsolutePath());
        }
        return fileUrl;

    }

    public List<String> uploadImagesToS3(AmazonS3 amazonS3, String bucketName, String endpointUrl, String imgUploadKey, MultipartFile[] multipartFiles,boolean isVideo) throws Exception {
        if (amazonS3 == null)
            throw new NullPointerException("Got null AmazonS3 object as arguement !");
        if (Utility.isFieldEmpty(bucketName)) {
            throw new IllegalArgumentException("Empty bucketname !");
        }
        if (multipartFiles == null || multipartFiles.length == 0) {
            throw new IllegalArgumentException("Got empty images as arguement !");
        }
        if (Utility.isFieldEmpty(endpointUrl)) {
            throw new IllegalArgumentException("Empty endpointurl !");
        }
        if (Utility.isFieldEmpty(imgUploadKey)) {
            throw new IllegalArgumentException("Empty imgUploadKey !");
        }
        List<String> uploadedImageNames = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String returnUrl = uploadSingleImageToS3(amazonS3, bucketName, endpointUrl, multipartFile, imgUploadKey,isVideo);
            uploadedImageNames.add(returnUrl);
        }
        return uploadedImageNames;
    }

    public List<String> uploadImagesToS3(AmazonS3 amazonS3, String bucketName, String endpointUrl, String imgUploadKey, MultipartFile[] multipartFiles) throws Exception {
        return uploadImagesToS3(amazonS3,bucketName,endpointUrl,imgUploadKey,multipartFiles,false);
    }

    public List<String> uploadImagesToS3(String imgUploadKey, MultipartFile[] multipartImages) throws Exception {
        return uploadImagesToS3(s3Client, bucketName, endpointUrl, imgUploadKey, multipartImages);
    }

    public List<String> uploadImagesToS3(String imgUploadKey, MultipartFile[] multipartImages,boolean isVideo) throws Exception {
        return uploadImagesToS3(s3Client, bucketName, endpointUrl, imgUploadKey, multipartImages,isVideo);
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