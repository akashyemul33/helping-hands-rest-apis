package com.ayprojects.helpinghands.services.user_details;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.security.UserDetailsDecorator;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoadByUserNameTest {

    @MockBean
    UserDao userDao;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Test
    void givenEmptyUserNameThenException() {
        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername(null);
            }
        });

        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername("");
            }
        });

    }

    @Test
    void givenInvalidMobileThenException() {
        String mobile = "7987787878";
        Mockito.when(userDao.findByMobileNumber(mobile)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername(mobile);
            }
        });
    }

    @Test
    void givenValidMobileWithStatusOtherThanActiveThenException() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_PENDING);
        Mockito.when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername(mobile);
            }
        });
    }

    @Test
    void givenValidMobileWithStatusActiveThenSucceed() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        Mockito.when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        UserDetailsDecorator expectedResponse = new UserDetailsDecorator(dhUser);
        UserDetailsDecorator actualResponse = userDetailsService.loadUserByUsername(mobile);
        assertTrue(expectedResponse.getUsername().equalsIgnoreCase(actualResponse.getUsername()));
    }

    @Test
    void givenValidMobileWithStatusNullThenException() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(null);
        Mockito.when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername(mobile);
            }
        });
    }
    @Test
    void givenValidMobileWithoutStatusThenException() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        Mockito.when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        assertThrows(UsernameNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userDetailsService.loadUserByUsername(mobile);
            }
        });
    }

}
