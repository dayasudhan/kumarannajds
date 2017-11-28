package com.kuruvatech.kumarannajds.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kuruvatech.kumarannajds.model.FeedItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	//ArrayList<FeedItem> feedList;
	// Sharedpref file name
	public static final String PREF_NAME = "ElectionCandidate";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	public static final String KEY_PHONE = "phone";
	public static final String KEY_AREANAME = "areaname";
	public static final String KEY_LANDMARK = "landmark	";
	public static final String KEY_ADDRESSLINE1 = "addressline1";
	public static final String KEY_ADDRESSLINE2 = "addressline2";
	public static final String KEY_CITY = "city";

	public static final String KEY_FAVOURITE_ADDRESS = "favourite_address";
	//To store the firebase id in shared preferences
	public static final String KEY_ORDER_ID = "orderid";

	//To store the firebase id in shared preferences
	public static final String KEY_ORDER_ID_LIST = "orderidlist";

	//To store the firebase id in shared preferences
	public static final String KEY_LAST_AREA_SERCHED = "lastareasearched";

	public static final String KEY_SLIDER_LOGO = "slider";
	public static final String KEY_LAST_PN = "last_pn";

	public static final String KEY_FEED_NEWS_HEADING = "NEWS_FEED_HEADING";
	public static final String KEY_FEED_NEWS_DESCRIPTION = "NEWS_FEED_DESCRIPTION";
	public static final String KEY_FEED_NEWS_IMAGES = "NEWS_FEED_IMAGES";
	public static final String KEY_FEED_NEWS_VIDEO = "NEWS_FEED_VIDEO";

	// Constructor
	public  boolean hasAddress=false;

	public boolean isHasAddress() {
		return hasAddress;
	}
	public void setHasAddress(Boolean hasAddress){
		this.hasAddress=hasAddress;
	}

	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	public List<String> getSlider()
	{
		String orders = pref.getString(KEY_SLIDER_LOGO, null);
		List<String> list = null;
		if(orders != null) {
			Gson gson = new Gson();
			list = (List<String>) gson.fromJson(orders, Object.class);
		}
		else
		{
			list = new ArrayList<String>();
			list.add(Constants.SLIDER_URL1);
			list.add(Constants.SLIDER_URL2);
			list.add(Constants.SLIDER_URL3);
			list.add(Constants.SLIDER_URL4);
		}
		return list;
	}
	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String phone, String email){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_PHONE,phone);
//		// Storing name in pref
		editor.putString(KEY_NAME, name);
//
//		// Storing email in pref
		editor.putString(KEY_EMAIL, email);


//
//		//Storing the unique id
//		editor.putString(Constants.UNIQUE_ID, uniqueId);
		// commit changes
		editor.commit();

	}
	public void setlastareasearched(String orderId)
	{
		editor.putString(KEY_LAST_AREA_SERCHED,orderId);
		editor.commit();
	}
	public String getlastareasearched()
	{
		String id = pref.getString(KEY_LAST_AREA_SERCHED, "");
		return id;
	}
	public void setlastpn(String msg)
	{
		editor.putString(KEY_LAST_PN,msg);
		editor.commit();
	}
	public String getlastpn()
	{
		String id = pref.getString(KEY_LAST_PN, "");
		return id;
	}
	public void setKeyPhone(String orderId)
	{
		editor.putString(KEY_PHONE,orderId);
		editor.commit();
	}
	public String getKeyPhone()
	{
		String id = pref.getString(KEY_PHONE, null);
		return id;
	}
