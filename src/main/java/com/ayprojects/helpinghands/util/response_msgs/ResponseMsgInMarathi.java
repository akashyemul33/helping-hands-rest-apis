package com.ayprojects.helpinghands.util.response_msgs;

import java.util.HashMap;
import java.util.Map;

import static com.ayprojects.helpinghands.AppConstants.*;

public class ResponseMsgInMarathi extends AbstractResponseMessages {

    private final Map<String, String> responseMsgsMap;

    protected ResponseMsgInMarathi() {
        responseMsgsMap = new HashMap<>();
        responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE, "दिलेल्या मोबाइल नंबरसह वापरकर्ता आधीपासून विद्यमान आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL, "दिलेल्या ईमेलसह वापरकर्ता आधीपासून विद्यमान आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NOT_ACTIVE_USER, "आपण एक सक्रिय वापरकर्ता नाही, कृपया अधिक माहितीसाठी आमच्याशी संपर्क साधा !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY, "संपर्क तपशील गहाळ आहेत! मोबाइल नंबर आणि ईमेल अनिवार्य आहेत.");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY, "पत्त्याचा तपशील गहाळ आहे! अक्षांश आणि रेखांश अनिवार्य आहेत.");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_REGISTERED, "हेलपिंग हॅन्ड्समध्ये आपले स्वागत आहे, \nआपण यशस्वीरित्या नोंदणीकृत केले आहात.");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY, "संकेतशब्द रिक्त आहे!");
        responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_USERNAME, "वापरकर्त्याचे नाव चुकीचे आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_PASSWORD, "संकेतशब्द चुकीचा आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_LOGGED_IN, "आपण यशस्वीरित्या लॉग इन केले आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_ADDED, "नवीन अॅप कॉन्फिगरेशन जोडले गेले आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG, "अॅप कॉन्फिगरेशन जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG, "सक्रिय  अॅप कॉन्फिगरेशन आणण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED, "वापरकर्ता आणि अॅप कॉन्फिगरेशन तपशील यशस्वीरित्या प्राप्त केले !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES, "इनपुट तपशील किंवा ठिकाण श्रेणी नावे गहाळ आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY, "कृपया इनपुट विनंतीचे मुख्य भाग प्रदान करा!");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, "कृपया स्थान श्रेणी नावे प्रदान करा & मुख्य आणि उप श्रेण्यांसाठी इंग्रजीमधील नाव अनिवार्य आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED, "ठिकाणांसाठी नवीन श्रेणी जोडली गेली आहे .");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_PLACECATEGORIES, "ठिकाणांसाठी कोणत्याही श्रेण्या आढळल्या नाहीत !");
        responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, "स्थानासाठी वर्ग आधीपासून विद्यमान आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, "दिलेल्या आयडीसह सक्रिय स्थानाची श्रेणी नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, "नवीन उप श्रेणी जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES, "इनपुट तपशील किंवा उत्पादनांची नावे गहाळ आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY, "कृपया उत्पादनांची नावे द्या !");
        responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_IDS_MISSING, "मुख्य ठिकाण श्रेणी किंवा उप-स्थान श्रेणी आयडी गहाळ आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PRODUCT_ADDED, "नवीन उत्पादन जोडले गेले आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_SOMETHING_WENT_WRONG, "काहीतरी चूक झाली, कृपया थोड्या वेळाने पुन्हा प्रयत्न करा !");
        responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS, "उत्पादन आधीपासून विद्यमान आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID, "दिलेल्या ठिकाणी उप श्रेणी आयडीसाठी कोणतीही सक्रिय उत्पादने आढळली नाहीत !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, "आपले ठिकाण यशस्वीरित्या जोडले गेले आहे, आपले लोक ते स्थान विभागात पाहू शकतात .");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING, "आपले ठिकाण यशस्वीरित्या जोडले गेले आहे, एकदा आम्ही ते मंजूर केले तेव्हा आपले लोक ते ठिकाणी विभागात पाहू शकतात.");
        responseMsgsMap.put(RESPONSEMESSAGE_CONGRATULATIONS, "अभिनंदन ");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, "नवीन रेटिंग आणि टिप्पणी जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PROMOTION_ADDED, "नवीन पोस्ट यशस्वीरित्या जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, "नवीन आवश्यकता यशस्वीरित्या जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_IMAGE_TYPE_MISSING, "प्रतिमेचा प्रकार गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, "कृपया आपला प्रतिमा प्रकार तपासा, प्रतिमा प्रकारासाठी कोणतीही जुळणी आढळली नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_ADDED, "ठिकाण प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_PROMOTION_IMAGES_ADDED, "पोस्ट प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_REQ_IMAGES_ADDED, "आवश्यकता  प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, "ठिकाण प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PROMOTION_IMAGES, "पोस्ट प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, "आवश्यकता प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_ID_IS_MISSING, "वापरकर्ता आयडी गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_IS_EMPTY, "मोबाइल नंबर रिक्त नसावा !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE, "आपण आधीपासून आमच्याकडे नोंदणीकृत आहात, कृपया अनुप्रयोग वापरण्यासाठी आपला संकेतशब्द प्रविष्ट करा !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE, "आमच्या अॅप मधे आपण नवीन आहात असे दिसते आहे, कृपया पुढे जा आणि आपले प्रोफाइल तयार करा!");
        responseMsgsMap.put(RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY, "ठिकाण प्रकाराचा प्रकार गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, "जागा जोडण्यात अक्षम, कृपया पुन्हा उपश्रेणी निवडा !");
        responseMsgsMap.put(RESPONSEMESSAGE_OPEN_24INTO7, "खुला);24 * 7 साठी");
        responseMsgsMap.put(RESPONSEMESSAGE_CLOSED_OPENS_AT, "बंद);वाजता उघडते ");
        responseMsgsMap.put(RESPONSEMESSAGE_LUNCH_HOURS, "दुपारच्या जेवणाच्या वेळी");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_LUNCH_HOURS, "उघडा);दुपारचे जेवण नाही");
        responseMsgsMap.put(RESPONSEMESSAGE_OPEN_CLOSES_AT, "उघडा);बंद केले जाईल");
        responseMsgsMap.put(RESPONSEMESSAGE_UNKNOWN, "अज्ञात");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, "वापरकर्ता आढळला नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_HELPED_USERID, "वापरकर्ता आढळला नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_INVALID_PROMOTIONTYPE, "अवैध पोस्ट प्रकार !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, "जागा सापडली नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_OFFER_MSG, "ऑफर वैधता ");
        responseMsgsMap.put(RESPONSEMESSAGE_AUTHENTICATION_REQD, "प्रमाणीकरण आवश्यक !");
        responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND, "अॅप कॉन्फिगरेशन आढळले नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_FOUND, "सक्रिय स्थितीसह अॅप कॉन्फिगरेशन आढळले.");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_MOBILE, "मोबाइल रिकामा असू नये !");
        responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_ALREADY_USED, "मोबाइल नंबर आधीपासून वापरलेला आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMAIL_ALREADY_USED, "ईमेल आधीच वापरलेला आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY, "ठिकाण श्रेणीचा प्रकार रिक्त नसावा !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_DEFAULTNAME, "डीफॉल्ट नाव रिक्त नसावे !");
        responseMsgsMap.put(RESPONSEMESSAGE_MAINCATEGORYID_MISSING, "मुख्य श्रेणी आयडी रिक्त नसावा !");
        responseMsgsMap.put(RESPONSEMESSAGE_MISSING_QUERY_PARAMS, "क्वेरी पॅरामीटर्स गहाळ आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_ANY_PRODUCTS_WITH_SEARCH_KEY, "दिलेल्या शोध कीवर्डशी जुळणारी कोणतीही सक्रिय उत्पादने आढळली नाहीत !");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_STICKERS_FOUND, "कोणतेही स्टिकर्स आढळले नाहीत !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_ID_IS_MISSING, "प्लेस आयडी गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_TICKET_RAISED, "तिकीट यशस्वीरित्या जोडले.");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_TICKETS_FOUND, "तिकिटे सापडली नाहीत !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_DETAILS_UPDATED, "ठिकाण तपशील यशस्वीरित्या अद्यतनित केले गेले आहेत.");
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
        responseMsgsMap.put(RESPONSEMESSAGE_THOUGHTS_NOT_POSTED_OR_HELPED, "You've to add atleast one person seeking for help or you've to help one person in a %s !");
        responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_CANNOT_ADD_HEADING, "We Regret !");
        responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_ADDED_BODY, "Your thought has been added. It will be live tomorrow, if you get maximum likes throughout day you will get your prize money for sure.");
        responseMsgsMap.put(RESPONSEMESSAGE_THOUGHT_ADDED_HEADING, "Congratulations !");
    }

    public Map<String, String> getResponseMsgsMap() {
        return responseMsgsMap;
    }

    @Override
    public String getResponseMsg(String key) {
        return getResponseMsgFromMap(key, responseMsgsMap);
    }
}
