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
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_EMPTY_MOBILE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_IMAGE_TYPE_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_PASSWORD;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INCORRECT_USERNAME;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_INVALID_POSTTYPE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_LUNCH_HOURS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MOBILE_ALREADY_USED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_MOBILE_IS_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_POST_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_PRODUCT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NOT_ACTIVE_USER;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_LUNCH_HOURS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_PLACECATEGORIES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OFFER_MSG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OPEN_24INTO7;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_OPEN_CLOSES_AT;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_POST_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_REQ_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_SOMETHING_WENT_WRONG;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES;
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
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, "नवीन ठिकाण यशस्वीरित्या जोडले गेले आहे, आपले लोक ते स्थान विभागात पाहू शकतात .");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING, "नवीन ठिकाण यशस्वीरित्या जोडले गेले आहे, एकदा आम्ही ते मंजूर केले तेव्हा आपले लोक ते ठिकाणी विभागात पाहू शकतात.");
        responseMsgsMap.put(RESPONSEMESSAGE_CONGRATULATIONS, "अभिनंदन ");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, "नवीन रेटिंग आणि टिप्पणी जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_POST_ADDED, "नवीन पोस्ट यशस्वीरित्या जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, "नवीन आवश्यकता यशस्वीरित्या जोडली गेली आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_IMAGE_TYPE_MISSING, "प्रतिमेचा प्रकार गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, "कृपया आपला प्रतिमा प्रकार तपासा, प्रतिमा प्रकारासाठी कोणतीही जुळणी आढळली नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_ADDED, "ठिकाण प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_POST_IMAGES_ADDED, "पोस्ट प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_REQ_IMAGES_ADDED, "आवश्यकता  प्रतिमा यशस्वीरित्या जोडल्या गेल्या आहेत !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, "ठिकाण प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES, "पोस्ट प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, "आवश्यकता प्रतिमा जोडण्यात अक्षम !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_ID_IS_MISSING, "वापरकर्ता आयडी गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_IS_EMPTY, "मोबाइल नंबर रिक्त नसावा !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE, "आपण आधीपासून आमच्याकडे नोंदणीकृत आहात, कृपया अनुप्रयोग वापरण्यासाठी आपला संकेतशब्द प्रविष्ट करा !");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE, "आमच्या अॅप मधे आपण नवीन आहात असे दिसते आहे, कृपया पुढे जा आणि आपले प्रोफाइल तयार करा!");
        responseMsgsMap.put(RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY, "ठिकाण प्रकाराचा प्रकार गहाळ आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, "जागा जोडण्यात अक्षम, कृपया पुन्हा उपश्रेणी निवडा !");
        responseMsgsMap.put(RESPONSEMESSAGE_OPEN_24INTO7, "खुला);24 * 7 साठी");
        responseMsgsMap.put(RESPONSEMESSAGE_CLOSED_OPENS_AT, "बंद);वाजता उघडते ");
        responseMsgsMap.put(RESPONSEMESSAGE_LUNCH_HOURS, "उघडा);दुपारच्या जेवणाच्या वेळी");
        responseMsgsMap.put(RESPONSEMESSAGE_NO_LUNCH_HOURS, "उघडा);दुपारचे जेवण नाही");
        responseMsgsMap.put(RESPONSEMESSAGE_OPEN_CLOSES_AT, "उघडा);बंद केले जाईल");
        responseMsgsMap.put(RESPONSEMESSAGE_UNKNOWN, "अज्ञात");
        responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, "वापरकर्ता आढळला नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_INVALID_POSTTYPE, "अवैध पोस्ट प्रकार !");
        responseMsgsMap.put(RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, "जागा सापडली नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_OFFER_MSG, "ऑफर वैधता ");
        responseMsgsMap.put(RESPONSEMESSAGE_AUTHENTICATION_REQD, "प्रमाणीकरण आवश्यक !");
        responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND, "अॅप कॉन्फिगरेशन आढळले नाही !");
        responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_FOUND, "सक्रिय स्थितीसह अॅप कॉन्फिगरेशन आढळले.");
        responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_MOBILE, "मोबाइल रिकामा असू नये !");
        responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_ALREADY_USED, "मोबाइल नंबर आधीपासून वापरलेला आहे !");
        responseMsgsMap.put(RESPONSEMESSAGE_EMAIL_ALREADY_USED, "ईमेल आधीच वापरलेला आहे !");

    }

    public Map<String, String> getResponseMsgsMap() {
        return responseMsgsMap;
    }


    @Override
    public String getResponseMsg(String key) {
        return getResponseMsgFromMap(key,responseMsgsMap);
    }
}