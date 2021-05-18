package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import org.springframework.security.core.Authentication;

public interface StrategyDeleteBehaviour<T extends AllCommonUsedAttributes> {
    void deleteById(Authentication authentication, String objId, int version);
    void updateStatusToPending(Authentication authentication, String objId, int version);
}
