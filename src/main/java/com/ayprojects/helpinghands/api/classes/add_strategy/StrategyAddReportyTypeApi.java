package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProductReportType;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class StrategyAddReportyTypeApi implements StrategyAddBehaviour<DhProductReportType> {
    @Autowired
    Utility utility;
    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public Response<DhProductReportType> add(String language, DhProductReportType obj) throws ServerSideException {
        Response<DhProductReportType> productReportTypeResponse = validateAddProductReportType(language, obj);
        if (productReportTypeResponse.getStatus()) {
            obj.setReportTypeId(Utility.getUUID());
            obj = (DhProductReportType) Utility.setCommonAttrs(obj, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(obj, AppConstants.COLLECTION_DH_PRODUCT_REPORT_TYPE);
            utility.addLog(obj.getAddedBy(), "Added product report type : " + obj.getTypeTitle());
        }
        return productReportTypeResponse;
    }

    private Response<DhProductReportType> validateAddProductReportType(String lang, DhProductReportType productReportType) {
        if (productReportType == null) {
            return new Response<DhProductReportType>(false, 402, ResponseMsgFactory.getResponseMsg(lang, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        } else if (Utility.isFieldEmpty(productReportType.getAddedBy()) || Utility.isFieldEmpty(productReportType.getTypeTitle())) {
            return new Response<DhProductReportType>(false, 402, "Report type title or user id is missing !", new ArrayList<>());
        }
        return new Response<>(true, 201, "Product report type has been added .", new ArrayList<>());
    }

    @Override
    public Response<DhProductReportType> add(String language, DhProductReportType obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddProductReportTypeStrategy;
    }
}
