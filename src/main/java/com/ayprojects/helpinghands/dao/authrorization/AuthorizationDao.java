package com.ayprojects.helpinghands.dao.authrorization;

import com.ayprojects.helpinghands.models.DhAuthorization;
import com.ayprojects.helpinghands.models.DhUser;

import java.util.Optional;

public interface AuthorizationDao {
    Optional<DhAuthorization> findByUsername(String username);
}
