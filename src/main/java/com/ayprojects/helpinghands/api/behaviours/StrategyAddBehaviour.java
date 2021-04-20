package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.stereotype.Service;

@Service
public interface StrategyAddBehaviour<T extends AllCommonUsedAttributes> {
    Response<T> add(String language, T obj) throws ServerSideException;

    StrategyName getStrategyName();
}
