package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhAppConfig;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.LoginResponse;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

@Component
public class StrategyGetLoginResponse implements StrategyGetBehaviour<LoginResponse> {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private StrategyGetAppConfig strategyGetAppConfig;

    @Override
    public Response<LoginResponse> get(String language, HashMap<String, Object> params) throws ServerSideException {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_AUTHENTICATION) &&
                keySet.contains(AppConstants.KEY_NEW_FCM_TOKEN) &&
                keySet.contains(AppConstants.KEY_LAST_LOGOUT_TIME)) {
            Authentication authentication = (Authentication) params.get(AppConstants.KEY_AUTHENTICATION);
            String newFcmToken = (String) params.get(AppConstants.KEY_NEW_FCM_TOKEN);
            String lastLogoutTime = (String) params.get(AppConstants.KEY_LAST_LOGOUT_TIME);
            return getUserDetails(authentication, language, newFcmToken, lastLogoutTime);
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetLoginResponseStrategy;
    }


    public Response<LoginResponse> getUserDetails(Authentication authentication, String language, String newFcmToken, String lastLogoutTime) {

        if (authentication == null || !authentication.isAuthenticated() || Utility.isFieldEmpty(authentication.getName())) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_AUTHENTICATION_REQD), new ArrayList<>());
        }

        try {
            UserDetailsDecorator userDetailsDecorator = userDetailsService.loadUserByUsername(authentication.getName());
            DhUser dhUser = userDetailsDecorator.getUser();
            Response<DhAppConfig> appConfigResponse = strategyGetAppConfig.get(language, new HashMap<>());
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

}
