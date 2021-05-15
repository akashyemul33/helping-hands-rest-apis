package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhStickers;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.Sticker;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class StrategyAddStickersApi implements StrategyAddBehaviour<DhStickers> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    Utility utility;

    @Override
    public Response<DhStickers> add(String language, DhStickers dhStickers) throws ServerSideException {
        Response<DhStickers> returnResponse = validateAddStickerPack(language, dhStickers);
        if (returnResponse.getStatus()) {
            dhStickers.setStickerSetId(Utility.getUUID());
            dhStickers = (DhStickers) ApiOperations.setCommonAttrs(dhStickers, AppConstants.STATUS_PENDING);
            dhStickers.setStickerCount(dhStickers.getStickers().size());
            for (Sticker s : dhStickers.getStickers()) {
                s.setCreatedDateTime(dhStickers.getCreatedDateTime());
                s.setModifiedDateTime(dhStickers.getModifiedDateTime());
                s.setStatus(AppConstants.STATUS_ACTIVE);
                s.setStickerId(Utility.getUUID());
            }
            mongoTemplate.save(dhStickers, AppConstants.COLLECTION_DH_STICKERS);
            utility.addLog("NoName", "Added sticker pack : " + dhStickers.getStickerPackName());
        }
        return returnResponse;
    }

    @Override
    public Response<DhStickers> add(String language, DhStickers obj, HashMap<String, Object> params) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddStickerStrategy;
    }

    public Response<DhStickers> validateAddStickerPack(String language, DhStickers dhStickers) {
        if (dhStickers == null || dhStickers.getStickers() == null || dhStickers.getStickers().isEmpty()) {
            return new Response<DhStickers>(false, 402, ResponseMsgFactory.getResponseMsg(language, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        } else if (Utility.isFieldEmpty(dhStickers.getStickerPackName())) {
            return new Response<DhStickers>(false, 402, "Sticker pack name is missing !", new ArrayList<>());
        } else {
            for (Sticker s : dhStickers.getStickers()) {
                if (s == null) {
                    return new Response<DhStickers>(false, 402, "Empty sticker body at sticker index : " + dhStickers.getStickers().indexOf(s), new ArrayList<>());
                } else if (Utility.isFieldEmpty(s.getStickerPath()) || Utility.isFieldEmpty(s.getStickerName())) {
                    return new Response<DhStickers>(false, 402, "Sticker path or sticker name is empty at index : " + dhStickers.getStickers().indexOf(s), new ArrayList<>());
                }
            }
        }
        return new Response<>(true, 201, "Sticker pack added successfully .", new ArrayList<>());
    }
}
