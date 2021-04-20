package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.services.common_service.CommonService;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

@Component
public class StrategyGetUser implements StrategyGetBehaviour<DhUser> {
    @Autowired
    UserDao userDao;

    @Autowired
    CommonService commonService;

    @Override
    public Response<DhUser> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }

        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_MOBILE) &&
                keySet.contains(AppConstants.KEY_COUNTRY_CODE) &&
                keySet.contains(AppConstants.KEY_FCM_TOKEN)) {
            String mobileNumber = (String) params.get(AppConstants.KEY_MOBILE);
            String countryCode = (String) params.get(AppConstants.KEY_COUNTRY_CODE);
            String newFcmToken = (String) params.get(AppConstants.KEY_FCM_TOKEN);
            return getUserByMobile(language, mobileNumber, countryCode, newFcmToken);
        } else {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetUserStrategy;
    }


    public Response<DhUser> getUserByMobile(String language, String mobileNumber, String countryCode, String newFcmToken) {

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
