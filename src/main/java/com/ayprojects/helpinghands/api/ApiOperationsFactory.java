package com.ayprojects.helpinghands.api;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.api.classes.AddLogApi;
import com.ayprojects.helpinghands.api.classes.AddUserApi;
import com.ayprojects.helpinghands.api.classes.AddViewsApi;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.services.common_service.CommonService;

public class ApiOperationsFactory {

    public static AddBehaviour<? extends AllCommonUsedAttributes> getObject(UserDetailsServiceImpl userDetailsService, CommonService commonService, AllCommonUsedAttributes obj) throws ClassNotFoundException {
        if (obj == null) throw new IllegalArgumentException("Object must not be null !");
        switch (obj.getClass().getSimpleName()) {
            case AppConstants.COLLECTION_DHUSER:
                return new AddUserApi(userDetailsService, commonService);
            case AppConstants.COLLECTION_DHLOG:
                return new AddLogApi();
            case AppConstants.COLLECTION_DH_VIEWS:
                return new AddViewsApi();


        }
        throw new ClassNotFoundException("Not found any class with name " + obj.getClass().getSimpleName());
    }

}
