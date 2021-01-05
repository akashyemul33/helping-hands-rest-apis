package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.data.mongodb.core.MongoTemplate;

public class AddPostApi implements AddBehaviour<DhPosts> {
    @Override
    public Response<DhPosts> add(String language, MongoTemplate mongoTemplate, DhPosts obj) {
        return null;
    }
}
