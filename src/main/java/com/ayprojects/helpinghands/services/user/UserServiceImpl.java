package com.ayprojects.helpinghands.services.user;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.dao.user.UserDao;
import com.ayprojects.helpinghands.models.Address;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.models.User;
import com.ayprojects.helpinghands.tools.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    @Override
    public Response<User> signUp(User userDetails, HttpHeaders httpHeaders) {
        String language =  Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("language="+language);
        userDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        Response<User> res = new Response<User>();
        if(Utility.isFieldEmpty(userDetails.getMobileNumber()) || Utility.isFieldEmpty(userDetails.getEmailId())){
            LOGGER.info("Contact details are missing ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY,language));
            res.setData(Collections.singletonList(userDetails));
            return res;
        }

        if(Utility.isFieldEmpty(userDetails.getPassword())){
            LOGGER.info("Password is empty ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY,language));
            res.setData(Collections.singletonList(userDetails));
            return res;
        }

        Address address = userDetails.getAddressDetails();
        if(address==null || address.getLat()==0 || address.getLng()==0){
            LOGGER.info("Address details are missing ");
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY,language));
            res.setData(Collections.singletonList(userDetails));
            return res;
        }

        Optional<User> repeatedUser = userDao.findByMobileNumber(userDetails.getMobileNumber());
        if(!repeatedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Not found user with mobile "+userDetails.getMobileNumber());
            repeatedUser = userDao.findByEmailId(userDetails.getEmailId());
        }

        if(repeatedUser.isPresent()){
            LOGGER.info("UserServiceImpl->Found user with");
            List<User> userResp = new ArrayList<>();
            userResp.add(repeatedUser.get());
            res.setStatus(false);
            res.setStatusCode(402);
            res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS,language));
            res.setData(userResp);
            return res;
        }

        String uniqueID = UUID.randomUUID().toString();

        userDetails.setUserId(uniqueID);
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        userDetails.setCreatedDateTime(Utility.currentDateTimeInUTC());
        userDetails.setModifiedDateTime(Utility.currentDateTimeInUTC());
        userDetails.setStatus(AppConstants.STATUS_ACTIVE);
        userDetails.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        res.setStatus(true);
        res.setStatusCode(201);
        res.setMessage(Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_USER_REGISTERED,language));
        res.setData(Collections.singletonList(userDetails));
        userDao.signUp(userDetails);
        return res;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }
}
