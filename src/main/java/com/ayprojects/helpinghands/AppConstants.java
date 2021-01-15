package com.ayprojects.helpinghands;

public class AppConstants {
    public static final String DATE_FORMAT_HOUR_MIN = "HH:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_WITHOUT_UNDERSCORE = "yyyyMMddHHmmss";
    //expiration value of 3 hours
    public static final long JWT_TOKEN_EXPIRATION_VALUE = 1000 * 60 * 60 * 3;
    public static final String JWT_TOKEN_ISSUER = "HelpingHandsAdmin";
    public static final String JWT_TOKEN_AUDIENCE = "HelpingHandsMobileAndWebUsers";
    public static final String JWT_SECRET_KEY = "helping-hands-secret-key";
    public static final String[] ROLE_USER = {"USER"};
    public static final String STATUS_PENDING = "Pending";
    public static final String MSG_SOMETHING_WENT_WRONG = "Something went wrong !";
    public static final String QUERY_SUCCESSFUL = "Query successful";
    public static final String RESPONSEMESSAGE_NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID = "NO_PRODUCTS_FOUND_FOR_SUBCATEGORYID";
    public static final String BUSINESS_POST = "BusinessPost";
    public static final String PUBLIC_POST = "PublicPost";
    public static final String PLACE = "Place";
    public static final String POST = "Post";
    public static final String REQUIREMENT = "Requirement";
    public static final String PLACE_ID = "placeId";
    public static final String NUMBER_OF_VIEWS = "numberOfViews";
    public static final String VIEW_IDS = "viewIds";
    public static final String TOP_VIEWS = "topViews";
    public static final String MODIFIED_DATE_TIME = "modifiedDateTime";
    public static final String POST_ID = "postId";
    public static final String REQUIREMENT_ID = "requirementId";
    public static final String CONTENT_ID = "contentId";
    public static final String CONTENT_TYPE = "contentType";
    public static final String STATUS = "status";
    public static final String NUMBER_OF_RATINGS = "numberOfRatings";
    public static final String NUMBER_OF_POSTS = "numberOfPosts";
    public static final String AVG_RATING = "avgRating";
    public static final String RATINGS_IDS = "ratingIds";
    public static final String TOP_RATINGS = "topRatings";
    public static final String TOP_POSTS = "topPosts";
    public static final String PLACE_MAIN_CATEGORY_ID = "placeCategoryId";
    public static final String PLACE_SUB_CATEGORIES = "placeSubCategories";
    public static final String POST_IDS = "postIds";
    public static final String POST_TYPE = "postType";
    public static final String SUB_PLACE_CATEGORY_ID = "subPlaceCategoryId";
    public static final String BUSINESS_PLACE = "BusinessPlace";
    public static final String REGEX_BUSINESS_POST = "^[Bb]usiness[\\s]*[Pp]ost$";
    public static final String REGEX_BUSINESS_REQUIREMENT = "^[Bb]usiness[\\s]*[Rr]equirement$";
    public static final String REGEX_PUBLIC_REQUIREMENT = "^[Pp]ublic[\\s]*[Rr]equirement$";
    public static final String REGEX_BUSINESS_PLACE = "^[Bb]usiness[\\s]*[Pp]lace$";
    public static final String REGEX_USER = "^[Uu]ser$";
    public static final String REGEX_PUBLIC_PLACE = "^[Pp]ublic[\\s]*[Pp]lace$";
    public static final String PUBLIC_PLACE = "PublicPlace";
    public static final String REGEX_PUBLIC_POST = "^[Pp]ublic[\\s]*[Pp]ost$";
    public static final int LIMIT_RATINGS_IN_PLACES = 5;
    public static final int LIMIT_RATINGS_IN_POSTS = 5;
    public static final int LIMIT_RATINGS_IN_REQUIREMENTS = 5;
    public static final int LIMIT_VIEWS_IN_PLACES = 10;
    public static final int LIMIT_VIEWS_IN_POSTS = 10;
    public static final int LIMIT_VIEWS_IN_REQUIREMENTS = 10;
    public static final int LIMIT_POSTS_IN_PLACES = 3;
    public static final String PUBLIC_REQUIREMENT = "PublicRequirement";
    public static final String ADDED_BY = "addedBy";
    public static final String PLACE_SUB_CATEGORY_ID = "placeSubCategoryId";
    public static final String PLACE_NAME = "placeName";
    public static final String PLACE_TYPE = "placeType";
    public static final String PLACE_CATEGORY_NAME = "placeCategoryName";
    public static final String PLACE_ADDRESS = "placeAddress";
    public static final String LATTITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String FULL_ADDRESS = "fullAddress";
    public static final String PLACE_CONTACT = "placeContact";
    public static final String KEY_MOBILE = "mobileNumber";
    public static final String EMAIL = "emailId";
    public static final String PLACE_OPENING_TIME = "placeOpeningTime";
    public static final String PLACE_CLOSING_TIME = "placeClosingTime";
    public static final String LUNCH_START_TIME = "lunchStartTime";
    public static final String LUNCH_END_TIME = "lunchEndTime";
    public static final String EXCHANGE_START_TIME = "exchangeStartTime";
    public static final String EXCHANGE_END_TIME = "exchangeEndTime";
    public static final String PLACE_AVAILABLITY_DETAILS = "placeAvailablityDetails";
    public static final String POST_TITLE = "postTitle";
    public static final String ADDRESS_DETAILS = "addressDetails";
    public static final String MAIN_PLACE_INITIAL_ID = "M_PLS_CTGRY_";
    public static final String SUB_PLACE_INITIAL_ID = "S_PLS_CTGRY_";
    public static final String OWNER_DETAILS = "ownerDetails";
    public static final String OWNER_NAME = "ownerName";
    public static final String OWNER_MOBILE_NUMBER = "ownerMobileNumber";
    public static final String OWNER_EMAIL_ID = "ownerEmailId";
    public static final String RESPONSEMESSAGE_PLACE_IMAGES_ADDED = "PLACE_IMAGES_ADDED";
    public static final String RESPONSEMESSAGE_POST_IMAGES_ADDED = "POST_IMAGES_ADDED";
    public static final String RESPONSEMESSAGE_REQ_IMAGES_ADDED = "REQ_IMAGES_ADDED";
    public static final String RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_IMAGES = "UNABLE_TO_ADD_PLACE_IMAGES";
    public static final String RESPONSEMESSAGE_UNABLE_TO_ADD_POST_IMAGES = "UNABLE_TO_ADD_POST_IMAGES";
    public static final String RESPONSEMESSAGE_UNABLE_TO_ADD_REQ_IMAGES = "UNABLE_TO_ADD_REQ_IMAGES";
    public static final String REQ_TITLE = "requirementTitle";
    public static final String REQ_DESC = "requirementDesc";
    public static final String RATING = "rating";
    public static final String CURRENT_API_VERSION = "1";
    public static final String RESPONSEMESSAGE_MOBILE_IS_EMPTY = "MOBILE_IS_EMPTY";
    public static final String RESPONSEMESSAGE_USER_FOUND_WITH_MOBILE = "USER_FOUND_WITH_MOBILE";
    public static final String RESPONSEMESSAGE_USER_NOT_FOUND_WITH_MOBILE = "USER_NOT_FOUND_WITH_MOBILE";
    public static final String RESPONSEMESSAGE_USER_NOT_FOUND_WITH_USERID = "USER_NOT_FOUND_WITH_USERID";
    public static final String RESPONSEMESSAGE_PLACE_NOT_FOUND_WITH_PLACEID = "PLACE_NOT_FOUND_WITH_PLACEID";
    public static final String TYPE_OF_PLACECATEGORY = "typeOfPlaceCategory";
    public static final String LANG_ENGLISH = "en";
    public static final String LANG_MARATHI = "mr";
    public static final String LANG_HINDI = "hi";
    public static final String PLACE_SUB_CATEGORY_NAME = "placeSubCategoryName";
    public static final String PRODUCT_DETAILS = "productDetails";
    public static final String KEY_PRODUCTNAME_ENG = "productnameInEnglish";
    public static final String KEY_PRODUCTNAME_MR = "productnameInMarathi";
    public static final String KEY_PRODUCTNAME_HI = "productnameInHindi";
    public static final String TYPE_OF_PLACE_CATEGORY = "typeOfPlaceCategory";
    public static final String DEFAULT_NAME = "defaultName";
    public static final String TRANSLATIONS = "translations";
    public static final String RESPONSEMESSAGE_UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY = "UNABLE_TO_ADD_PLACE_ISSUES_WITH_SUBCATEGORY";
    public static final String PLACE_DESC = "placeDesc";
    public static final String PLACE_MOBILE = "placeContact.mobile";
    public static final String NUMBER_OF_PRODUCTS = "numberOfProducts";
    public static final String DOOR_SERVICE = "doorService";
    public static final String PLACE_IMAGES = "placeImages";
    public static final String CREATED_DATETIME = "createdDateTime";
    public static final String IA_ADDRESS_GENERATED = "isAddressGenerated";
    public static final String USER_ID = "userId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String RESPONSEMESSAGE_OPEN_24INTO7 = "OPEN_24INTO7";
    public static final String RESPONSEMESSAGE_CLOSED_OPENS_AT = "CLOSED_OPENS_AT";
    public static final String RESPONSEMESSAGE_LUNCH_HOURS = "LUNCH_HOURS";
    public static final String RESPONSEMESSAGE_NO_LUNCH_HOURS = "NO_LUNCH_HOURS";
    public static final String RESPONSEMESSAGE_OPEN_CLOSES_AT = "OPEN_CLOSES_AT";
    public static final long HOUR_IN_MILLIS = 1 * 60 * 60 * 1000;
    public static final String CLOSED = "Closed";
    public static final String OPEN = "Open";
    public static final String RESPONSEMESSAGE_UNKNOWN = "UNKNOWN";
    public static final String USER_INITIAL = "USER_";
    public static final String USER_DIR = "/user/";
    public static final String PLACE_INITIAL = "_PLCS_";
    public static final String PLACE_DIR = "/places/";
    public static final String POST_INITIAL = "_PSTS_";
    public static final String POST_DIR = "/posts/";
    public static final String REQUIREMENT_DIR = "/requirements/";
    public static final String REQUIREMENT_INITIAL = "_RQMNTS_";
    public static final String POST_DESC = "postDesc";
    public static final String POST_OFFER_END_TIME = "offerEndTime";
    public static final String POST_OFFER_START_TIME = "offerStartTime";
    public static final String OBJECT_ID = "_id";
    public static final String LANDMARK = "landmark";
    public static final String USER_PROFILE_IMG = "profileImg";
    public static final String RESPONSEMESSAGE_OFFER_MSG = "OFFER_MSG";
    public static final String RESPONSEMESSAGE_EMPTY_MOBILE = "EMPTY_MOBILE";
    public static final String RESPONSEMESSAGE_EMPTY_EMAIL = "EMPTY_EMAIL";
    public static final String RESPONSEMESSAGE_MOBILE_ALREADY_USED = "MOBILE_ALREADY_USED";
    public static final String RESPONSEMESSAGE_EMAIL_ALREADY_USED = "EMAIL_ALREADY_USED";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String LOGIN_TIME = "logInTime";
    public static final String TRIED_TO_LOGIN_TIME = "triedToLoginTime";
    public static final String LAST_LOGIN_TIME = "lastLogInTime";
    public static final String KEY_LAST_LOGOUT_TIME = "lastLogOutTime";
    public static final String STATUS_NOTREGISTERED = "NotRegistered";
    public static final String STATUS_REGISTERED = "Registered";
    public static final String UTC = "UTC";
    public static final String KEY_COUNTRY_CODE = "countryCode";
    public static final String KEY_SCHEMA_VERSION = "schemaVersion";
    public static final String COLLECTION_DHUSER = "DhUser";
    public static final String COLLECTION_DH_PLACE_CATEGORIES = "dhPlaceCategories";
    public static final String COLLECTION_DH_PRODUCT = "dhProduct";
    public static final String COLLECTION_DH_PLACE = "dhPlace";
    public static final String COLLECTION_DH_POSTS = "dhPosts";
    public static final String COLLECTION_DH_REQUIREMENTS = "dhRequirements";
    public static final String COLLECTION_DH_RATING_COMMENT = "dhRatingAndComments";
    public static final String COLLECTION_DH_VIEWS = "dhViews";
    public static final String COLLECTION_DHLOG = "dhLog";
    public static final String RESPONSEMESSAGE_EMPTY_TYPEOFMAINPLACECATEGORY = "EMPTY_TYPEOFMAINPLACECATEGORY";
    public static final String RESPONSEMESSAGE_EMPTY_DEFAULTNAME = "EMPTY_DEFAULTNAME";
    public static final String RESPONSEMESSAGE_MAINCATEGORYID_MISSING = "MAINCATEGORYID_MISSING";
    public static final String RESPONSEMESSAGE_SUBCATEGORYID_MISSING = "SUBCATEGORYID_MISSING";
    public static final String STATUS_PENDING_NEW_SUBCATEGORY = "Pending(New Subcategory)";
    public static final String KEY_PAGE = "page";
    public static final String KEY_SIZE = "size";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";
    public static final String RESPONSEMESSAGE_MISSING_QUERY_PARAMS = "MISSING_QUERY_PARAMS";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_TYPE_OF_PLACECATEGORY = "typeOfPlaceCategory";
    public static final String KEY_PLACE_ID = "placeId";
    public static final String KEY_SUB_PLACECATEGORY_ID = "subPlaceCategoryId";
    public static final String KEY_CONTENT_ID = "contentId";
    public static final String KEY_CONTENT_TYPE = "contentType";
    public static final String KEY_AUTHENTICATION = "authentication";
    public static final String KEY_NEW_FCM_TOKEN = "newFcmToken";
    public static final String FILETYPE_WEBP = "webp";
    //actions
    public static final String ACTION_NEW_USER_ADDED = "New user has been added.";
    public static final String ACTION_TRIED_LOGGING_WITH_INCORRECT_USERNAME = "Tried logging in with incorrect username !";
    //public static final String EXCEPTION_OCCURRED_WHILE_ADDING_CONFIGURATION = "Exception occurred while adding configuration .";
    public static final String ACTION_TRIED_LOGGING_WITH_INCORRECT_PASSWORD = "Tried logging in with incorrect password !";
    public static final String ACTION_NEW_APPCONFIG_ADDED = "New app configuration added .";
    public static final String ACTION_NEW_PLACE_CATEGORY_ADDED = "New main place category has been added .";
    public static String ACCESS_KEY = "";
    public static String SECRET_KEY = "";
    public static String[] SUPPORTED_LANGUAGES = {"en", "hi", "mr"};
    public static String LABEL_HEADER_APPLANGUAGE = "App-Language";
    public static String STATUS_ACTIVE = "Active";
    public static String SCHEMA_VERSION = "1.0.0";
    public static String RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_MOBILE = "USER_ALREADY_EXISTS_WITH_MOBILE";
    public static String RESPONSEMESSAGE_USER_ALREADY_EXISTS_WITH_EMAIL = "USER_ALREADY_EXISTS_WITH_EMAIL";
    public static String RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY = "USER_CONTACT_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_ID_IS_MISSING = "USER_ID_IS_MISSING";
    public static String RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY = "USER_ADDRESS_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY = "USER_PASSWORD_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_REGISTERED = "USER_REGISTERED";
    public static String RESPONSEMESSAGE_INCORRECT_USERNAME = "INCORRECT_USERNAME";
    public static String RESPONSEMESSAGE_NOT_ACTIVE_USER = "NOT_ACTIVE_USER";
    public static String RESPONSEMESSAGE_INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public static String RESPONSEMESSAGE_USER_LOGGED_IN = "USER_LOGGED_IN";
    public static String RESPONSEMESSAGE_APP_CONFIG_ADDED = "APP_CONFIG_ADDED";
    public static String RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG = "ERROR_WHILE_ADDING_APP_CONFIG";
    public static String RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG = "ERROR_WHILE_FETCHING_APP_CONFIG";
    public static String RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED = "USER_AND_APPCONFIG_DETAILS_FETCHED";
    public static String RESPONSEMESSAGE_EMPTY_BODY = "EMPTY_BODY";
    public static String RESPONSEMESSAGE_INVALID_POSTTYPE = "INVALID_POSTTYPE";
    public static String RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES = "EMPTY_BODY_OR_PLACECATEGORYNAMES";
    public static String RESPONSEMESSAGE_TYPE_PLACECATEGORY_EMPTY = "TYPE_PLACECATEGORY_EMPTY";
    public static String RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY = "PLACE_CATEGORY_NAMES_EMPTY";
    public static String RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED = "NEW_PLACE_CATEGORY_ADDED";
    public static String RESPONSEMESSAGE_NO_PLACECATEGORIES = "NO_PLACECATEGORIES";
    public static String RESPONSEMESSAGE_CATEGORY_ALREADY_EXISTS = "CATEGORY_ALREADY_EXISTS";
    public static String RESPONSEMESSAGE_NOT_FOUND_PLACECATEGORIY_WITH_ID = "NOT_FOUND_PLACECATEGORIY_WITH_ID";
    public static String RESPONSEMESSAGE_NEW_PLACESUBCATEGORY_ADDED = "NEW_PLACESUBCATEGORY_ADDED";
    public static String RESPONSEMESSAGE_EMPTY_BODY_OR_PRODUCTNAMES = "EMPTY_BODY_OR_PRODUCTNAMES";
    public static String RESPONSEMESSAGE_PRODUCT_NAMES_EMPTY = "PRODUCT_NAMES_EMPTY";
    public static String RESPONSEMESSAGE_CATEGORY_IDS_MISSING = "CATEGORY_IDS_MISSING";
    public static String RESPONSEMESSAGE_NO_CATEGORY_FOUND_WITH_ID = "CATEGORY_IDS_MISSING";
    public static String RESPONSEMESSAGE_NEW_PRODUCT_ADDED = "NEW_PRODUCT_ADDED";
    public static String RESPONSEMESSAGE_SOMETHING_WENT_WRONG = "SOMETHING_WENT_WRONG";
    public static String RESPONSEMESSAGE_AUTHENTICATION_REQD = "AUTHENTICATION_REQD";
    public static String RESPONSEMESSAGE_APP_CONFIG_NOT_FOUND = "APP_CONFIG_NOT_FOUND";
    public static String RESPONSEMESSAGE_APP_CONFIG_FOUND = "APP_CONFIG_FOUND";
    public static String RESPONSEMESSAGE_PRODUCT_ALREADY_EXISTS = "PRODUCT_ALREADY_EXISTS";
    public static String RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_ACTIVE = "NEW_PLACE_ADDED_WITH_ACTIVE";
    public static String RESPONSEMESSAGE_NEW_PLACE_ADDED_WITH_PENDING = "NEW_PLACE_ADDED_WITH_PENDING";
    public static String RESPONSEMESSAGE_CONGRATULATIONS = "CONGRATULATIONS";
    public static String RESPONSEMESSAGE_NEW_POST_ADDED = "NEW_POST_ADDED";
    public static String RESPONSEMESSAGE_NEW_REQUIREMENT_ADDED = "NEW_REQUIREMENT_ADDED";
    public static String RESPONSEMESSAGE_IMAGE_TYPE_MISSING = "IMAGE_TYPE_MISSING";
    public static String RESPONSEMESSAGE_INCORRECT_IMAGE_TYPE = "INCORRECT_IMAGE_TYPE";
    public static String RESPONSEMESSAGE_NEW_RATING_COMMENT_ADDED = "NEW_RATING_COMMENT_ADDED";

    public static String CONTACT_DETAILS = "contactDetails";
    public static long TOTAL_ADD_PLACES_LIMIT = 100;
    public static long PER_DAY_ADD_PLACES_LIMIT = 10;
    public static long PER_DAY_ADD_POSTS_LIMIT = 200;
    public static long PER_PLACE_IMAGES_LIMIT = 16;
    public static long PER_POST_IMAGES_LIMIT = 8;
    public static long PER_PLACE_PRODUCTS_LIMIT = 200;

    public static String MSG_DISTANCE_CALC_FAILED = "DISTANCE_CALC_FAILED";
}
