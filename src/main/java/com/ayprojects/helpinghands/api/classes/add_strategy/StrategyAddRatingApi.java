package com.ayprojects.helpinghands.api.classes.add_strategy;

import com.ayprojects.helpinghands.api.behaviours.StrategyAddBehaviour;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.stereotype.Component;

@Component
public class StrategyAddRatingApi implements StrategyAddBehaviour<DhRatingAndComments> {


    @Override
    public Response<DhRatingAndComments> add(String language, DhRatingAndComments obj) throws ServerSideException {
        return null;
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.AddRatingStrategy;
    }
}
