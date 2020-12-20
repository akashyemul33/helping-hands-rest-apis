package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    UserRepository userRepository;


    @Override
    public DhUser addUser(DhUser dhUserDetails) {
        return userRepository.save(dhUserDetails);
    }

    @Override
    public DhUser login(String username, String password) {
//        return userRepository.loginUser(username,password);
        return null;
    }

    @Override
    public Optional<DhUser> findByMobileNumber(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public Optional<DhUser> findByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId);
    }

    @Override
    public Optional<DhUser> findByMobileNumberOrEmailId(String mobileNumber, String emailId) {
        return userRepository.findByMobileNumberOrEmailId(mobileNumber,emailId);
    }

    @Override
    public Optional<DhUser> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }


}
