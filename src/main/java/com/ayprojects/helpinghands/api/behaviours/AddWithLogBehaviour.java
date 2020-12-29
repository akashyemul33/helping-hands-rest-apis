package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import org.springframework.security.core.Authentication;

public interface AddWithLogBehaviour {
    void addWithLog(Authentication authentication,AllCommonUsedAttributes obj,String logMsg,int version);
}
