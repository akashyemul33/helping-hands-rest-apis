package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.ResponseMessages;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AddBehaviour<T>{
    Response<T> add();
}
