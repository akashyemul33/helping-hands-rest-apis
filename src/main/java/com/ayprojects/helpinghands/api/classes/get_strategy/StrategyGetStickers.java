package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhStickers;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class StrategyGetStickers implements StrategyGetBehaviour<DhStickers> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Response<DhStickers> get(String language, HashMap<String, Object> params) {
        return getAllStickers(language);
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetStickersStrategy;
    }

    public Response<DhStickers> getAllStickers(String language) {
        Query query = new Query(Criteria.where(AppConstants.STATUS).regex(AppConstants.STATUS_ACTIVE, "i"));
        List<DhStickers> dhStickersList = mongoTemplate.find(query, DhStickers.class);

        if (dhStickersList.isEmpty()) {
            return new Response<DhStickers>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_NO_STICKERS_FOUND), new ArrayList<>(), 0);
        }

        return new Response<DhStickers>(true, 200, AppConstants.QUERY_SUCCESSFUL, dhStickersList, dhStickersList.size());
    }
}
