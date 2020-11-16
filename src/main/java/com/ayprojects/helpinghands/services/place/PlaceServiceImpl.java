package com.ayprojects.helpinghands.services.place;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.Response;
import com.ayprojects.helpinghands.repositories.PlaceRepository;
import com.ayprojects.helpinghands.tools.Utility;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.ayprojects.helpinghands.HelpingHandsApplication.LOGGER;

@Service
public class PlaceServiceImpl implements PlaceService{

    @Value("${images.base_folder}")
    String imagesBaseFolder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    Utility utility;

    @Override
    public Response<DhPlace> addPlace(Authentication authentication, HttpHeaders httpHeaders, MultipartFile[] placeImages, String placeBody, String version) throws ServerSideException{
        String language = Utility.getLanguageFromHeader(httpHeaders).toUpperCase();
        LOGGER.info("PlaceServiceImpl->addPlace : language=" + language);
        //convert placebody json string to DhPlace object
        DhPlace dhPlace = null;
        if(!Utility.isFieldEmpty(placeBody)) dhPlace = new Gson().fromJson(placeBody, DhPlace.class);

        if (dhPlace == null) {
            return new Response<DhPlace>(false, 402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY, language), new ArrayList<>(), 0);
        }

        List<String> missingFieldsList = new ArrayList<>();
        if(Utility.isFieldEmpty(dhPlace.getAddedBy())) missingFieldsList.add("AddedBy");
        if(Utility.isFieldEmpty(dhPlace.getPlaceMainCategoryId())) missingFieldsList.add("PlaceMainCategoryId");
        if(Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) missingFieldsList.add("PlaceSubCategoryId");
        if(Utility.isFieldEmpty(dhPlace.getPlaceName())) missingFieldsList.add("PlaceName");
        if(Utility.isFieldEmpty(dhPlace.getPlaceType())) missingFieldsList.add("PlaceType");
        if(Utility.isFieldEmpty(dhPlace.getPlaceCategoryName()))missingFieldsList.add("PlaceCategoryName");
        if(dhPlace.getPlaceAddress()==null)missingFieldsList.add("PlaceAddressBlock");
        else {
            if(dhPlace.getPlaceAddress().getLat()==0)
                missingFieldsList.add("Lattitude");
            if(dhPlace.getPlaceAddress().getLng()==0)
                missingFieldsList.add("Longitude");
            if(Utility.isFieldEmpty(dhPlace.getPlaceAddress().getFullAddress())) {
                missingFieldsList.add("Full Address");
            }
        }
        if(dhPlace.getPlaceContact()==null)missingFieldsList.add("PlaceContactBlock");
        else{
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile())){
                missingFieldsList.add("MobileNumber");
            }
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getEmail())){
                missingFieldsList.add("Email");
            }
        }
        if(dhPlace.getPlaceAvailablityDays()==null)missingFieldsList.add("PlaceAvailabilityDaysBlock");
        if(missingFieldsList.size()>0){
            String resMsg = Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_EMPTY_BODY,language);
            resMsg = resMsg+" , these fields are missing : "+missingFieldsList;
            return new Response<DhPlace>(false,402,resMsg,new ArrayList<>(),0);
        }
        String uinquePlaceId= Utility.getUUID();
        //store image first
        if(placeImages!=null){
            try {
                String imgType = dhPlace.getPlaceType().matches(AppConstants.REGEX_BUSINESS_PLACE) ? "B" : "P";
                String imgUploadFolder = imagesBaseFolder + "/" + dhPlace.getAddedBy() + "/places/" + dhPlace.getPlaceType() + "/";
                LOGGER.info("PlaceServiceImpl->addPlace : imagesBaseFolder = " + imagesBaseFolder);
                Path path1 = Paths.get(imgUploadFolder);
                Files.createDirectories(path1);
                for (MultipartFile multipartFile : placeImages) {
                    String ext = multipartFile.getOriginalFilename().split("\\.")[1];
                    String imgFileName = imgType + "_PLACE_" + uinquePlaceId + "_" + Calendar.getInstance().getTimeInMillis() + "." + ext;
                    String imgUploadFolderWithFile = imgUploadFolder + imgFileName;
                    LOGGER.info("PlaceServiceImpl->addPlace : imgUploadFolderWithFile = " + imgUploadFolderWithFile);
                    byte[] bytes = multipartFile.getBytes();
                    Path filePath = Paths.get(imgUploadFolderWithFile);
                    Files.probeContentType(filePath);
                    Files.write(filePath, bytes);
                    dhPlace.getPlaceImages().add(imgUploadFolderWithFile);
                }
            }
            catch (IOException ioException){
                return new Response<>(false,402, Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG,language),new ArrayList<>());
            }
        }

        dhPlace.setPlaceId(uinquePlaceId);
        dhPlace.setSchemaVersion(AppConstants.SCHEMA_VERSION);
        dhPlace.setCreatedDateTime(Utility.currentDateTimeInUTC());
        dhPlace.setModifiedDateTime(Utility.currentDateTimeInUTC());
        dhPlace.setStatus(AppConstants.STATUS_ACTIVE);
        mongoTemplate.save(dhPlace,AppConstants.COLLECTION_DH_PLACE);
        utility.addLog(authentication.getName(),"New ["+dhPlace.getPlaceType()+"] place with category ["+dhPlace.getPlaceCategoryName()+"] has been added.");
        return new Response<>(true,201,Utility.getResponseMessage(AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED,language),new ArrayList<>(),1);
    }

    @Override
    public Response<DhPlace> deletePlace(Authentication authentication, HttpHeaders httpHeaders, String placeId, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPlace> updatePlace(Authentication authentication, HttpHeaders httpHeaders, DhPlace dhPlace, String version) throws ServerSideException {
        return null;
    }

    @Override
    public Response<DhPlace> getPlaces(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version) {
        return null;
    }

    @Override
    public Response<DhPlace> getPaginatedPlaces(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version) {
        PageRequest paging = PageRequest.of(page,size);
        Page<DhPlace> dhPlacePages = placeRepository.findAllByStatus(AppConstants.STATUS_ACTIVE,paging);
        List<DhPlace> dhPlaceList = dhPlacePages.getContent();
        return new Response<DhPlace>(true,201,"Query successful",dhPlaceList.size(),dhPlacePages.getNumber(),dhPlacePages.getTotalPages(),dhPlacePages.getTotalElements(),dhPlaceList);
    }
}
