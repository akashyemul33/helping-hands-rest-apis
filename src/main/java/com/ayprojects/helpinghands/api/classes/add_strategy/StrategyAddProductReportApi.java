package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhProductReport;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class StrategyAddProductReportApi implements StrategyAddBehaviour<DhProductReport> {
    @Autowired
    Utility utility;
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhProductReport> add(String language, DhProductReport obj) throws ServerSideException {
        Response<DhProductReport> productReportResponse = validateAddProductReport(language, obj);
        if (productReportResponse.getStatus()) {
            obj = (DhProductReport) Utility.setCommonAttrs(obj, AppConstants.STATUS_ACTIVE);
            mongoTemplate.save(obj, AppConstants.COLLECTION_DH_PRODUCT_REPORT);
            utility.addLog(obj.getAddedBy(), "Added product report of type : " + obj.getProductReportTypeTitle());
        }
        return productReportResponse;
    }

    @Override
    public Response<DhProductReport> add(String language, DhProductReport obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddProductReportStrategy;
    }

    private Response<DhProductReport> validateAddProductReport(String lang, DhProductReport productReport) {
        if (productReport == null) {
            return new Response<DhProductReport>(false, 402, ResponseMsgFactory.getResponseMsg(lang, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        } else if (Utility.isFieldEmpty(productReport.getAddedBy()) || Utility.isFieldEmpty(productReport.getProductId()) || Utility.isFieldEmpty(productReport.getProductReportTypeId()) || (AppConstants.OTHER.equalsIgnoreCase(productReport.getProductReportTypeTitle()) && Utility.isFieldEmpty(productReport.getProductReportDescription()))) {
            return new Response<DhProductReport>(false, 402, "Report type id, product id, user id or type description (when Other chosen) is missing !", new ArrayList<>());
        }
        return new Response<>(true, 201, "Product report has been added .", new ArrayList<>());
    }

}
