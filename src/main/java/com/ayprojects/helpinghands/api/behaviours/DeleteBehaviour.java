package com.ayprojects.helpinghands.api.behaviours;

import org.springframework.security.core.Authentication;

public interface DeleteBehaviour {
    void deleteById(Authentication authentication, String objId, int version);
}
