package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;

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
        //find out user with mobile and email, as username can be either mobile or email
        Optional<DhUser> fetchedUser = userDao.findByMobileNumber(username);
        if(!fetchedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Not found user with mobile "+ username);
            fetchedUser = userDao.findByEmailId(username);
        }

        if(fetchedUser.isPresent())return  new UserDetailsDecorator(fetchedUser.get());

        throw new UsernameNotFoundException(username);
    }

}
