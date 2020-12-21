package com.ayprojects.helpinghands.dao.user;

import com.ayprojects.helpinghands.models.DhUser;

import java.util.Optional;

public interface UserDao {
    DhUser addUser(DhUser dhUserDetails);
    DhUser login(String username, String password);
    Optional<DhUser> findByMobileNumber(String mobileNumber);
    Optional<DhUser> findByMobileNumberAndCountryCode(String mobileNumber,String countryCode);
    Optional<DhUser> findByEmailId(String emailId);
    Optional<DhUser> findByMobileNumberOrEmailId(String mobileNumber,String emailId);
    Optional<DhUser> findByUserId(String userId);
}
