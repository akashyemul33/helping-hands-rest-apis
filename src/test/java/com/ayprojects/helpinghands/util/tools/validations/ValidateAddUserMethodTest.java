package com.ayprojects.helpinghands.util.tools.validations;


import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.UserRepository;
import com.ayprojects.helpinghands.security.UserDetailsServiceImpl;
import com.ayprojects.helpinghands.util.response_msgs.ResponseMsgFactory;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidateAddUserMethodTest {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    UserDao userDao;

    @Autowired
    MongoTemplate mongoTemplate;

    @MockBean
    UserRepository userRepository;

    @Test
    void userDaoShouldBeLoaded() {
        assertNotNull(userDao);
    }

    @Test
    void userRepoShouldBeLoaded() {
        assertNotNull(userRepository);
    }

    @Test
    void givenEmptyDhUserThen402() {
        Response<DhUser> expectedResponse = new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMPTY_BODY), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, null,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyMobileThen402() {
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber("");
        Response<DhUser> expectedResponse = new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));

        DhUser dhUser2 = new DhUser();
        dhUser2.setMobileNumber(null);
        Response<DhUser> actualResponse2 = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse2.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse2.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse2.getMessage()));
    }

    @Test
    void givenAlreadyAddedMobileThen402() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        when(userDao.findByMobileNumber(mobile)).thenReturn(java.util.Optional.of(dhUser));

        Response<DhUser> expectedResponse = new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }


    @Test
    void givenEmailProvidedAndItIsAlreadyAddedThen402() {
        String mobile = "7987787878";
        String email = "akash@gmail.com";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setEmailId(email);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);
        when(userRepository.findByEmailId(email)).thenReturn(java.util.Optional.of(dhUser));

        Response<DhUser> expectedResponse = new Response<DhUser>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_EMAIL_ALREADY_USED), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenEmptyPasswordThen402() {
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);

        Response<DhUser> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));

    }


    @Test
    void givenProfileImgButNotUserIdThen402(){
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setProfileImg("asdfjljoiwer");
        dhUser.setPassword("adsfkljk");
        dhUser.setStatus(AppConstants.STATUS_ACTIVE);

        Response<DhUser> expectedResponse = new Response<>(false, 402, ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING), new ArrayList<>());
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }


    @Test
    void givenValidDhUserWithEmptyProfileThen201(){
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setPassword("adsfkljk");
        dhUser.setEmailId("adkf@gmail.com");
        dhUser.setFirstName("adsf");
        dhUser.setLastName("asdfdf");

        Response<DhUser> expectedResponse = new Response<>(true, 201, dhUser.getFirstName() + " Sir/Madam", ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUser));
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }

    @Test
    void givenValidDhUserWithNonEmptyProfileThen201(){
        String mobile = "7987787878";
        DhUser dhUser = new DhUser();
        dhUser.setMobileNumber(mobile);
        dhUser.setProfileImg("adf");
        dhUser.setUserId("zfgdfgrt463sdg");
        dhUser.setPassword("adsfkljk");
        dhUser.setEmailId("adkf@gmail.com");
        dhUser.setFirstName("adsf");
        dhUser.setLastName("asdfdf");

        Response<DhUser> expectedResponse = new Response<>(true, 201, dhUser.getFirstName() + " Sir/Madam", ResponseMsgFactory.getResponseMsg(null, AppConstants.RESPONSEMESSAGE_USER_REGISTERED), Collections.singletonList(dhUser));
        Response<DhUser> actualResponse = Validations.validateAddUser(null, dhUser,mongoTemplate, null);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertTrue(expectedResponse.getMessage().equalsIgnoreCase(actualResponse.getMessage()));
    }
}
