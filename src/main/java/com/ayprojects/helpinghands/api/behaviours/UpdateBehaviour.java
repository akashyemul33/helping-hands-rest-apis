package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import org.springframework.security.core.Authentication;

public interface UpdateBehaviour {
     void updateObjById(Authentication authentication, String objId, AllCommonUsedAttributes objToUpdate, int version);
}
