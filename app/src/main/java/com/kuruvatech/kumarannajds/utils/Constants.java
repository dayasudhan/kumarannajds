package com.kuruvatech.kumarannajds.utils;


public class Constants {

    public static final String LOCALHOST = "http://192.168.1.104:3000";

    public static final String RELEASE_URL = "http://chunavane.herokuapp.com";
    public static final String DEBUG_URL = "http://chunavane.herokuapp.com";

    public static final String MAIN_URL = DEBUG_URL;

    public static final String GET_FEEDS_URL = MAIN_URL + "/v1/feed/info/";
    public static final String GET_IMAGES_URL = MAIN_URL + "/v1/feed/images/";
    public static final String GET_VIDEOS_URL = MAIN_URL + "/v1/feed/videos/";

    public static final String FIREBASE_APP = "https://project-8598805513533999178.firebaseio.com";
    //To store the firebase id in shared preferences
    public static final String UNIQUE_ID = "uniqueid";
    public static final String INVITE_TEXT = "Karnataka's local food speciality at your door step\n" +
            " \n" +
            " Joladha Rotti, Akki rotti, Jawari rotti and Native Kannada food\n" +
            " \n" +
            " To order online visit: http://Khaanavali.com\n" +
            "\t\n" +
            " Download Android App: https://play.google.com/store/apps/details?id=khaanavali.customer";
    public static final String INVITE_SUBJECT = "Khaanavali ( ಖಾನಾವಳಿ) the real taste of Karnataka";


    public static final String SECUREKEY_KEY = "securekey";
    public static final String VERSION_KEY = "version";
    public static final String CLIENT_KEY = "client";

    public static final String SECUREKEY_VALUE = "EjR7tUPWx7WhsVs9FuVO6veFxFISIgIxhFZh6dM66rs";
    public static final String VERSION_VALUE = "1";
    public static final String CLIENT_VALUE = "bhoomika";
	

    public static final int TITLE_TEXT_COLOR_RED = 00;
    public static final int TITLE_TEXT_COLOR_GREEN = 177;
    public static final int TITLE_TEXT_COLOR_BLUE = 106;

}
