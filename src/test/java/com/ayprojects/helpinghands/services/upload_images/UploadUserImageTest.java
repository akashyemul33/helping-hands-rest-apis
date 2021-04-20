package com.ayprojects.helpinghands.services.upload_images;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.image.ImageServiceImpl;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UploadUserImageTest {

    @Autowired
    ImageServiceImpl imageService;

    @Test
    void givenEmptyImagesThenErrorResponse() throws ServerSideException {
        Response<DhUser> expectedUserResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhUser> actualResponse = imageService.uploadUserImage(null,null,AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedUserResponse.getStatusCode(),actualResponse.getStatusCode());

        MultipartFile emptyFile = new MockMultipartFile("abc", (byte[]) null);
        Response<DhUser> actualResponse2 = imageService.uploadUserImage(null,emptyFile,AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedUserResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedUserResponse.getStatusCode(), actualResponse2.getStatusCode());
    }

    @Test
    void givenValidInputThenSucceed() throws IOException, ServerSideException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/sad.svg");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/sad_copy.svg", String.valueOf(ContentType.IMAGE_SVG), inputStream);
        Response<DhUser> expectedUserResponse =  new Response<DhUser>(true, 201, "Image saved successfully", new ArrayList<>());
        Response<DhUser> actualResponse = imageService.uploadUserImage(null,multipartFile,AppConstants.CURRENT_API_VERSION);
        assertEquals(expectedUserResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedUserResponse.getStatusCode(),actualResponse.getStatusCode());
    }

}
