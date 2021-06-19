package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Component
public class StrategyAddUserApi implements StrategyAddBehaviour<DhUser> {

    @Autowired
    UserDao userDao;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private CommonService commonService;

    @Override
    public Response<DhUser> add(String language, DhUser dhUser) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Response<DhUser> returnResponse = validateAddUser(language, dhUser);
        LOGGER.info("returnResponse=>" + returnResponse.getMessage());
        if (returnResponse.getStatus()) {
            dhUser.setPassword(bCryptPasswordEncoder.encode(dhUser.getPassword()));
            if(dhUser.getRoles()==null || dhUser.getRoles().length==0)
                dhUser.setRoles(AppConstants.ROLE_USER);

            dhUser.setUserSettings(Utility.getGlobalUserSettings());
            dhUser.setUserSettingEnabled(true);
            dhUser.setSponsored(false);
            dhUser = (DhUser) Utility.setCommonAttrs(dhUser, AppConstants.STATUS_ACTIVE);
            userDao.addUser(dhUser);
            returnResponse.setLogActionMsg("New user registered .");
            commonService.markRegisteredInNewUserSupport(dhUser);
            returnResponse.setData(Collections.singletonList(dhUser));
        }
        LOGGER.info("addUser->" + returnResponse.getMessage());
        return returnResponse;
    }

    @Override
    public Response<DhUser> add(String language, DhUser obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddUserStrategy;
    }

    public Response<DhUser> validateAddUser(String language, DhUser dhUserDetails) {
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
            Optional<DhUser> queriedUserByEmail = userDao.findByEmailId(dhUserDetails.getEmailId());
            if (queriedUserByEmail.isPresent()) {
                return new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMAIL_ALREADY_USED), new ArrayList<>());
            }
        }


        if (Utility.isFieldEmpty(dhUserDetails.getPassword())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY), new ArrayList<>());
        }

        if (Utility.isFieldEmpty(dhUserDetails.getProfileImgLow())) {
            dhUserDetails.setUserId(Utility.getUUID());
        } else if (Utility.isFieldEmpty(dhUserDetails.getUserId())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        }

        return new Response<>(true, 201, dhUserDetails.getFirstName() + " Sir", ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUserDetails));
    }

}
