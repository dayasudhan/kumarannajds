package com.kuruvatech.kumarannajds.model;

import java.util.ArrayList;

/**
 * Created by gagan on 10/24/2017.
 */
public class FeedItem {
    String description;
    String heading;
    ArrayList<String> feedimages;



//    ArrayList<String> scrollimages;
    String time;


    String videoid;
    public FeedItem()
    {
        description = new String();
        heading = new String();
        feedimages = new ArrayList<String>();
//        scrollimages = new ArrayList<String>();
        videoid = new String();
        time = new String();

    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String headinga) {
        heading = headinga;
    }

    public ArrayList<String> getFeedimages() {
        return feedimages;
    }

    public void setFeedimages(ArrayList<String> feedimages) {
        this.feedimages = feedimages;
    }
    public String getVideoid() {
        return videoid;
    }

    public void setVideoid(String videoid) {
        this.videoid = videoid;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
//    public ArrayList<String> getScrollimages() {
//        return scrollimages;
//    }
//
//    public void setScrollimages(ArrayList<String> scrollimages) {
//        this.scrollimages = scrollimages;
//    }
}
