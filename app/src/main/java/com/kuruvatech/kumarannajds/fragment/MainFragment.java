package com.kuruvatech.kumarannajds.fragment;

import android.app.Dialog;

import android.content.DialogInterface;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.kumarannajds.MainActivity;
import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.adapter.FeedAdapter;
import com.kuruvatech.kumarannajds.model.FeedItem;
import com.kuruvatech.kumarannajds.utils.Constants;
import com.kuruvatech.kumarannajds.utils.SessionManager;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainFragment extends Fragment{
    private static final String TAG_FEEDS = "feeds";
    private static final String TAG_ID = "id";
    private static final String TAG_HEADING = "heading";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_FEEDIMAGES = "feedimages";
    private static final String TAG_URL = "url";
    private static final String TAG_VIDEO = "feedvideo";
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    public static final String VIDEO_ID = "gy5_T2ACerk";
    Button btnshareApp;
    ArrayList<FeedItem> feedList;
    FeedAdapter adapter;
    View rootview;
    ListView listView;
    TextView noFeedstv;
    boolean isSwipeRefresh;
    private SwipeRefreshLayout swipeRefreshLayout;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootview.findViewById(R.id.listView_feedlist);
        noFeedstv = (TextView)rootview.findViewById(R.id.textView_no_feeds);
        session = new SessionManager(getActivity().getApplicationContext());
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_layout);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.titletext));

        isSwipeRefresh = false;
        feedList  =session.getLastNewsFeed();
        if(feedList !=null)
        {
            initAdapter();
        }
        getFeeds();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefresh = true;
                getFeeds();
            }

        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.transparent);

       return rootview;
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    public void initAdapter()
    {

        adapter = new FeedAdapter(getActivity(),R.layout.feeditem,feedList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        if(feedList.size() > 0 ) {
            noFeedstv.setVisibility(View.INVISIBLE);
        }
        else
        {
            noFeedstv.setVisibility(View.VISIBLE);
        }
        session.setLastNewsFeed(feedList);
    }
    public void getFeeds()
    {
        String getFeedsUrl = Constants.GET_FEEDS_URL;
        getFeedsUrl = getFeedsUrl + getString(R.string.username);
        new JSONAsyncTask().execute(getFeedsUrl);
    }


public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        Dialog dialog;
        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isSwipeRefresh == false) {
                swipeRefreshLayout.setRefreshing(true);
//                dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.custom_progress_dialog);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.show();
//                dialog.setCancelable(true);
            }

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet request = new HttpGet(urls[0]);
//                request.addHeader(Constants.SECUREKEY_KEY, Constants.SECUREKEY_VALUE);
//                request.addHeader(Constants.VERSION_KEY, Constants.VERSION_VALUE);
//                request.addHeader(Constants.CLIENT_KEY, Constants.CLIENT_VALUE);

                HttpClient httpclient = new DefaultHttpClient();

                HttpResponse response = httpclient.execute(request);

                int status = response.getStatusLine().getStatusCode();

                feedList = new ArrayList<FeedItem>();
                feedList.clear();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();

                    String data = EntityUtils.toString(entity,HTTP.UTF_8);

                    JSONArray feedsarray = new JSONArray(data);
                    for (int i = 0; i < feedsarray.length(); i++) {
                            JSONObject feed_object = feedsarray.getJSONObject(i);
                            FeedItem feedItem = new FeedItem();
                            if (feed_object.has(TAG_HEADING)) {
                                feedItem.setHeading(feed_object.getString(TAG_HEADING));
                            }
                            if (feed_object.has(TAG_VIDEO)) {
                                feedItem.setVideoid(feed_object.getString(TAG_VIDEO));
                            }

                            if (feed_object.has(TAG_DESCRIPTION)) {
                                feedItem.setDescription(TextUtils.htmlEncode(feed_object.getString(TAG_DESCRIPTION)));
                            }
                            if (feed_object.has(TAG_FEEDIMAGES)) {
                                JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDIMAGES);
                                    ArrayList<String> strList = new ArrayList<String>();
                                    strList.clear();
                                    for (int j = 0; j < feedimagesarray.length(); j++) {
                                        JSONObject image_object = feedimagesarray.getJSONObject(j);
                                        if (image_object.has(TAG_URL)) {
                                            strList.add(image_object.getString(TAG_URL));
                                        }
                                    }
                                    feedItem.setFeedimages(strList);

                            }
                            feedList.add(feedItem);
                        }
                    return true;
                }
           } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();
            }

            return false;

        }
        protected void onPostExecute(Boolean result) {
//            if(dialog != null && isSwipeRefresh ==false)
//                dialog.cancel();

            if(swipeRefreshLayout != null)
             swipeRefreshLayout.setRefreshing(false);
            isSwipeRefresh = false;
            if(getActivity() != null) {
                if (result == false) {

                   // Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                    alertMessage("Unable to fetch data from server");
                } else {
                    initAdapter();

                }
            }

        }
    }
    public void alertMessage(String message) {
        DialogInterface.OnClickListener dialogClickListeneryesno = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_NEUTRAL:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message).setNeutralButton("Ok", dialogClickListeneryesno)
                .setIcon(R.drawable.ic_action_about).show();

    }
}
