package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.User;
import com.ayprojects.helpinghands.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;


    @Override
    public User signUp(User userDetails) {
        return userRepository.save(userDetails);
    }

    @Override
    public User login(String username, String password) {
//        return userRepository.loginUser(username,password);
        return null;
    }

    @Override
    public Optional<User> findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public Optional<User> findByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }
}
