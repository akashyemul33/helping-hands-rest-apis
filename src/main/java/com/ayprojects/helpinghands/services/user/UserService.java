package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UserService {
    Response<DhUser> addUser(HttpHeaders httpHeaders, DhUser dhUser, String version);
    Response<AccessTokenModel> login(AuthenticationRequest authenticationRequest, HttpHeaders httpHeaders, String version);
    Response<LoginResponse> getUserDetails(HttpHeaders httpHeaders, Authentication authentication, String version);
    Response<DhUser> getUserByMobile(HttpHeaders httpHeaders, String mobileNumber, String version);
}
