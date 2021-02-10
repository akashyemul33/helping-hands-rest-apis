package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Response<DhUser> addUser(HttpHeaders httpHeaders, DhUser dhUser, String version);

    Response<LoginResponse> getUserDetails(HttpHeaders httpHeaders, Authentication authentication, String fcmToken, String lastLogoutTime, String version);

    Response<DhUser> getUserByMobile(HttpHeaders httpHeaders, String mobileNumber, String countryCode, String fcmToken, String version);
}
