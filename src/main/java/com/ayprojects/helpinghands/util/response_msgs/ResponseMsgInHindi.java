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
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_DETAILS_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_ID_IS_MISSING;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_POST_IMAGES_ADDED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY;
import static com.ayprojects.helpinghands.AppConstants.RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED;
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

public class ResponseMsgInHindi extends AbstractResponseMessages {

    private Map<String, String> responseMsgsMap;

    protected ResponseMsgInHindi() {
        if (responseMsgsMap == null) {
            responseMsgsMap = new HashMap<>();
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE, "उपयोगकर्ता पहले से ही दिए गए मोबाइल नंबर के साथ मौजूद है !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL, "उपयोगकर्ता पहले से ही दिए गए ईमेल के साथ मौजूद है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_ACTIVE_USER, "आप एक सक्रिय उपयोगकर्ता नहीं हैं, कृपया अधिक जानकारी के लिए हमसे संपर्क करें !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY, "संपर्क विवरण गायब हैं! मोबाइल नंबर और ईमेल अनिवार्य है ।");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY, "पते का विवरण गायब है ! लेटिट्यूड एंड लॉन्गिट्यूड अनिवार्य है ।");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_REGISTERED, "हेल्पिंग हैंड्समे आपका स्वागत है,\nआप सफलतापूर्वक पंजीकृत किया हैं। ।");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY, "पासवर्ड खाली है!");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_USERNAME, "उपयोगकर्ता नाम गलत है !");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_PASSWORD, "पासवर्ड गलत है !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_LOGGED_IN, "आप सफलतापूर्वक लॉग इन हो गए हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_ADDED, "नया एप्लिकेशन कॉन्फ़िगरेशन जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG, "एप्लिकेशन कॉन्फ़िगर जोड़ने में असमर्थ !");
            responseMsgsMap.put(RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG, "सक्रिय एप्लिकेशन कॉन्फ़िगर करने में असमर्थ !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED, "उपयोगकर्ता और एप्लिकेशन कॉन्फ़िगरेशन विवरण सफलतापूर्वक प्राप्त हुए !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES, "इनपुट विवरण या स्थान श्रेणी के नाम गायब हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY, "कृपया इनपुट विवरण प्रदान करें !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY, "कृपया श्रेणी के नाम प्रदान करें & मुख्य और उप श्रेणियों के लिए अंग्रेजी में नाम अनिवार्य है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED, "स्थानों के लिए नई श्रेणी जोड़ी गई है ।");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_PLACECATEGORIES, "स्थानों के लिए कोई श्रेणियां नहीं मिलीं !");
            responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS, "जगह के लिए श्रेणी पहले से मौजूद है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID, "दिए गए आईडी के साथ कोई सक्रिय स्थान श्रेणी नहीं है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED, "नई उप श्रेणी को जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES, "इनपुट विवरण या उत्पाद नाम गायब हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY, "कृपया उत्पाद नाम प्रदान करें !");
            responseMsgsMap.put(RESPONSEMESSAGE_CATEGORY_IDS_MISSING, "मुख्य स्थान श्रेणी या उप स्थान श्रेणी आईडी गायब हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PRODUCT_ADDED, "नया उत्पाद जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_SOMETHING_WENT_WRONG, "कुछ गलत हुआ, कृपया कुछ समय बाद फिर से प्रयास करें !");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS, "उत्पाद पहले से मौजूद है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID, "दिए गए उप श्रेणी आईडी के लिए कोई सक्रिय उत्पाद नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE, "आपका जगह को सफलतापूर्वक जोड़ा गया है, आपके लोग इसे स्थानों के अनुभाग में देख सकते हैं .");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING, "आपका जगह को सफलतापूर्वक जोड़ दिया गया है, जब हम इसे अनुमोदित कर देंगे तो आपके लोग इसे स्थानों के अनुभाग में देख सकते हैं।");
            responseMsgsMap.put(RESPONSEMESSAGE_CONGRATULATIONS, "बधाई हो ");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED, "नई रेटिंग और टिप्पणी जोड़ी गई है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_POST_ADDED, "नई पोस्ट सफलतापूर्वक जोड़ दी गई है !");
            responseMsgsMap.put(RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED, "नई आवश्यकता को सफलतापूर्वक जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_IMAGE_TYPE_MISSING, "छवि प्रकार गायब है !");
            responseMsgsMap.put(RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE, "कृपया अपना छवि प्रकार जांचें, छवि प्रकार के लिए कोई मिलान नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_IMAGES_ADDED, "स्थान चित्र सफलतापूर्वक जोड़े गए हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_POST_IMAGES_ADDED, "पोस्ट छवियों को सफलतापूर्वक जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_REQ_IMAGES_ADDED, "आवश्यकता छवियों को सफलतापूर्वक जोड़ा गया है !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES, "स्थान चित्र जोड़ने में असमर्थ !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES, "पोस्ट छवियों को जोड़ने में असमर्थ !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES, "आवश्यकता छवियों को जोड़ने में असमर्थ !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_ID_IS_MISSING, "उपयोगकर्ता आईडी गायब है !");
            responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_IS_EMPTY, "मोबाइल नंबर खाली नहीं होना चाहिए !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE, "आप पहले से ही हमारे साथ पंजीकृत हैं, कृपया एप्लिकेशन का उपयोग करने के लिए अपना पासवर्ड दर्ज करें !");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE, "ऐसा लगता है कि आप हमारे अॅप में नए हैं, कृपया आगे बढ़ें और अपना प्रोफ़ाइल बनाएं !");
            responseMsgsMap.put(RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY, "स्थान श्रेणी का प्रकार गायब है !");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY, "स्थान जोड़ने में असमर्थ, कृपया फिर से उपश्रेणी चुनें !");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_24INTO7, "खुला);24 * 7 के लिए ");
            responseMsgsMap.put(RESPONSEMESSAGE_CLOSED_OPENS_AT, "बन्द);पर खुलता है ");
            responseMsgsMap.put(RESPONSEMESSAGE_LUNCH_HOURS, "खुला);दोपहर के खाने पर");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_LUNCH_HOURS, "खुला);दोपहर का भोजन नहीं");
            responseMsgsMap.put(RESPONSEMESSAGE_OPEN_CLOSES_AT, "खुला);बंद किया जाएगा");
            responseMsgsMap.put(RESPONSEMESSAGE_UNKNOWN, "अनजान");
            responseMsgsMap.put(RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID, "उपयोगकर्ता नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_INVALID_POSTTYPE, "अमान्य पोस्ट प्रकार !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID, "जगह नहीं मिली !");
            responseMsgsMap.put(RESPONSEMESSAGE_OFFER_MSG, "प्रस्ताव की वैधता ");
            responseMsgsMap.put(RESPONSEMESSAGE_AUTHENTICATION_REQD, "प्रमाणीकरण आवश्यक !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND, "एप्लिकेशन कॉन्फ़िगरेशन नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_APP_CONFIG_FOUND, "ऐप कॉन्फ़िगर सक्रिय स्थिति के साथ मिला  ।");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_MOBILE, "मोबाइल खाली नहीं होना चाहिए !");
            responseMsgsMap.put(RESPONSEMESSAGE_MOBILE_ALREADY_USED, "मोबाइल नंबर पहले से उपयोग किया गया !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMAIL_ALREADY_USED, "ईमेल पहले से उपयोग किया गया !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY, "स्थान श्रेणी का प्रकार खाली नहीं होना चाहिए !");
            responseMsgsMap.put(RESPONSEMESSAGE_EMPTY_DEFAULTNAME, "डिफ़ॉल्ट नाम खाली नहीं होना चाहिए !");
            responseMsgsMap.put(RESPONSEMESSAGE_MAINCATEGORYID_MISSING, "ममुख्य श्रेणी आईडी खाली नहीं होनी चाहिए !");
            responseMsgsMap.put(RESPONSEMESSAGE_MISSING_QUERY_PARAMS, "क्वेरी पैरामीटर गायब हैं !");
            responseMsgsMap.put(RESPONSEMESSAGE_NOT_FOUND_ANY_PRODUCTS_WITH_SEARCH_KEY, "दिए गए खोज कीवर्ड के साथ मेल खाते कोई सक्रिय उत्पाद नहीं मिले !");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_STICKERS_FOUND, "कोई स्टिकर नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_ID_IS_MISSING, "जगह आईडी गायब है !");
            responseMsgsMap.put(RESPONSEMESSAGE_TICKET_RAISED, "टिकट सफलतापूर्वक जोड़ा गया ।");
            responseMsgsMap.put(RESPONSEMESSAGE_NO_TICKETS_FOUND, "कोई टिकट नहीं मिला !");
            responseMsgsMap.put(RESPONSEMESSAGE_PLACE_DETAILS_UPDATED, "स्थान का विवरण सफलतापूर्वक अपडेट किया गया है।");
            responseMsgsMap.put(RESPONSEMESSAGE_PRODUCT_DETAILS_UPDATED, "Product details updated successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_PROUDCT_IMAGES_ADDED, "Product images uploaded successfully .");
            responseMsgsMap.put(RESPONSEMESSAGE_UNABLE_TO_ADD_PRODUCT_IMAGES, "Unable to upload Product images !");
            responseMsgsMap.put(RESPONSEMESSAGE_NATURE_OF_BUSINESS_UPDATED, "Nature of business has been updated successfully.");
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
