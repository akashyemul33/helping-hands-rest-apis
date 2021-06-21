package com.ayprojects.helpinghands.util.response_msgs;

import java.util.HashMap;
import java.util.Map;

import static com.ayprojects.helpinghands.AppConstants.*;

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
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PROMOTION_ADDED, "New post has been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, "New requirement has been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_IMAGE_TYPE_MISSING, "Image type is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, "Please check your Image type, no match found for image type!");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_ADDED, "Place images have been added successfully !");
            responseMsgsMap.put(RESPONSEMESSAGE_PROMOTION_IMAGES_ADDED, "Post images have been added successfully  !");
            responseMsgsMap.put(RESPONSEMESSAGE_REQ_IMAGES_ADDED, "Requirement images have been added successfully  !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, "Unable to add place images !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PROMOTION_IMAGES, "Unable to add post images !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, "Unable to add post images !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ID_IS_MISSING, "User id is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_IS_EMPTY, "Mobile number should not be empty !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE, "You're already registered with us, please enter your password to use the application !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE, "Looks like you're new to our application, please go ahead and create your profile !");
            responseMsgsMap.put(RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY, "Type of place category is missing !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, "Unable to add place, please choose subcategory again !");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_24INTO7, "Open);for 24*7");
            responseMsgsMap.put(RESPONSEMESSAGE_CLOSED_OPENS_AT, "Closed);Opens at ");
            responseMsgsMap.put(RESPONSEMESSAGE_LUNCH_HOURS, "On Lunch Hours");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_LUNCH_HOURS, "Open);No Lunch Hours");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_CLOSES_AT, "Open);Closes at ");
            responseMsgsMap.put(RESPONSEMESSAGE_UNKNOWN, "Unknwon");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, "User not found !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_HELPED_USERID, "Helped User not found !");
            responseMsgsMap.put(RESPONSEMESSAGE_INVALID_PROMOTIONTYPE, "Invalid Post Type !");
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
            responseMsgsMap.put(RESPONSEMESSAGE_RATING_REPLIED_TITLE, "Reply on your comment !");
            responseMsgsMap.put(RESPONSEMESSAGE_CLOSED, "Closed");
            responseMsgsMap.put(RESPONSEMESSAGE_OPENS_WITHIN_HALF_HOUR, "Opens within 30 mins");
            responseMsgsMap.put(RESPONSEMESSAGE_OPENS_AT_TIME, "Opens at %s");
            responseMsgsMap.put(RESPONSEMESSAGE_FEW_MINS_AGO, "Few mins ago");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN, "Open");
            responseMsgsMap.put(RESPONSEMESSAGE_CLOSES_AT_TIME, "Closes at %s");
            responseMsgsMap.put(RESPONSEMESSAGE_REQUESTED_FOR_PRODUCT_PRICES, "Your request placed, You will be notified once owner approves your request");
            responseMsgsMap.put(RESPONSEMESSAGE_UPDATED_SHOW_PRODUCT_PRICES_REQUEST, "Updated show product prices request");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_PLACE_SHOW_PRICES_REQUEST, "Unable to place show product prices request !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICESREQUEST_PLACED, "New request for you !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_SHOWPRICESREQUEST_PLACED, "%s has requested to show product prices !");
            responseMsgsMap.put(RESPONSEMESSAGE_CURRENT_STATUS_UPDATED, "You are %s now ");
            responseMsgsMap.put(RESPONSEMESSAGE_ONLINE, "Online");
            responseMsgsMap.put(RESPONSEMESSAGE_OFFLINE, "Offline");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCTPRICES_HIDDEN_NOW, "Now nobody can see your product prices.");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCTPRICES_PUBLIC_NOW, "Now everyone can see your product prices.");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCTPRICES_ONLY_REQUESTED, "Now your product prices are visible to requested people only .");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICES_REQ_REJECTED, "Update on your request !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_SHOWPRICES_REQ_REJECTED, "Your request for showing product prices to place %s has been rejected by owner !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_TITLE_SHOWPRICES_REQ_APPROVED, "Update on your request !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_SHOWPRICES_REQ_APPROVED, "Your request for showing product prices to place %s has been approved by owner !");
            responseMsgsMap.put(RESPONSEMESSAGE_HH_POST_IMAGES_ADDED, "Post images have been uploaded successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_HH_POST_IMAGES, "Unable to add post images !");
            responseMsgsMap.put(RESPONSEMESSAGE_IMAGES_SIZE_GREATER_THAN_MAX, "Number of images is greater than max image upload limit !");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_RECORDS_FOUND, "No Records Found !");
            responseMsgsMap.put(RESPONSEMESSAGE_HH_ADD_POST_MSG, "Thank You ");
            responseMsgsMap.put(RESPONSEMESSAGE_HH_ADD_POST_BODY, "Team Duniyaa, feeling proud being part of society where people like you are residing who are doing their part to help people.We will send this post to our 135 Lakh users around us who can help these people.");
            responseMsgsMap.put(RESPONSEMESSAGE_HH_MARK_POST_HELPED_MSG, "Great Work");
            responseMsgsMap.put(RESPONSEMESSAGE_HH_MARK_POST_HELPED_BODY, "Team Duniyaa, really appreciates your work towards society. Your nature of helping people in the society is remarkable.");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_TITLE_HH_HELPED, "Update on your Helping Hands Post");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_HH_HELPED, "%s has helped the people by reacting to your post.");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_HH_HELPED_NONAME, "Anonymous person helped the people by reacting to your post.");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_TITLE_HH_POST_COMMENT_ADD, "Update on your post !");
            responseMsgsMap.put(RESPONSEMESSAGE_NTFN_BODY_HH_POST_COMMENT_ADD, "%s has commented on your post ");
            responseMsgsMap.put(RESPONSEMESSAGE_COMMENTS_TURNED_ON_OFF, "Comments are turned %s");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_SCHEDULED_NOTE, "Thought will be available at hour %s of the day");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_NOTE, "Everybody reads, only few follows. Are you reader only?");
            responseMsgsMap.put(RESPONSEMESSAGE_VIDEOS_SIZE_GREATER_THAN_MAX, "Promotions max videos limit is 4 ");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHTS_DAILY_LIMIT_REACHED, "We regret, todays thoughts intake limit is reached !");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHTS_NOT_POSTED_OR_HELPED, "You've to add atleast one person seeking for help or you've to help one person %s ! Your last post was %s and last post you helped is %s");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_CANNOT_ADD_HEADING, "We Regret !");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_ADDED_BODY, "Your thought has been added. It will be live tomorrow, if you get maximum likes throughout day you will get your prize money for sure.");
            responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_ADDED_HEADING, "Congratulations !");

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
