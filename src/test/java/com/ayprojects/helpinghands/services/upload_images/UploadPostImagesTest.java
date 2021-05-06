package com.ayprojects.helpinghands.services.upload_images;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.image.ImageServiceImpl;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UploadPostImagesTest {

    @Autowired
    ImageServiceImpl imageService;

    @Test
    void givenEmptyImagesThenErrorResponse() throws ServerSideException {
        Response<DhPosts> expectedUserResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
//        Response<DhPosts> actualResponse = imageService.uploadPostImages(null, null, AppConstants.BUSINESS_POST, null, null, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse.getStatusCode());

        MultipartFile emptyFile = new MockMultipartFile("abc", (byte[]) null);
//        Response<DhPosts> actualResponse2 = imageService.uploadPostImages(null, null, AppConstants.PUBLIC_POST, null, new MultipartFile[]{emptyFile}, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse2.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse2.getStatusCode());
    }

    @Test
    void givenEmptyPostTypeThenErrorResponse() throws ServerSideException {
        Response<DhPosts> expectedUserResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        MultipartFile emptyFile = new MockMultipartFile("abc", (byte[]) null);
//        Response<DhPosts> actualResponse = imageService.uploadPostImages(null, null, null, null, new MultipartFile[]{emptyFile}, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void givenEmptyUserIdThenErrorResponse() throws ServerSideException {
        Response<DhPosts> expectedUserResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        MultipartFile emptyFile = new MockMultipartFile("abc", (byte[]) null);
//        Response<DhPosts> actualResponse = imageService.uploadPostImages(null, null, AppConstants.BUSINESS_POST, null, new MultipartFile[]{emptyFile}, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    void givenValidInputWithSingleImgThenSucceed() throws IOException, ServerSideException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile};
        DhPosts dhPost = new DhPosts();
        dhPost.setAddedBy(Utility.getUUID());
        dhPost.setPostType(AppConstants.BUSINESS_POST);
        String successMsg = ResponseMsgFactory.getResponseMsg(AppConstants.LANG_MARATHI, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED);
        Response<DhPosts> expectedUserResponse = new Response<>(true, 201, successMsg, Collections.singletonList(dhPost), 1);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_MARATHI);
//        Response<DhPosts> actualResponse = imageService.uploadPostImages(httpHeaders, null, dhPost.getPostType(), dhPost.getAddedBy(), multipartFiles, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse.getStatusCode());
//        assertTrue(successMsg.equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenValidInputWithMultiImgsThenSucceed() throws IOException, ServerSideException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        InputStream inputStream2 = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile1 = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy1.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        MultipartFile multipartFile2 = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy2.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream2);

        MultipartFile[] multipartFiles = new MultipartFile[]{multipartFile1, multipartFile2};
        DhPosts dhPost = new DhPosts();
        dhPost.setAddedBy(Utility.getUUID());
        dhPost.setPostType(AppConstants.PUBLIC_POST);
        String successMsg = ResponseMsgFactory.getResponseMsg(AppConstants.LANG_ENGLISH, AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED);
        Response<DhPosts> expectedUserResponse = new Response<>(true, 201, successMsg, Collections.singletonList(dhPost), 1);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(AppConstants.LABEL_HEADER_APPLANGUAGE, AppConstants.LANG_ENGLISH);
//        Response<DhPosts> actualResponse = imageService.uploadPostImages(httpHeaders, null, dhPost.getPostType(), dhPost.getAddedBy(), multipartFiles, AppConstants.CURRENT_API_VERSION);
//        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
//        assertEquals(expectedUserResponse.getStatusCode(), actualResponse.getStatusCode());
//        assertTrue(successMsg.equalsIgnoreCase(actualResponse.getMessage()));
    }
}
