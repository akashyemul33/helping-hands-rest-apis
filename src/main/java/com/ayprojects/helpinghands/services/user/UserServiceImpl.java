package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.Address;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.UserSettings;
import com.ayprojects.helpinghands.security.JwtHelper;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.security.JwtUtils;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserServiceImpl implements UserService{

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserDao userDao;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private Utility utility;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private AppConfigService appConfigService;

    @Override
    public Response<DhUser> addUser(HttpHeaders httpHeaders, DhUser dhUserDetails, String version) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);

        if(dhUserDetails==null){
            return new Response<DhUser>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language),new ArrayList<>(), 0);
        }

        if(Utility.isFieldEmpty(dhUserDetails.getMobileNumber()) || Utility.isFieldEmpty(dhUserDetails.getEmailId())){
            LOGGER.info("Contact details are missing ");
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY,language),new ArrayList<>());
        }

        if(Utility.isFieldEmpty(dhUserDetails.getPassword())){
            LOGGER.info("Password is empty ");
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY,language),new ArrayList<>());
        }

        if(Utility.isFieldEmpty(dhUserDetails.getProfileImg())){
            dhUserDetails.setUserId(Utility.getUUID());
        }
        else if(Utility.isFieldEmpty(dhUserDetails.getUserId())){
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING,language),new ArrayList<>());
        }

        Optional<DhUser> queriedUser = userDao.findByMobileNumberOrEmailId(dhUserDetails.getMobileNumber(),dhUserDetails.getEmailId());

        if(queriedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Found user with");
            List<DhUser> dhUserResp = new ArrayList<>();
            dhUserResp.add(queriedUser.get());
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS,language),dhUserResp);
        }

        dhUserDetails.setPassword(bCryptPasswordEncoder.encode(dhUserDetails.getPassword()));
        dhUserDetails.setRoles(AppConstants.ROLE_USER);
        dhUserDetails.setUserSettings(Utility.getGlobalUserSettings());
        dhUserDetails.setUserSettingEnabled(false);
        dhUserDetails.setSponsored(false);
        dhUserDetails = (DhUser) utility.setCommonAttrs(dhUserDetails,AppConstants.STATUS_ACTIVE);
        userDao.addUser(dhUserDetails);
        utility.addLog(dhUserDetails.getMobileNumber(),AppConstants.ACTION_NEW_USER_ADDED);
        return new Response<>(true,201,dhUserDetails.getFirstName()+" Sir",Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_REGISTERED,language),Collections.singletonList(dhUserDetails));
    }

    @Override
    public Response<AccessTokenModel> login(AuthenticationRequest authenticationRequest, HttpHeaders httpHeaders, String version) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);

        //find out user with mobile and email, as username can be either mobile or email
        UserDetailsDecorator userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("UserServiceImpl->login -> UserNameNotFoundException : No user found with given usernam, search for user in db with username as mobile and email");
            utility.addLog(authenticationRequest.getUsername(),AppConstants.ACTION_TRIED_LOGGING_WITH_INCORRECT_USERNAME);
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_USERNAME,language),Collections.singletonList(new AccessTokenModel()));
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
                claims.put("userId", userDetails.getUser().getUserId());

                String jwt = jwtHelper.createJwtForClaims(authenticationRequest.getUsername(), claims);
                AccessTokenModel accessTokenModel = new AccessTokenModel(jwt,"","",AppConstants.JWT_TOKEN_EXPIRATION_VALUE,"");
                //send all the details required back as a response of login
                return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_LOGGED_IN,language),Collections.singletonList(accessTokenModel));
            }
            catch (BadCredentialsException e){
                LOGGER.info("UserServiceImpl->login->BadCredentialsException : Password entered is Incorrect :exception "+e.getMessage());
                utility.addLog(authenticationRequest.getUsername(),AppConstants.ACTION_TRIED_LOGGING_WITH_INCORRECT_PASSWORD+"by userId:"+userDetails.getUser().getUserId());
                return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_PASSWORD,language),Collections.singletonList(new AccessTokenModel()));
            }
    }

    @Override
    public Response<LoginResponse> getUserDetails(HttpHeaders httpHeaders, Authentication authentication, String version){
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);
        Response<DhAppConfig> appConfigResponse = appConfigService.getActiveAppConfig(httpHeaders,authentication, version);
        DhAppConfig dhAppConfig = appConfigResponse.getStatus() ? appConfigResponse.getData().get(0) : null;
        return new Response<LoginResponse>(true,
                201,
                Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED,language)
                ,Collections.singletonList(new LoginResponse(userDetailsService.loadUserByUsername(authentication.getName()).getUser(),dhAppConfig))
                , 1);
    }

    @Override
    public Response<DhUser> getUserByMobile(HttpHeaders httpHeaders, String mobileNumber, String version) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);

        if(Utility.isFieldEmpty(mobileNumber)){
            return new Response<>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY,language),new ArrayList<>(),0);
        }

        Optional<DhUser> queriedUser = userDao.findByMobileNumber(mobileNumber);
        if(queriedUser.isPresent()){
            return new Response<>(true,200,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE,language),new ArrayList<>(),1);
        }
        return new Response<>(false,403,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE,language),new ArrayList<>(),1);
    }

}
