package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.models.DhLog;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.mongodb.lang.NonNull;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class AddUserApi implements AddBehaviour<DhUser> {

    private final UserDetailsServiceImpl userDetailsService;
    private final CommonService commonService;

    public AddUserApi(UserDetailsServiceImpl ud, CommonService commonService) {
        this.userDetailsService = ud;
        this.commonService = commonService;
    }

    @Override
    public Response<DhUser> add(String language, @NonNull MongoTemplate mongoTemplate, DhUser dhUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Response<DhUser> returnResponse = validateAddUser(language, dhUser, mongoTemplate, userDetailsService);
        LOGGER.info("returnResponse=>" + returnResponse.getMessage());
        if (returnResponse.getStatus()) {
            dhUser.setPassword(bCryptPasswordEncoder.encode(dhUser.getPassword()));
            dhUser.setRoles(AppConstants.ROLE_USER);
            dhUser.setUserSettings(Utility.getGlobalUserSettings());
            dhUser.setUserSettingEnabled(false);
            dhUser.setSponsored(false);
            dhUser = (DhUser) Utility.setCommonAttrs(dhUser, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(dhUser);
            returnResponse.setLogActionMsg("New user registered .");
            commonService.markRegisteredInNewUserSupport(dhUser);
            returnResponse.setData(Collections.singletonList(dhUser));
        }
        LOGGER.info("addUser->" + returnResponse.getMessage());
        return returnResponse;
    }

    public Response<DhUser> validateAddUser(String language, DhUser dhUserDetails, MongoTemplate mongoTemplate, UserDetailsServiceImpl userDetailsService) {
        if (dhUserDetails == null) {
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhUserDetails.getMobileNumber()))
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY), new ArrayList<>());

        try {
            UserDetailsDecorator userDetailsDecorator = userDetailsService.loadUserByUsername(dhUserDetails.getMobileNumber());
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED), new ArrayList<>());
        } catch (NullPointerException nullPointerException) {
            LOGGER.info("validateAddUser=>nullPointerException:" + nullPointerException.getMessage());
            return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG), new ArrayList<>());
        } catch (UsernameNotFoundException usernameNotFoundException) {
            LOGGER.info("validateAddUser=>UsernameNotFoundException:" + usernameNotFoundException.getMessage());
        }

        if (!Utility.isFieldEmpty(dhUserDetails.getEmailId())) {
            Query queryFindUserByEmailId = new Query(Criteria.where(AppConstants.EMAIL).is(dhUserDetails.getEmailId()));
            DhUser userByEmailId = mongoTemplate.findOne(queryFindUserByEmailId, DhUser.class);
            if (userByEmailId != null) {
                return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMAIL_ALREADY_USED), new ArrayList<>());
            }
        }


        if (Utility.isFieldEmpty(dhUserDetails.getPassword())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhUserDetails.getProfileImg())) {
            dhUserDetails.setUserId(Utility.getUUID());
        } else if (Utility.isFieldEmpty(dhUserDetails.getUserId())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        }

        return new Response<>(true, 201, dhUserDetails.getFirstName() + " Sir", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUserDetails));
    }

}
