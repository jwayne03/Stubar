package com.example.stubar.utils.constants;

import com.example.stubar.model.user.User;

public class Constants {
    /**
     * STATIC METHOD
     */
    public static User USER_LOGGED = null;
    /**
     * STATIC METHOD TO CALL THE USER OFFER
     */
    public static String USER_OFFERS_URL = "http://46.101.46.166:8080/stuapi/api/user/offers/";
    /**
     * STATIC METHOD TO ALL OFFERS
     */
    public static String ALL_OFFERS_URL = "http://46.101.46.166:8080/stuapi/api/offer";
    /**
     * STATIC METHOD TO CALL THE USER DOCUMENTS
     */
    public static String USER_DOCUMENTS_URL = "http://46.101.46.166:8080/stuapi/api/user/documents/";
    /**
     * STATIC METHOD TO FIND DOCUMENTS
     */
    public static String DOCUMENTS_URL = "http://46.101.46.166:8080/stuapi/api/document";
    /**
     * STATIC METHOD TO DOWNLOAD THE DOCUMENTS
     */
    public static String DOCUMENTS_DOWNLOAD_URL = "http://46.101.46.166:8080/stuapi/api/document/download/";
    /**
     * STATIC METHOD TO GET THE REGISTER
     */
    public static String REGISTER_URL = "http://46.101.46.166:8080/stuapi/api/user";
    /**
     * STATIC METHOD TO GET THE USER AUTHENTICATION
     */
    public static String AUTHENTICATION_USER_URL = "http://46.101.46.166:8080/stuapi/api/user/authentication";
    /**
     * STATIC METHOD TO GET THE USER
     */
    public static String USER_URL = "http://46.101.46.166:8080/stuapi/api/user/";
    /**
     * STATIC METHOD TO GET THE PROFILE PHOTO
     */
    public static String PROFILE_PHOTO_URL = "http://46.101.46.166:8080/stuapi/api/user/";
    /**
     * STATIC METHOD TO GET THE INSTITUTION
     */
    public static String INSTITUTION_URL = "http://46.101.46.166:8080/stuapi/api/institution";
    /**
     * STATIC METHOD TO GET THE LOCAL
     */
    public static String LOCAL_URL = "http://46.101.46.166:8080/stuapi/api/local/";
    /**
     * STATIC METHOD TO INSERT AN OFFER
     */
    public static String INSERT_OFFER = "http://46.101.46.166:8080/stuapi/api/offer";
    /**
     * STATIC METHOD TO INSERT A DOCUMENT
     */
    public static String INSERT_DOCUMENT = "http://46.101.46.166:8080/stuapi/api/document";
    /**
     * STATIC METHOD TO FIND A DOCUMENT
     */
    public static String SEARCH_DOCUMENT = "http://46.101.46.166:8080/stuapi/api/document/search?name=";
    /**
     * STATIC METHOD TO FIND AN OFFER
     */
    public static String SEARCH_OFFER = "http://46.101.46.166:8080/stuapi/api/offer/search?name=";
    /**
     * STATIC METHOD TO UPDATE THE PASSWORD OF THE USER
     */
    public static String UPDATE_USER = "http://46.101.46.166:8080/stuapi/api/user/modify";
    /**
     * STATIC METHOD TO GET ALL TOPICS
     */
    public static String GET_ALL_TOPICS = "http://46.101.46.166:8080/stuapi/api/topic";
}