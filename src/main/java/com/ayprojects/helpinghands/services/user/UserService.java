package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Response<DhUser> signUp(DhUser dhUserDetails, HttpHeaders httpHeaders);
    Response<AccessTokenModel> login(AuthenticationRequest authenticationRequest, HttpHeaders httpHeaders);
}
