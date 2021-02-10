package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.UserRepository;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.appconfig.AppConfigService;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.services.log.LogService;
import com.ayprojects.helpinghands.util.headers.IHeaders;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    CommonService commonService;

    @Autowired
    LogService logService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppConfigService appConfigService;

    @Autowired
    UserDao userDao;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Response<DhUser> addUser(HttpHeaders httpHeaders, DhUser dhUserDetails, String version) {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);

        Response<DhUser> returnResponse = Validations.validateAddUser(language, dhUserDetails, mongoTemplate, null);
        LOGGER.info("returnResponse=>" + returnResponse.getMessage());
        if (returnResponse.getStatus()) {
            dhUserDetails.setPassword(bCryptPasswordEncoder.encode(dhUserDetails.getPassword()));
            dhUserDetails.setRoles(AppConstants.ROLE_USER);
            dhUserDetails.setUserSettings(Utility.getGlobalUserSettings());
            dhUserDetails.setUserSettingEnabled(false);
            dhUserDetails.setSponsored(false);
            dhUserDetails = (DhUser) Utility.setCommonAttrs(dhUserDetails, AppConstants.STATUS_ACTIVE);
            userDao.addUser(dhUserDetails);
            logService.addLog(new DhLog(dhUserDetails.getUserId(), "New user registered ."));
            commonService.markRegisteredInNewUserSupport(dhUserDetails);
            returnResponse.setData(Collections.singletonList(dhUserDetails));
        }
        LOGGER.info("addUser->" + returnResponse.getMessage());
        return returnResponse;
    }

    @Override
    public Response<LoginResponse> getUserDetails(HttpHeaders httpHeaders, Authentication authentication, String newFcmToken, String lastLogoutTime, String version) {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);

        if (authentication == null || !authentication.isAuthenticated() || Utility.isFieldEmpty(authentication.getName())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_AUTHENTICATION_REQD), new ArrayList<>());
        }

        try {
            UserDetailsDecorator userDetailsDecorator = userDetailsService.loadUserByUsername(authentication.getName());
            DhUser dhUser = userDetailsDecorator.getUser();
            Response<DhAppConfig> appConfigResponse = appConfigService.getActiveAppConfig(httpHeaders, authentication, version);
            if (dhUser != null && appConfigResponse != null && appConfigResponse.getStatus() && appConfigResponse.getData() != null) {
                commonService.updateUserWithSessionAndOtherDetails(newFcmToken, lastLogoutTime, dhUser);
                return new Response<>(true,
                        200,
                        ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED)
                        , Collections.singletonList(new LoginResponse(userDetailsService.loadUserByUsername(authentication.getName()).getUser(), appConfigResponse.getData().get(0)))
                        , 1);
            }
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
        } catch (UsernameNotFoundException e) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER), new ArrayList<>());
        }
    }

    @Override
    public Response<DhUser> getUserByMobile(HttpHeaders httpHeaders, String mobileNumber, String countryCode, String newFcmToken, String version) {
        String language = IHeaders.getLanguageFromHeader(httpHeaders);
        if (Utility.isFieldEmpty(mobileNumber)) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY), new ArrayList<>());
        }

        Optional<DhUser> queriedUser = userDao.findByMobileNumber(mobileNumber);

        if (queriedUser != null && queriedUser.isPresent() && queriedUser.get() != null) {
            String status = queriedUser.get().getStatus();
            if (!Utility.isFieldEmpty(status) && status.equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                commonService.updateUserWithSessionAndOtherDetails(newFcmToken, null, queriedUser.get());
                return new Response<>(true, 200, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE), new ArrayList<>(), 1);
            } else {
                return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER), new ArrayList<>());
            }
        } else {
            commonService.insertIntoNewUserSupport(newFcmToken, mobileNumber, countryCode);
        }
        return new Response<>(false, 403, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE), new ArrayList<>());
    }

}
