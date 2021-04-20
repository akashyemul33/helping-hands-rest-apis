package com.ayprojects.helpinghands.dao.authrorization;

import com.ayprojects.helpinghands.models.DhAuthorization;
import com.ayprojects.helpinghands.repositories.AuthorizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorizationDaoImpl implements AuthorizationDao {

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Override
    public Optional<DhAuthorization> findByUsername(String username) {
        return authorizationRepository.findByUsername(username);
    }
}
