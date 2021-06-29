package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.PlaceSubCategories;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class StrategyGetPlaceCategories implements StrategyGetBehaviour<DhPlaceCategories> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhPlaceCategories> get(String language, HashMap<String, Object> params) {
        if (params == null) {
            return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS), new ArrayList<>());
        }
        Set<String> keySet = params.keySet();
        if (keySet.contains(AppConstants.KEY_TYPE_OF_PLACECATEGORY)) {
            return getAllPlaceCategoriesByType(language, (String) params.get(AppConstants.KEY_TYPE_OF_PLACECATEGORY));
        } else
            return new Response<DhPlaceCategories>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS) + ": " + AppConstants.KEY_TYPE_OF_PLACECATEGORY, new ArrayList<>());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetPlaceCategoriesStrategy;
    }

    public Response<DhPlaceCategories> getAllPlaceCategoriesByType(String language, String typeOfPlaceCategory) {

        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        query.addCriteria(Criteria.where(AppConstants.TYPE_OF_PLACECATEGORY).regex(typeOfPlaceCategory, "i"));
        List<DhPlaceCategories> dhPlaceCategoriesList = mongoTemplate.find(query, DhPlaceCategories.class);
        if (dhPlaceCategoriesList.size() <= 0) {
            return new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES), new ArrayList<>(), 0);
        } else {
            //remove the sub categories with status other than active
            //and for active subcategories add maincategoryid and name.
            for (DhPlaceCategories mc : dhPlaceCategoriesList) {
                if (mc != null && mc.getPlaceSubCategories() != null && mc.getPlaceSubCategories().size() > 0) {
                    List<PlaceSubCategories> activePlaceSubCategories = new ArrayList<>(mc.getPlaceSubCategories().size());
                    for (int j = 0; j < mc.getPlaceSubCategories().size(); j++) {
                        PlaceSubCategories sc = mc.getPlaceSubCategories().get(j);
                        if (sc != null) {
                            if (sc.getStatus().equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                                sc.setPlaceMainCategoryId(mc.getPlaceCategoryId());
                                sc.setPlaceMainCategoryName(Utility.getMainCategoryName(mc, language));
                                activePlaceSubCategories.add(sc);
                            }
                        }
                    }
                    mc.setPlaceSubCategories(activePlaceSubCategories);

                }
            }

            return new Response<DhPlaceCategories>(true, 200, dhPlaceCategoriesList.size() + " place categories found .", dhPlaceCategoriesList, dhPlaceCategoriesList.size());
        }
    }
}
