package com.ayprojects.helpinghands.services;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.mockito.Mockito.when;

public class GetAuthenticationObj {


    public static Authentication getValidAuthenticationObj(UserDao userDao, AuthenticationManager authenticationManager) {
        String mobile = "7987787878";
        String password = "Aloha@123";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setPassword("$2a$10$pWhMDo85eBhqynKZ9hibSe5QFZyakcg8CkNQrvxEiXkZ6qYwwViYa");
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobile, password));
    }
}
