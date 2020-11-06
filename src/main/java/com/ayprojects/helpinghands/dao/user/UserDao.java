package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.User;

import java.util.Optional;

public interface UserDao {
    User signUp(User userDetails);
    User login(String username,String password);
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByEmailId(String emailId);
}
