package com.kuruvatech.kumarannajds.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuruvatech.kumarannajds.R;
import com.kuruvatech.kumarannajds.adapter.YoutubeRecyclerAdapter;
import com.kuruvatech.kumarannajds.model.FeedItem;
import com.kuruvatech.kumarannajds.utils.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class VideoFragment extends Fragment {
    private static final String TAG_HEADING = "heading";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_FEEDIMAGES = "feedimages";
    private static final String TAG_VIDEO = "feedvideo";
    private static final String TAG_FEEDVIDEOS = "feedvideos";
    private static final String TAG_FEEDAUDIOS = "feedaudios";
    private static final String TAG_URL = "url";
  //  RecyclerView recyclerView;
  //  Adapter adapter;
    boolean isSwipeRefresh =true;
    ArrayList<FeedItem> feedList;
    ArrayList<String> imageList;
    RecyclerView recyclerView;
    YoutubeRecyclerAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_fragment_item_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        recyclerView=(RecyclerView)view.findViewById(R.id.video_list);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
    //    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        isSwipeRefresh = false;
        getVideos();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefresh = true;
                getVideos();
            }

        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.transparent);
        return view;
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//    @Override
//    public void setUserVisibleHint(boolean visible)
//    {
//        super.setUserVisibleHint(visible);
//        if (visible  && isResumed())
//        {
//            //Only manually call onResume if fragment is already visible
//            //Otherwise allow natural fragment lifecycle to call onResume
//            onResume();
//        }
//    }

    @Override
    public void onResume()
    {
        super.onResume();
    }



    public void initAdapter()
    {
        adapter=new YoutubeRecyclerAdapter(getContext(),feedList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
    public void getVideos()
    {
        String getFeedsUrl = Constants.GET_VIDEOS_URL;
        getFeedsUrl = getFeedsUrl +  Constants.USERNAME;
        new JSONAsyncTask().execute(getFeedsUrl);
    }



    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isSwipeRefresh == false) {
                swipeRefreshLayout.setRefreshing(true);

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

                        if (feed_object.has(TAG_DESCRIPTION))

                            feedItem.setDescription(TextUtils.htmlEncode(feed_object.getString(TAG_DESCRIPTION)));
                        if (feed_object.has(TAG_FEEDVIDEOS)) {
                            JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDVIDEOS);
                            ArrayList<String> strList = new ArrayList<String>();
                            strList.clear();
                            for (int j = 0; j < feedimagesarray.length(); j++) {
                                JSONObject image_object = feedimagesarray.getJSONObject(j);
                                if (image_object.has(TAG_URL)) {
                                    strList.add(image_object.getString(TAG_URL));
                                }
                            }
                            feedItem.setFeedvideos(strList);

                        }
                        if (feed_object.has(TAG_FEEDAUDIOS)) {
                            JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDAUDIOS);
                            ArrayList<String> strList = new ArrayList<String>();
                            strList.clear();
                            for (int j = 0; j < feedimagesarray.length(); j++) {
                                JSONObject image_object = feedimagesarray.getJSONObject(j);
                                if (image_object.has(TAG_URL)) {
                                    strList.add(image_object.getString(TAG_URL));
                                }
                            }
                            feedItem.setFeedaudios(strList);

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
    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int i) {

//            if(i == 0 || i == 1) {
//                // grid items on positions 0 and 1 will occupy 2 spans of the grid
//                return 2;
//            } else {
//                // the rest of the items will behave normally and occupy only 1 span
            return 1;
//            }
        }
    }


}
