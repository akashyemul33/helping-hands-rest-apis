package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.UploadBehaviour;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.aws.AmazonClient;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.GetImageFoldersAndPrefix;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Component
public class UploadImagesApi implements UploadBehaviour {
    @Autowired
    AmazonClient amazonClient;

    @Override
    public Response<DhUser> uploadUserImage(HttpHeaders httpHeaders, MultipartFile image, String version) {
        if (image == null || image.isEmpty()) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }
        String language = IHeaders.getLanguageFromHeader(httpHeaders);

        String uniqueUserID = Utility.getUUID();
        String imgUploadKey = GetImageFoldersAndPrefix.getUserImgUploadKey(uniqueUserID);
        try {
            String finalKey = amazonClient.uploadSingleImageToS3(imgUploadKey, image);
            DhUser dhUser = new DhUser(uniqueUserID, finalKey);
//            new AddLogApi(new DhLog(Utility.getUUID(), uniqueUserID, "User image has been added", Utility.currentDateTimeInUTC(), Utility.currentDateTimeInUTC()));
            return new Response<DhUser>(true, 201, "Image saved successfully", new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG, language), new ArrayList<>());
        }
    }

    @Override
    public Response<DhPlace> uploadPlaceImages(HttpHeaders httpHeaders, Authentication authentication, String placeType, String addedBy, MultipartFile[] placeImages, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPosts> uploadPostImages(HttpHeaders httpHeaders, Authentication authentication, String postType, String addedBy, MultipartFile[] postImages, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhRequirements> uploadRequirementImages(HttpHeaders httpHeaders, Authentication authentication, String reqType, String addedBy, MultipartFile[] reqImages, String version) throws ServerSideException {
        return null;
    }
}
