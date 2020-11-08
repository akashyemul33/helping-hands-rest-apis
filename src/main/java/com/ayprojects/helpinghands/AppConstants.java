package com.ayprojects.helpinghands;

public class AppConstants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss z";
    //expiration value of 3 hours
    public static final long JWT_TOKEN_EXPIRATION_VALUE = 1000*60*60*3;
    public static final String JWT_TOKEN_ISSUER = "HelpingHandsAdmin";
    public static final String JWT_TOKEN_AUDIENCE = "HelpingHandsMobileAndWebUsers";
    public static final String SECRET_KEY = "helping-hands-secret-key";
    public static final String NEW_USER_ADDED = "New user has been added." ;
    public static final String TRIED_LOGGING_WITH_INCORRECT_USERNAME = "Tried logging in with incorrect username !" ;
    public static final String TRIED_LOGGING_WITH_INCORRECT_PASSWORD = "Tried logging in with incorrect password !" ;

    public static String[] LANGUAGES = { "en", "hi","mr" };
    public static String LABEL_HEADER_APPLANGUAGE ="App-Language";
    public static String STATUS_ACTIVE = "Active";
    public static String SCHEMA_VERSION = "1.0.0";
    public static String RESPONSEMESSAGE_USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";
    public static String RESPONSEMESSAGE_USER_CONTACT_IS_EMPTY = "USER_CONTACT_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_ADDRESS_IS_EMPTY = "USER_ADDRESS_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_PASSWORD_IS_EMPTY = "USER_PASSWORD_IS_EMPTY";
    public static String RESPONSEMESSAGE_USER_REGISTERED = "USER_REGISTERED";
    public static String RESPONSEMESSAGE_INCORRECT_USERNAME = "INCORRECT_USERNAME";
    public static String RESPONSEMESSAGE_INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public static String RESPONSEMESSAGE_USER_LOGGED_IN = "USER_LOGGED_IN";
}
