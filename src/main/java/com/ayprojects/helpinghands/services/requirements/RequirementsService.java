package com.ayprojects.helpinghands.services.requirements;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface RequirementsService {
    Response<DhRequirements> addRequirements(HttpHeaders httpHeaders, Authentication authentication, DhRequirements dhRequirements, String version) throws ServerSideException;
    Response<DhRequirements> deleteRequirement( HttpHeaders httpHeaders, Authentication authentication,  String requirementId,String version) throws ServerSideException ;
    Response<DhRequirements> updateRequirement( HttpHeaders httpHeaders, Authentication authentication,  DhRequirements dhRequirements, String version) throws ServerSideException ;
    Response<DhRequirements> getAllRequirements( HttpHeaders httpHeaders, Authentication authentication, String searchValue,String version);
    Response<DhRequirements> getPaginatedRequirements( HttpHeaders httpHeaders, Authentication authentication,int page, int size, String version);

}
