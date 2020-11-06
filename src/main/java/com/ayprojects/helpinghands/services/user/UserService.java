package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.User;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Response<User> signUp(User userDetails, HttpHeaders httpHeaders);
    User login(String username,String password);
}
