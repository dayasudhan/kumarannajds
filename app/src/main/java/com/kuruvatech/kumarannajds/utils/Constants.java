package com.kuruvatech.kumarannajds.utils;


public class Constants {

    public static final String LOCALHOST = "http://192.168.1.104:3000";

    public static final String RELEASE_URL = "http://chunavane.herokuapp.com";
    public static final String DEBUG_URL = "http://chunavane.herokuapp.com";

    public static final String MAIN_URL = DEBUG_URL;
    public static final String USERNAME = "hdk";
    public static final String PARTY = "jds";
    public static final String GET_FEEDS_URL = MAIN_URL + "/v2/feed/info/";
    public static final String GET_IMAGES_URL = MAIN_URL + "/v1/feed/images/";
    public static final String GET_VIDEOS_URL = MAIN_URL + "/v1/feed/videos/";
    public static final String GET_MANIFESTO_URL = MAIN_URL + "/v1/feed/manifesto/" + PARTY;
    public static final String POST_LETTER_URL = MAIN_URL + "/v1/candidate/suggestion/" +USERNAME;


    public static final String FIREBASE_APP = "https://project-8598805513533999178.firebaseio.com";
    //To store the firebase id in shared preferences
    public static final String UNIQUE_ID = "uniqueid";
    public static final String INVITE_TEXT = "Invite JDS workers, fans, to this app" +

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
//    public static final String SLIDER_URL1= "https://chunavane.s3.amazonaws.com/hdk/main1511347643328.jpg";
//    public static final String SLIDER_URL3="https://chunavane.s3.ap-south-1.amazonaws.com/hdk/main1511185615407.jpg";
//    public static final String SLIDER_URL2="https://gallery.oneindia.com/ph-big/2013/04/hd-kumaraswamy_136490242813.jpg";
//    public static final String SLIDER_URL4="https://s3.ap-south-1.amazonaws.com/chunavane/hdk/images.jpg";
//    public static final String SLIDER_URL5="https://s3.ap-south-1.amazonaws.com/chunavane/hdk/hqdefault.jpg";
//    public static final String SLIDER_URL6="https://s3.ap-south-1.amazonaws.com/chunavane/hdk/19665643_350745285342945_1332574716575418921_n.jpg";

}
