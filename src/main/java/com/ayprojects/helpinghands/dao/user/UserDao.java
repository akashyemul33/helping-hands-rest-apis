package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.DhUser;

import java.util.Optional;

public interface UserDao {
    DhUser addUser(DhUser dhUserDetails);
    DhUser login(String username, String password);
    Optional<DhUser> findByMobileNumber(String mobileNumber);
    Optional<DhUser> findByEmailId(String emailId);
}
