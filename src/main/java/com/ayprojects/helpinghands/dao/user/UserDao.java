package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.User;

import java.util.Optional;

public interface UserDao {
    User signUp(User userDetails);
    User login(String username,String password);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByEmail(String email);
}