//gagan
	public void setEmail(String orderId)
	{
		editor.putString(KEY_EMAIL,orderId);
		editor.commit();
	}

	public String getEmail()
	{
		String id = pref.getString(KEY_EMAIL, null);
		return id;
	}

	public void setAddress(String areaname, String landmark, String addressline1 , String addressline2, String city)
	{
		editor.putString(KEY_AREANAME,areaname);
		editor.putString(KEY_LANDMARK,landmark);
		editor.putString(KEY_ADDRESSLINE1,addressline1);
		editor.putString(KEY_ADDRESSLINE2,addressline2);
		editor.putString(KEY_CITY,city);
		editor.commit();
	}

	public void setCurrentOrderId(String orderId)
	{
		editor.putString(KEY_ORDER_ID,orderId);
		editor.commit();
		addOrderId(orderId);
	}
	public String getCurrentOrderId()
	{
		String id = pref.getString(KEY_ORDER_ID, null);
		return id;

	}
	public void setOrderIdList(List<String> list)
	{
		Gson gson = new Gson();
		String json = gson.toJson(list);
		editor.putString(KEY_ORDER_ID_LIST,json);
		editor.commit();
	}

	public void addOrderId(String orderid)
	{
		String orders = pref.getString(KEY_ORDER_ID_LIST, null);

		Gson gson = new Gson();
		List<String> list = null;
		if(orders != null) {
			list = (List<String>) gson.fromJson(orders, Object.class);
		}
		else
		{
			list = new ArrayList<String>();
		}

		list.add(0,orderid);
		if(list.size()> 10)
		{
			list.remove(10);
		}
		setOrderIdList(list);
	}
	public List<String> getOrderIdList()
	{
		String orders = pref.getString(KEY_ORDER_ID_LIST, null);
		List<String> list = null;
		if(orders != null) {
			Gson gson = new Gson();
			list = (List<String>) gson.fromJson(orders, Object.class);
		}
		return list;
	}
	public void setSlider(List<String> list)
	{
		Gson gson = new Gson();
		String json = gson.toJson(list);
		editor.putString(KEY_SLIDER_LOGO,json);
		editor.commit();
	}


	public void commiting(){
		editor.commit();
	}



	public void clearAddress()
	{
		editor.putString(KEY_FAVOURITE_ADDRESS, null);
		editor.commit();
	}

	public void setLastNewsFeed(ArrayList<FeedItem> feedList)
	{
		Gson gson = new Gson();
		//String json = gson.toJson(news);
		List<String> heading =  new ArrayList<String>();
		List<String> description =  new ArrayList<String>();
		List<String> images =  new ArrayList<String>();
		List<String> video =  new ArrayList<String>();
		for(int i = 0 ; i < feedList.size();i++) {
			heading.add(feedList.get(i).getHeading());
			description.add(feedList.get(i).getDescription());
			if(feedList.get(i).getFeedimages().size()> 0)
				images.add(feedList.get(i).getFeedimages().get(0));
			video.add(feedList.get(i).getVideoid());
		}
		String jsonHEADING = gson.toJson(heading);
		String jsonDESCRIPTION = gson.toJson(description);
		String jsonIMAGES = gson.toJson(images);
		String jsonVIDEO = gson.toJson(video);
		editor.putString(KEY_FEED_NEWS_HEADING,jsonHEADING);
		editor.putString(KEY_FEED_NEWS_DESCRIPTION,jsonDESCRIPTION);
		editor.putString(KEY_FEED_NEWS_IMAGES,jsonIMAGES);
		editor.putString(KEY_FEED_NEWS_VIDEO,jsonVIDEO);
		editor.commit();


	}
	public ArrayList<FeedItem> getLastNewsFeed()
	{
		String headings = pref.getString(KEY_FEED_NEWS_HEADING, null);
		String descriptions = pref.getString(KEY_FEED_NEWS_DESCRIPTION, null);
		String images = pref.getString(KEY_FEED_NEWS_IMAGES, null);
		String videos = pref.getString(KEY_FEED_NEWS_VIDEO, null);
		List<String> heading =  null;
		List<String> description =  null;
		List<String> imagelist =  null;
		List<String> video =  null;
		if(headings != null) {
			Gson gson = new Gson();
			heading = (List<String>) gson.fromJson(headings, Object.class);
		}
		if(descriptions != null) {
			Gson gson = new Gson();
			description = (List<String>) gson.fromJson(descriptions, Object.class);
		}
		if(images != null) {
			Gson gson = new Gson();
			imagelist = (List<String>) gson.fromJson(images, Object.class);
		}
		if(videos != null) {
			Gson gson = new Gson();
			video = (List<String>) gson.fromJson(videos, Object.class);
		}
		ArrayList<FeedItem> feedlist  = new ArrayList<FeedItem>();
		if(heading != null) {
			for (int i = 0; i < heading.size(); i++) {
				FeedItem feedItem = new FeedItem();
				feedItem.setHeading(heading.get(i));
				feedItem.setDescription(description.get(i));
				//feedItem.setFeedimages(imagelist);
				feedItem.setVideoid(video.get(i));
				feedlist.add(feedItem);
			}
		}
		return feedlist;
	}
	public void setName(String orderId)
	{
		editor.putString(KEY_NAME,orderId);
		editor.commit();

	}
	public String getName()
	{
		String id = pref.getString(KEY_NAME, null);
		return id;

	}
	public String getCustomer()
	{
		String id = pref.getString(KEY_PHONE, null);
		return id;
	}

	
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// user email id
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		
		// return user
		return user;
	}

	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
