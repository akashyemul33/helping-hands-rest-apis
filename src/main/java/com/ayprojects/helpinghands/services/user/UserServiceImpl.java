package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.Address;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.security.JwtHelper;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserDao userDao;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Autowired
    JwtUtils jwtTokenUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public Response<DhUser> signUp(DhUser dhUserDetails, HttpHeaders httpHeaders) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);
        dhUserDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        Response<DhUser> res = new Response<DhUser>();
        if(Utility.isFieldEmpty(dhUserDetails.getMobileNumber()) || Utility.isFieldEmpty(dhUserDetails.getEmailId())){
            LOGGER.info("Contact details are missing ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY,language));
            res.setData(Collections.singletonList(dhUserDetails));
            return res;
        }

        if(Utility.isFieldEmpty(dhUserDetails.getPassword())){
            LOGGER.info("Password is empty ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY,language));
            res.setData(Collections.singletonList(dhUserDetails));
            return res;
        }

        Address address = dhUserDetails.getAddressDetails();
        if(address==null || address.getLat()==0 || address.getLng()==0){
            LOGGER.info("Address details are missing ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY,language));
            res.setData(Collections.singletonList(dhUserDetails));
            return res;
        }

        Optional<DhUser> repeatedUser = userDao.findByMobileNumber(dhUserDetails.getMobileNumber());
        if(!repeatedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Not found user with mobile "+ dhUserDetails.getMobileNumber());
            repeatedUser = userDao.findByEmailId(dhUserDetails.getEmailId());
        }

        if(repeatedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Found user with");
            List<DhUser> dhUserResp = new ArrayList<>();
            dhUserResp.add(repeatedUser.get());
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS,language));
            res.setData(dhUserResp);
            return res;
        }

        String uniqueID = UUID.randomUUID().toString();

        dhUserDetails.setUserId(uniqueID);
        dhUserDetails.setPassword(bCryptPasswordEncoder.encode(dhUserDetails.getPassword()));
        dhUserDetails.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhUserDetails.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhUserDetails.setStatus(AppConstants.STATUS_ACTIVE);
        dhUserDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        res.setStatus(true);
        res.setStatusCode(201);
        res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_REGISTERED,language));
        res.setData(Collections.singletonList(dhUserDetails));
        userDao.signUp(dhUserDetails);
        return res;
    }

    @Override
    public Response<LoginResponse> login(AuthenticationRequest authenticationRequest, HttpHeaders httpHeaders) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);

        Response<LoginResponse> res = new Response<LoginResponse>();

        //find out user with mobile and email, as username can be either mobile or email
        UserDetailsDecorator userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("UserServiceImpl->login -> UserNameNotFoundException : No user found with given usernam, search for user in db with username as mobile and email");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_USERNAME,language));
            res.setData(Collections.singletonList(new LoginResponse()));
            return res;
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

            LOGGER.info("User is present with given username "+authenticationRequest.getUsername());
            //lets authenticate user whether he entered correct password
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
                Map<String, String> claims = new HashMap<>();
                claims.put("username", authenticationRequest.getUsername());
                String authorities = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));
                claims.put("authorities", authorities);
                claims.put("userId", String.valueOf(userDetails.getUser().getUserId()));
                String jwt = jwtHelper.createJwtForClaims(authenticationRequest.getUsername(), claims);
                AccessTokenModel accessTokenModel = new AccessTokenModel();
                accessTokenModel.setToken_type("");
                accessTokenModel.setScope("");
                accessTokenModel.setRefresh_token("");
                accessTokenModel.setAccess_token(jwt);
                accessTokenModel.setExpires_in(AppConstants.JWT_TOKEN_EXPIRATION_VALUE);
                //send all the details required back as a response of login
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setAccessToken(accessTokenModel);
                res.setStatus(true);
                res.setStatusCode(201);
                res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_LOGGED_IN,language));
                res.setData(Collections.singletonList(loginResponse));
                return res;
            }
            catch (BadCredentialsException e){
                LOGGER.info("UserServiceImpl->login->BadCredentialsException : Password entered is Incorrect :exception "+e.getMessage());
                res.setStatus(false);
                res.setStatusCode(402);
                res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_PASSWORD,language));
                res.setData(Collections.singletonList(new LoginResponse()));
                return res;
            }


    }
}
