package com.ayprojects.helpinghands.util.response_msgs;

import java.util.HashMap;
import java.util.Map;

import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_APP_CONFIG_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_APP_CONFIG_FOUND;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_AUTHENTICATION_REQD;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_CATEGORY_IDS_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_CLOSED_OPENS_AT;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_CONGRATULATIONS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMAIL_ALREADY_USED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_BODY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_DEFAULTNAME;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_MOBILE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_IMAGE_TYPE_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_PASSWORD;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_USERNAME;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INVALID_POSTTYPE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_LUNCH_HOURS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MAINCATEGORYID_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MISSING_QUERY_PARAMS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NATURE_OF_BUSINESS_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NOT_FOUND_ANY_PRODUCTS_WITH_SEARCH_KEY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_LUNCH_HOURS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_STICKERS_FOUND;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_TICKETS_FOUND;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OFFER_MSG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OPEN_24INTO7;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OPEN_CLOSES_AT;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_AVAILABILITY_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_DETAILS_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_ID_IS_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_POST_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_RATING_ADDED_TITLE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_REQ_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_TICKET_RAISED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PRODUCT_IMAGES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNKNOWN;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_ID_IS_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_LOGGED_IN;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_USER_REGISTERED;

public class ResponseMsgInEnglish extends AbstractResponseMessages {
    private Map<String, String> responseMsgsMap;

    protected ResponseMsgInEnglish() {
        if (responseMsgsMap == null) {
            responseMsgsMap = new HashMap<>();
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE, "User already exists with given mobile number !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL, "User already exists with given email !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_ACTIVE_USER, "You're not an Active User, Please contact us for more details !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY, "Contact details are missing! MobileNumber & Email are compulsory.");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY, "Address details are missing! Lattitude & Longitude are compulsory.");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_REGISTERED, "Welcome to Helping Hands,\nYou are registered successfully.");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY, "Password is empty!");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_USERNAME, "Incorrect user name !");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_PASSWORD, "Incorrect password !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_LOGGED_IN, "You're logged in successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_ADDED, "New app configuration has been added !");
            responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG, "Unable to add app config !");
            responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG, "Unable to fetch active app config !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED, "User and app config details fetched successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES, "Input details or place category names are missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY, "Please provide the input details !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, "Please provide place category names & name in english is compulsory for main and sub categories !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED, "New category for places has been added .");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_PLACECATEGORIES, "No categories found for places !");
            responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, "Category for place already exists !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, "There is no active place category with given id !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, "New sub category has been added !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES, "Input details or product names are missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY, "Please provide product name !");
            responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_IDS_MISSING, "Main place category or sub place category id's are missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PRODUCT_ADDED, "New product has been added !");
            responseMsgsMap.put(RESPONSEMESSAGE_SOMETHING_WENT_WRONG, "Something went wrong, please try again after some time !");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS, "Product already exists !");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID, "No active products found for given place sub category id!");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, "Your place has been added successfully, people can see it in places section .");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING, "Your place has been added successfully, people can see it in places section once we approve it .");
            responseMsgsMap.put(RESPONSEMESSAGE_CONGRATULATIONS, "Congratulations ");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, "New rating and comment has been added !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_POST_ADDED, "New post has been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, "New requirement has been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_IMAGE_TYPE_MISSING, "Image type is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, "Please check your Image type, no match found for image type!");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_ADDED, "Place images have been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_POST_IMAGES_ADDED, "Post images have been added successfully  !");
            responseMsgsMap.put(RESPONSEMESSAGE_REQ_IMAGES_ADDED, "Requirement images have been added successfully  !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, "Unable to add place images !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES, "Unable to add post images !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, "Unable to add post images !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ID_IS_MISSING, "User id is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_IS_EMPTY, "Mobile number should not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE, "You're already registered with us, please enter your password to use the application !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE, "Looks like you're new to our application, please go ahead and create your profile !");
            responseMsgsMap.put(RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY, "Type of place category is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, "Unable to add place, please choose subcategory again !");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_24INTO7, "Open);for 24*7");
            responseMsgsMap.put(RESPONSEMESSAGE_CLOSED_OPENS_AT, "Closed);Opens at ");
            responseMsgsMap.put(RESPONSEMESSAGE_LUNCH_HOURS, "Open);On Lunch Hours");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_LUNCH_HOURS, "Open);No Lunch Hours");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_CLOSES_AT, "Open);Closes at ");
            responseMsgsMap.put(RESPONSEMESSAGE_UNKNOWN, "Unknwon");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, "User not found !");
            responseMsgsMap.put(RESPONSEMESSAGE_INVALID_POSTTYPE, "Invalid Post Type !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, "Place not found !");
            responseMsgsMap.put(RESPONSEMESSAGE_OFFER_MSG, "Offer valid from ");
            responseMsgsMap.put(RESPONSEMESSAGE_AUTHENTICATION_REQD, "Authentication required !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND, "App configuration not found !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_FOUND, "Found app config with status Active.");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_MOBILE, "Mobile should not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_ALREADY_USED, "Mobile number already used !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMAIL_ALREADY_USED, "Email already used !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY, "Type of place category should not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_DEFAULTNAME, "Default name should not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_MAINCATEGORYID_MISSING, "Main category id must not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_MISSING_QUERY_PARAMS, "Missing query parameters !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_ANY_PRODUCTS_WITH_SEARCH_KEY, "Not found any active products matching with given search keywords !");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_STICKERS_FOUND, "No stickers found !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_ID_IS_MISSING, "Place id is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_TICKET_RAISED, "Ticket raised successfully.");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_TICKETS_FOUND, "No tickets found !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_DETAILS_UPDATED, "Place details have been updated successfully.");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED, "Product details updated successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED, "Product images uploaded successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PRODUCT_IMAGES, "Unable to upload Product images !");
            responseMsgsMap.put(RESPONSEMESSAGE_NATURE_OF_BUSINESS_UPDATED, "Nature of business has been updated successfully.");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_AVAILABILITY_UPDATED, "Place availability details have been updated successfully.");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_UPDATED, "Place images updated successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_RATING_ADDED_TITLE, "New Rating For You !");
        }
    }

    public Map<String, String> getResponseMsgsMap() {
        return responseMsgsMap;
    }


    @Override
    public String getResponseMsg(String key) {
        return getResponseMsgFromMap(key, responseMsgsMap);
    }
}
