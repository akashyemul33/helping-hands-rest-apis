package com.ayprojects.helpinghands.tools;

import com.ayprojects.helpinghands.AppConstants;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.DhRequirements;
import com.ayprojects.helpinghands.models.DhViews;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Validations {

    public static List<String>  findMissingFieldsForViews(DhViews dhViews){
        List<String> missingFieldsList = new ArrayList<>();
        if(dhViews==null)return missingFieldsList;
        if (Utility.isFieldEmpty(dhViews.getAddedBy())) missingFieldsList.add("AddedBy");
        if (Utility.isFieldEmpty(dhViews.getContentType())) missingFieldsList.add("ContentType");
        if (Utility.isFieldEmpty(dhViews.getContentId())) missingFieldsList.add("ContentId");
        return missingFieldsList;
    }

    public static List<String>  findMissingFieldsForRequirements(DhRequirements dhRequirements){
        List<String> missingFieldsList = new ArrayList<>();
        if(dhRequirements==null)return missingFieldsList;
        if(Utility.isFieldEmpty(dhRequirements.getAddedBy())) missingFieldsList.add("AddedBy");
        if(dhRequirements.getAddressDetails()==null)missingFieldsList.add("AddressBlock");
        else {
            if(dhRequirements.getAddressDetails().getLat()==0)
                missingFieldsList.add("Lattitude");
            if(dhRequirements.getAddressDetails().getLng()==0)
                missingFieldsList.add("Longitude");
            if(Utility.isFieldEmpty(dhRequirements.getAddressDetails().getFullAddress())) {
                missingFieldsList.add("Full Address");
            }
        }
        if(dhRequirements.getContactDetails()==null)missingFieldsList.add("ContactBlock");
        else{
            if(Utility.isFieldEmpty(dhRequirements.getContactDetails().getMobile())){
                missingFieldsList.add("MobileNumber");
            }
            if(Utility.isFieldEmpty(dhRequirements.getContactDetails().getEmail())){
                missingFieldsList.add("Email");
            }
        }
        if(Utility.isFieldEmpty(dhRequirements.getRequirementTitle())) missingFieldsList.add("RequirementTitle");
        if(Utility.isFieldEmpty(dhRequirements.getRequirementDesc())) missingFieldsList.add("RequirementDesc");
        return missingFieldsList;
    }
    public static List<String>  findMissingFieldsForRatings(DhRatingAndComments dhRatingComments){
        List<String> missingFieldsList = new ArrayList<>();
        if(dhRatingComments==null)return missingFieldsList;

        if(Utility.isFieldEmpty(dhRatingComments.getAddedBy())) missingFieldsList.add("AddedBy");
        if(dhRatingComments.getRating()<=0) missingFieldsList.add("Rating");
        if(Utility.isFieldEmpty(dhRatingComments.getContentType())) missingFieldsList.add("ContentType");
        if(Utility.isFieldEmpty(dhRatingComments.getContentId())) missingFieldsList.add("ContentId");
        return missingFieldsList;
    }

    public static List<String>  findMissingFieldsForPlaces(DhPlace dhPlace){
        List<String> missingFieldsList = new ArrayList<>();
        if(dhPlace==null)return missingFieldsList;
        if(Utility.isFieldEmpty(dhPlace.getAddedBy())) missingFieldsList.add(AppConstants.ADDED_BY);
        if(Utility.isFieldEmpty(dhPlace.getPlaceMainCategoryId())) missingFieldsList.add(AppConstants.PLACE_MAIN_CATEGORY_ID);
        if(Utility.isFieldEmpty(dhPlace.getPlaceSubCategoryId())) missingFieldsList.add(AppConstants.PLACE_SUB_CATEGORY_ID);
        if(Utility.isFieldEmpty(dhPlace.getPlaceName())) missingFieldsList.add(AppConstants.PLACE_NAME);
        if(Utility.isFieldEmpty(dhPlace.getPlaceType())) missingFieldsList.add(AppConstants.PLACE_TYPE);
        if(Utility.isFieldEmpty(dhPlace.getPlaceCategoryName()))missingFieldsList.add(AppConstants.PLACE_CATEGORY_NAME);
        if(dhPlace.getPlaceAvailablityDays()==null)missingFieldsList.add(AppConstants.PLACE_AVAILABLITY_DAYS);
        if(dhPlace.getPlaceAddress()==null)missingFieldsList.add(AppConstants.PLACE_ADDRESS);
        else {
            if(dhPlace.getPlaceAddress().getLat()==0)
                missingFieldsList.add(AppConstants.LATTITUDE);
            if(dhPlace.getPlaceAddress().getLng()==0)
                missingFieldsList.add(AppConstants.LONGITUDE);
            if(Utility.isFieldEmpty(dhPlace.getPlaceAddress().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }
        if(dhPlace.getPlaceContact()==null)missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else{
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getMobile())){
                missingFieldsList.add(AppConstants.MOBILE);
            }
            if(Utility.isFieldEmpty(dhPlace.getPlaceContact().getEmail())){
                missingFieldsList.add(AppConstants.EMAIL);
            }
        }
        if(dhPlace.getOwnerDetails()==null)missingFieldsList.add(AppConstants.OWNER_DETAILS);
        else{
            if(Utility.isFieldEmpty(dhPlace.getOwnerDetails().getOwnerName()))missingFieldsList.add(AppConstants.OWNER_NAME);
            if(Utility.isFieldEmpty(dhPlace.getOwnerDetails().getOwnerMobileNumber()))missingFieldsList.add(AppConstants.OWNER_MOBILE_NUMBER);
            if(Utility.isFieldEmpty(dhPlace.getOwnerDetails().getOwnerEmailId()))missingFieldsList.add(AppConstants.OWNER_EMAIL_ID);
        }

        return missingFieldsList;

    }
    public static List<String>  findMissingFieldsForPosts(DhPosts dhPosts){
        List<String> missingFieldsList = new ArrayList<>();
        if(dhPosts==null)return missingFieldsList;
        if(Utility.isFieldEmpty(dhPosts.getAddedBy())) missingFieldsList.add(AppConstants.ADDED_BY);
        if(Utility.isFieldEmpty(dhPosts.getPostType())) missingFieldsList.add(AppConstants.POST_TYPE);
        else if(dhPosts.getPostType().matches(AppConstants.REGEX_BUSINESS_POST)) {
            if(Utility.isFieldEmpty(dhPosts.getPlaceId())){
                missingFieldsList.add(AppConstants.PLACE_ID);
            }
        }
        if(dhPosts.getAddressDetails()==null)missingFieldsList.add(AppConstants.ADDRESS_DETAILS);
        else {
            if(dhPosts.getAddressDetails().getLat()==0)
                missingFieldsList.add(AppConstants.LATTITUDE);
            if(dhPosts.getAddressDetails().getLng()==0)
                missingFieldsList.add(AppConstants.LONGITUDE);
            if(Utility.isFieldEmpty(dhPosts.getAddressDetails().getFullAddress())) {
                missingFieldsList.add(AppConstants.FULL_ADDRESS);
            }
        }
        if(dhPosts.getContactDetails()==null)missingFieldsList.add(AppConstants.CONTACT_DETAILS);
        else{
            if(Utility.isFieldEmpty(dhPosts.getContactDetails().getMobile())){
                missingFieldsList.add(AppConstants.MOBILE);
            }
            if(Utility.isFieldEmpty(dhPosts.getContactDetails().getEmail())){
                missingFieldsList.add(AppConstants.EMAIL);
            }
        }
        if(Utility.isFieldEmpty(dhPosts.getPostTitle())) missingFieldsList.add(AppConstants.POST_TITLE);
        return missingFieldsList;
    }
}
