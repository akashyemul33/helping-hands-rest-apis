package com.ayprojects.helpinghands.services.requirements;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.RequirementsRepository;
import com.ayprojects.helpinghands.util.tools.Utility;
import com.ayprojects.helpinghands.util.tools.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class RequirementsServiceImpl implements RequirementsService {

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RequirementsRepository requirementsRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhRequirements> addRequirements(HttpHeaders httpHeaders, Authentication authentication, DhRequirements dhRequirements, String version) throws ServerSideException {
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("RequirementsServiceImpl->addRequirements : language=" + language);

        if (dhRequirements == null) {
            return new Response<DhRequirements>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }


        //fields check against empty
        List<String> missingFieldsList = Validations.findMissingFieldsForRequirements(dhRequirements);
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<DhRequirements>(false,402,resMsg,new ArrayList<>(),0);
        }

        dhRequirements = (DhRequirements) utility.setCommonAttrs(dhRequirements,AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhRequirements,AppConstants.COLLECTION_DH_REQUIREMENTS);
        utility.addLog(authentication.getName(),"New ["+dhRequirements.getRequirementType()+"]  has been added.");
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED,language),new ArrayList<>(),1);
    }

    @Override
    public Response<DhRequirements> deleteRequirement(HttpHeaders httpHeaders, Authentication authentication, String requirementId, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhRequirements> updateRequirement(HttpHeaders httpHeaders, Authentication authentication, DhRequirements dhRequirements, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhRequirements> getAllRequirements(HttpHeaders httpHeaders, Authentication authentication, String searchValue, String version) {
        return null;
    }

    @Override
    public Response<DhRequirements> getPaginatedRequirements(HttpHeaders httpHeaders, Authentication authentication, int page, int size, String version) {
        PageRequest paging = PageRequest.of(page,size);
        Page<DhRequirements> dhRequirementPages = requirementsRepository.findAllByStatus(AppConstants.STATUS_ACTIVE,paging);
        List<DhRequirements> dhRequirementList = dhRequirementPages.getContent();
        return new Response<DhRequirements>(true,201,AppConstants.QUERY_SUCCESSFUL,dhRequirementList.size(),dhRequirementPages.getNumber(),dhRequirementPages.getTotalPages(),dhRequirementPages.getTotalElements(),dhRequirementList);
    }
}
