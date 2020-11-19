package com.ayprojects.helpinghands.security;

import com.ayprojects.helpinghands.dao.authrorization.AuthorizationDao;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhAuthorization;
import com.ayprojects.helpinghands.models.DhUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.swing.text.html.Option;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /*@Autowired
    AuthorizationDao authorizationDao;*/
    @Autowired
    UserDao userDao;

    @Override
    public UserDetailsDecorator loadUserByUsername(String username) throws UsernameNotFoundException {
        /*Optional<DhAuthorization> fetchedUser = authorizationDao.findByUsername(username);
        if(fetchedUser.isPresent()) return new UserDetailsDecorator(fetchedUser.get());
        LOGGER.info("UserDetailsServiceImpl-> loadUserByUsername : username "+username+"not found : ");

        throw new UsernameNotFoundException(username);*/

        //find out user with mobile and email, as username can be either mobile or email
        Optional<DhUser> fetchedUser = userDao.findByMobileNumberOrEmailId(username,username);
        LOGGER.info("UserServiceImpl-> username="+username);
        /*if(!fetchedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Not found user with mobile "+ username);
            fetchedUser = userDao.findByEmailId(username);
        }*/
        if(fetchedUser.isPresent())return  new UserDetailsDecorator(fetchedUser.get());
        LOGGER.info("UserServiceImpl->Not found user with mobile or emailId, username entered = "+ username);
        throw new UsernameNotFoundException(username);
    }

}
