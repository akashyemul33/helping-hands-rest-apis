package com.ayprojects.helpinghands.api.classes.get_strategy;

import com.ayprojects.helpinghands.api.behaviours.StrategyGetBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.models.DhViews;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StrategyGetViews implements StrategyGetBehaviour<DhViews> {
    @Override
    public Response<DhViews> get(String language, HashMap<String, Object> params) {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.GetViewsStrategy;
    }
}
