package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.util.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDao userDao;

    @Override
    public UserDetailsDecorator loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<DhUser> queriedUser = userDao.findByMobileNumber(username);
        if (queriedUser != null && queriedUser.isPresent() && queriedUser.get() != null) {
            String status = queriedUser.get().getStatus();
            if (!Utility.isFieldEmpty(status) && status.equalsIgnoreCase(AppConstants.STATUS_ACTIVE)) {
                return new UserDetailsDecorator(queriedUser.get());
            }
        }

        LOGGER.info("UserServiceImpl->Not found user with mobile " + username);
        throw new UsernameNotFoundException(username);
    }

}
