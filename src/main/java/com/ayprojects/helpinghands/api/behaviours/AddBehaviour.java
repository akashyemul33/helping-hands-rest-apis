package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AddBehaviour<T extends AllCommonUsedAttributes>{
    Response<T> add(String language,MongoTemplate mongoTemplate,T obj) throws ServerSideException;
}
