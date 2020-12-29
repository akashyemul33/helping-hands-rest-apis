package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import org.springframework.security.core.Authentication;

public interface AddBehaviour {
    void add(Authentication authentication,AllCommonUsedAttributes obj, int version);
}
