package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.api.behaviours.AddWithLogBehaviour;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import org.springframework.security.core.Authentication;

public class UserApi implements AddBehaviour, AddWithLogBehaviour {
    @Override
    public void add(Authentication authentication, AllCommonUsedAttributes obj, int version) {

    }

    @Override
    public void addWithLog(Authentication authentication, AllCommonUsedAttributes obj, String logMsg, int version) {

    }
}
