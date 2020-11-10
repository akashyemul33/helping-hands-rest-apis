package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.AccessTokenModel;
import com.ayprojects.helpinghands.models.Address;
import com.ayprojects.helpinghands.models.AuthenticationRequest;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.security.JwtHelper;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.tools.Utility;
import com.ayprojects.helpinghands.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private Utility utility;

    @Autowired
    private AppConfigService appConfigService;

    @Override
    public Response<DhUser> signUp(DhUser dhUserDetails, HttpHeaders httpHeaders, String version) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);
        dhUserDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        Response<DhUser> res = new Response<DhUser>();
        if(dhUserDetails==null){
            return new Response<DhUser>(false,402,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language),new ArrayList<>(), 0);
        }

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

        String uniqueUserID = Utility.getUUID();

        dhUserDetails.setUserId(uniqueUserID);
        dhUserDetails.setPassword(bCryptPasswordEncoder.encode(dhUserDetails.getPassword()));
        dhUserDetails.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhUserDetails.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhUserDetails.setStatus(AppConstants.STATUS_ACTIVE);
        dhUserDetails.setRoles(AppConstants.ROLE_USER);
        dhUserDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        res.setStatus(true);
        res.setStatusCode(201);
        res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_REGISTERED,language));
        res.setData(Collections.singletonList(dhUserDetails));
        userDao.signUp(dhUserDetails);
        utility.addLog(dhUserDetails.getMobileNumber(),AppConstants.ACTION_NEW_USER_ADDED+"by userId:"+uniqueUserID);
        return res;
    }

    @Override
    public Response<AccessTokenModel> login(AuthenticationRequest authenticationRequest, HttpHeaders httpHeaders, String version) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);

        Response<AccessTokenModel> res = new Response<>();

        //find out user with mobile and email, as username can be either mobile or email
        UserDetailsDecorator userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        } catch (UsernameNotFoundException e) {
            LOGGER.info("UserServiceImpl->login -> UserNameNotFoundException : No user found with given usernam, search for user in db with username as mobile and email");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_USERNAME,language));
            res.setData(Collections.singletonList(new AccessTokenModel()));
            utility.addLog(authenticationRequest.getUsername(),AppConstants.ACTION_TRIED_LOGGING_WITH_INCORRECT_USERNAME);
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
                claims.put("userId", userDetails.getUser().getUserId());
                String jwt = jwtHelper.createJwtForClaims(authenticationRequest.getUsername(), claims);
                AccessTokenModel accessTokenModel = new AccessTokenModel(jwt,"","",AppConstants.JWT_TOKEN_EXPIRATION_VALUE,"");
                //send all the details required back as a response of login
                res.setStatus(true);
                res.setStatusCode(201);
                res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_LOGGED_IN,language));
                res.setData(Collections.singletonList(accessTokenModel));
                return res;
            }
            catch (BadCredentialsException e){
                LOGGER.info("UserServiceImpl->login->BadCredentialsException : Password entered is Incorrect :exception "+e.getMessage());
                res.setStatus(false);
                res.setStatusCode(402);
                res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_INCORRECT_PASSWORD,language));
                res.setData(Collections.singletonList(new AccessTokenModel()));
                utility.addLog(authenticationRequest.getUsername(),AppConstants.ACTION_TRIED_LOGGING_WITH_INCORRECT_PASSWORD+"by userId:"+userDetails.getUser().getUserId());
                return res;
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
}
