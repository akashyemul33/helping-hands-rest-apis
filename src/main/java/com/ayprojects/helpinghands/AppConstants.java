package com.ayprojects.helpinghands;

public class AppConstants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    //expiration value of 3 hours
    public static final long JWT_TOKEN_EXPIRATION_VALUE = 1000*60*60*3;
    public static final String JWT_TOKEN_ISSUER = "HelpingHandsAdmin";
    public static final String JWT_TOKEN_AUDIENCE = "HelpingHandsMobileAndWebUsers";
    public static final String SECRET_KEY = "helping-hands-secret-key";
    public static final String[] ROLE_USER = {"USER"} ;
    public static final String STATUS_PENDING = "Pending";
    //public static final String EXCEPTION_OCCURRED_WHILE_ADDING_CONFIGURATION = "Exception occurred while adding configuration .";

    public static String[] LANGUAGES = { "en", "hi","mr" };
    public static String LABEL_HEADER_APPLANGUAGE ="App-Language";
    public static String STATUS_ACTIVE = "Active";
    public static String SCHEMA_VERSION = "1.0.0";
    public static String FOUND_APP_CONFIG = "Found app config with status Active";

    //actions
    public static final String ACTION_NEW_USER_ADDED = "New user has been added." ;
    public static final String ACTION_TRIED_LOGGING_WITH_INCORRECT_USERNAME = "Tried logging in with incorrect username !" ;
    public static final String ACTION_TRIED_LOGGING_WITH_INCORRECT_PASSWORD = "Tried logging in with incorrect password !" ;
    public static final String ACTION_NEW_APPCONFIG_ADDED = "New app configuration added .";
    public static final String ACTION_NEW_PLACE_CATEGORY_ADDED = "New main place category has been added .";


    public static String RESPONSEMESSAGE_USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static String RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY = "USER_CONTACT_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY = "USER_ADDRESS_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY = "USER_PASSWORD_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_REGISTERED = "USER_REGISTERED";
    public static String RESPONSEMESSAGE_INCORRECT_USERNAME = "INCORRECT_USERNAME";
    public static String RESPONSEMESSAGE_INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public static String RESPONSEMESSAGE_USER_LOGGED_IN = "USER_LOGGED_IN";
    public static String RESPONSEMESSAGE_APP_CONFIG_ADDED = "APP_CONFIG_ADDED";
    public static String RESPONSEMESSAGE_ERROR_WHILE_ADDING_APP_CONFIG = "ERROR_WHILE_ADDING_APP_CONFIG";
    public static String RESPONSEMESSAGE_ERROR_WHILE_FETCHING_APP_CONFIG = "ERROR_WHILE_FETCHING_APP_CONFIG";
    public static String RESPONSEMESSAGE_USER_AND_APPCONFIG_DETAILS_FETCHED = "USER_AND_APPCONFIG_DETAILS_FETCHED";
    public static String RESPONSEMESSAGE_EMPTY_BODY = "EMPTY_BODY";
    public static String RESPONSEMESSAGE_EMPTY_BODY_OR_PLACECATEGORYNAMES = "EMPTY_BODY_OR_PLACECATEGORYNAMES";
    public static String RESPONSEMESSAGE_PLACE_CATEGORY_NAMES_EMPTY = "PLACE_CATEGORY_NAMES_EMPTY";
    public static String RESPONSEMESSAGE_NEW_PLACE_CATEGORY_ADDED = "NEW_PLACE_CATEGORY_ADDED";
    public static String RESPONSEMESSAGE_NO_PLACECATEGORIES = "NO_PLACECATEGORIES";


}
